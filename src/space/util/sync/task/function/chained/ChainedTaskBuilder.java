package space.util.sync.task.function.chained;

import space.util.baseobject.interfaces.ICache;
import space.util.dependency.IDependency;
import space.util.sync.task.basic.AbstractRunnableTask;
import space.util.sync.task.basic.ITask;
import space.util.sync.task.basic.MultiTask;
import space.util.sync.task.function.TypeHandlerTaskCreator;
import space.util.sync.task.function.chained.ChainedTaskBuilder.ChainedTaskMultithreaded.Node;
import space.util.sync.task.function.chained.ChainedTaskBuilder.ChainedTaskMultithreaded.Node.NodeTask;
import space.util.sync.task.function.creator.IFunctionTaskCreator;
import space.util.sync.task.function.typehandler.ITypeHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class ChainedTaskBuilder<FUNCTION> extends AbstractChainedTaskBuilder<FUNCTION> implements ICache {
	
	//boolean fields
	/**
	 * for debugging purpose. If you have issues with multithreading, set this to true. Remember that this may have a significant performance impact.
	 */
	public boolean singlethreadedOnly;
	
	/**
	 * when creating the singlethreaded Instance it has to create a multithreaded Instance and then convert it into a singlethreaded Instance.
	 * By enabling this option it stores the multithreaded Instance instead of disposing / stack allocating it. Turn on if you use singlethreaded and multithreaded Handlers.
	 */
	public boolean keepMultithreaded;
	
	/**
	 * resort the {@link List} of next {@link Node}s, in order to put Nodes with more Dependencies further at the top, and with less towards the end
	 */
	public boolean optimizeExecutionPriority;
	
	//cache
	protected ChainedTaskSinglethreaded<FUNCTION> singlethread;
	protected ChainedTaskMultithreaded<FUNCTION> multithread;
	
	//const
	public ChainedTaskBuilder() {
		this(false, false);
	}
	
	public ChainedTaskBuilder(boolean singlethreadedOnly, boolean preferSinglethreaded, boolean preferMultithreaded) {
		this(singlethreadedOnly, preferSinglethreaded & preferMultithreaded);
	}
	
	public ChainedTaskBuilder(boolean singlethreadedOnly, boolean keepMultithreaded) {
		this.singlethreadedOnly = singlethreadedOnly;
		this.keepMultithreaded = keepMultithreaded;
	}
	
	public ChainedTaskBuilder setSinglethreadedOnly(boolean singlethreadedOnly) {
		this.singlethreadedOnly = singlethreadedOnly;
		return this;
	}
	
	//create
	@Override
	public ITask create(ITypeHandler<FUNCTION> handler) {
		return (singlethreadedOnly || !handler.allowMultithreading()) ? getSinglethread().create(handler) : getMultithread().create(handler);
	}
	
	//getter
	public ChainedTaskSinglethreaded<FUNCTION> getSinglethread() {
		return singlethread == null ? singlethread = new ChainedTaskSinglethreaded<>(ChainedTaskBuilder.this.list) : singlethread;
	}
	
	public ChainedTaskMultithreaded<FUNCTION> getMultithread() {
		return multithread == null ? multithread = new ChainedTaskMultithreaded<>(ChainedTaskBuilder.this.list, optimizeExecutionPriority) : multithread;
	}
	
	//clear
	@Override
	public void onModification() {
		clear();
	}
	
	@Override
	public void clear() {
		singlethread = null;
		multithread = null;
	}
	
	public static class ChainedTaskSinglethreaded<FUNCTION> implements IFunctionTaskCreator<FUNCTION> {
		
		public List<TypeHandlerTaskCreator<FUNCTION>> task;
		
		public ChainedTaskSinglethreaded(List<ChainedTaskEntry<FUNCTION>> list) {
			List<ChainedTaskEntry<FUNCTION>> entryList = new ArrayList<>(list);
			entryList.sort(ChainedTaskEntry.COMPARATOR);
			
			task = new ArrayList<>();
			entryList.forEach(entry -> task.add(new TypeHandlerTaskCreator<>(entry.function)));
		}
		
		@Override
		public ITask create(ITypeHandler<FUNCTION> handler) {
			return new AbstractRunnableTask() {
				@Override
				protected void run0() {
					task.forEach(creator -> creator.execute(handler));
				}
			};
		}
	}
	
	public static class ChainedTaskMultithreaded<FUNCTION> implements IFunctionTaskCreator<FUNCTION> {
		
		public List<Node> allNodes = new ArrayList<>();
		public List<Node> firstNodes = new ArrayList<>();
		
		/**
		 * @param list                      List of {@link ChainedTaskEntry}
		 * @param optimizeExecutionPriority whether to resort the {@link List} of next {@link Node}s, in order to put Nodes with more Dependencies further at the top, and with less towards the end
		 */
		public ChainedTaskMultithreaded(List<ChainedTaskEntry<FUNCTION>> list, boolean optimizeExecutionPriority) {
			//adding
			list.forEach(entry -> allNodes.add(new Node(entry.function, entry.dependency)));
			
			//sorting
			for (int i = 0; i < allNodes.size(); i++) {
				Node node = allNodes.get(i);
				
				for (int k = i + 1; k < allNodes.size(); k++) {
					Node test = allNodes.get(k);
					int comp = IDependency.COMPARATOR.compare(node.dep, test.dep);
					if (comp < 0) {
						node.addDependencyAndThen(test);
					} else if (comp > 0) {
						test.addDependencyAndThen(node);
					}
				}
			}
			
			for (Node node : allNodes) {
				//firstNodes
				if (node.depCnt == 0) {
					firstNodes.add(node);
					node.depCnt = 1;
				}
				
				//optimizeExecutionPriority
				if (optimizeExecutionPriority)
					node.next.sort((o1, o2) -> o2.next.size() - o1.next.size());
			}
		}
		
		@Override
		public ITask create(ITypeHandler<FUNCTION> handler) {
			return new ChainedTaskMultithreadedTask(handler);
		}
		
		public class Node extends TypeHandlerTaskCreator<FUNCTION> {
			
			public IDependency dep;
			public int depCnt;
			public List<Node> next = new ArrayList<>();
			
			public Node(FUNCTION func, IDependency dep) {
				super(func);
				this.dep = dep;
			}
			
			public void addDependencyAndThen(Node node) {
				next.add(node);
				node.depCnt++;
			}
			
			@Override
			public ITask create(ITypeHandler<FUNCTION> handler) {
				return new NodeTask(handler);
			}
			
			public class NodeTask extends TypeHandlerTask {
				
				public ChainedTaskMultithreadedTask exec;
				public AtomicInteger callCnt;
				
				public NodeTask(ITypeHandler<FUNCTION> handler) {
					super(handler);
					callCnt = new AtomicInteger(depCnt);
				}
				
				public NodeTask init(ChainedTaskMultithreadedTask exec) {
					this.exec = exec;
					return this;
				}
				
				public void call() {
					if (callCnt.decrementAndGet() == 0)
						exec.executor.execute(this);
				}
				
				@Override
				protected synchronized void runHooks() {
					super.runHooks();
					next.forEach(exec::runNode);
				}
				
				@Override
				public String toString() {
					return dep.uuid() + ": " + callCnt.get();
				}
			}
			
			@Override
			public String toString() {
				return dep.uuid();
			}
		}
		
		public class ChainedTaskMultithreadedTask extends MultiTask {
			
			public ITypeHandler<FUNCTION> handler;
			public Executor executor;
			public Map<Node, NodeTask> map = new HashMap<>();
			
			public ChainedTaskMultithreadedTask(ITypeHandler<FUNCTION> handler) {
				super();
				this.handler = handler;
				
				for (Node node : allNodes)
					map.put(node, node.new NodeTask(handler).init(this));
				init(map.values());
			}
			
			@Override
			public synchronized void submit(Executor executor) {
				startExecution();
				this.executor = executor;
				for (Node node : firstNodes)
					runNode(node);
			}
			
			public void runNode(Node node) {
				map.get(node).call();
			}
		}
	}
}
