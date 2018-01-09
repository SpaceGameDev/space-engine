package space.util.sync.task.function.chained;

import space.util.baseobject.ToString;
import space.util.baseobject.additional.Cache;
import space.util.dependency.IDependency;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;
import space.util.sync.task.basic.AbstractRunnableTask;
import space.util.sync.task.basic.ITask;
import space.util.sync.task.basic.MultiTask;
import space.util.sync.task.function.TypeHandlerTaskCreator;
import space.util.sync.task.function.chained.ChainedTaskBuilder.ChainedTaskMultithreaded.ChainedTaskMultithreadedExecutor;
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

public class ChainedTaskBuilder<FUNCTION> extends AbstractChainedTaskBuilder<FUNCTION> implements ToString, Cache {
	
	/**
	 * hides the cached values {@link ChainedTaskBuilder#singlethread} and {@link ChainedTaskBuilder#multithread} from the {@link ChainedTaskBuilder#toString()} call, as they are very complex and big objects to turn into a String
	 */
	public static boolean hideCacheValues = true;
	
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
	 * resort the {@link List} of next {@link Node}s, in order to put Nodes with more Dependencies further at the top, and with less towards the end. Is only worth while if you have giant dependency lists.
	 */
	public boolean optimizeExecutionPriority;
	
	//cache
	protected ChainedTaskSinglethreaded<FUNCTION> singlethread;
	protected ChainedTaskMultithreaded<FUNCTION> multithread;
	
	//const
	public ChainedTaskBuilder() {
		this(false, false, false);
	}
	
	public ChainedTaskBuilder(boolean singlethreadedOnly) {
		this(singlethreadedOnly, false, false);
	}
	
	public ChainedTaskBuilder(boolean preferSinglethreaded, boolean preferMultithreaded) {
		this(preferSinglethreaded && !preferMultithreaded, preferSinglethreaded && preferMultithreaded, false);
	}
	
	public ChainedTaskBuilder(boolean singlethreadedOnly, boolean keepMultithreaded, boolean optimizeExecutionPriority) {
		this.singlethreadedOnly = singlethreadedOnly;
		this.keepMultithreaded = keepMultithreaded;
		this.optimizeExecutionPriority = optimizeExecutionPriority;
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
		return singlethread == null ? singlethread = new ChainedTaskSinglethreaded<>(getMultithread()) : singlethread;
	}
	
	public ChainedTaskMultithreaded<FUNCTION> getMultithread() {
		return multithread == null ? multithread = new ChainedTaskMultithreaded<>(ChainedTaskBuilder.this.list, optimizeExecutionPriority) : multithread;
	}
	
	//clear
	@Override
	public void onModification() {
		clearCache();
	}
	
	@Override
	public void clearCache() {
		singlethread = null;
		multithread = null;
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("singlethreadedOnly", this.singlethreadedOnly);
		tsh.add("keepMultithreaded", this.keepMultithreaded);
		tsh.add("optimizeExecutionPriority", this.optimizeExecutionPriority);
		tsh.add("list", this.list);
		
		if (hideCacheValues) {
			tsh.add("singlethread", this.singlethread == null ? "null" : "exists");
			tsh.add("multithread", this.multithread == null ? "null" : "exists");
		} else {
			tsh.add("singlethread", this.singlethread);
			tsh.add("multithread", this.multithread);
		}
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	//singlethreaded
	public static class ChainedTaskSinglethreaded<FUNCTION> implements ToString, IFunctionTaskCreator<FUNCTION> {
		
		public List<TypeHandlerTaskCreator<FUNCTION>> task;
		
		public ChainedTaskSinglethreaded(ChainedTaskMultithreaded<FUNCTION> multithreaded) {


//			TypeHandlerTaskCreator<FUNCTION>

//			List<ChainedTaskEntry<FUNCTION>> entryList = new ArrayList<>(list);
//			entryList.sort(ChainedTaskEntry.COMPARATOR);
//
//			task = new ArrayList<>();
//			entryList.forEach(entry -> task.add(new TypeHandlerTaskCreator<>(entry.function)));
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
		
		@Override
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("task", this.task);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	public static class ToSinglethreadedTask<FUNCTION> implements ChainedTaskMultithreadedExecutor<FUNCTION>, ToString {
		
		public List<FUNCTION> resultingList = new ArrayList<>();
		public Map<ChainedTaskBuilder.ChainedTaskMultithreaded<FUNCTION>.Node, NodeTask> map = new HashMap<>();
		
		public ToSinglethreadedTask() {
			for (Node node : allNodes)
				map.put(node, node.new NodeTask(handler).init(this));
		}
		
		@Override
		@SuppressWarnings("RedundantCast")
		public void execute(ChainedTaskBuilder.ChainedTaskMultithreaded<FUNCTION>.Node.NodeTask nodeTask) {
			resultingList.add((FUNCTION) nodeTask.getNode().func);
		}
		
		@Override
		public void runNodes(Iterable<ChainedTaskBuilder.ChainedTaskMultithreaded<FUNCTION>.Node> nodes) {
			for (ChainedTaskBuilder.ChainedTaskMultithreaded<FUNCTION>.Node node : nodes)
				map.get(node).call();
		}
		
		public List<FUNCTION> evaluate() {
			map = null;
			return resultingList;
		}
		
		@Override
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("resultingList", this.resultingList);
			tsh.add("map", this.map);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	//multithreaded
	public static class ChainedTaskMultithreaded<FUNCTION> implements ToString, IFunctionTaskCreator<FUNCTION> {
		
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
					if (comp < 0)
						node.addDependencyAndThen(test);
					else if (comp > 0)
						test.addDependencyAndThen(node);
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
		
		@Override
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("allNodes", this.allNodes);
			tsh.add("firstNodes", this.firstNodes);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
		
		//Node
		public class Node extends TypeHandlerTaskCreator<FUNCTION> implements ToString {
			
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
			
			@Override
			public <T> T toTSH(ToStringHelper<T> api) {
				ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
				tsh.add("func", this.func);
				tsh.add("dep", this.dep);
				tsh.add("depCnt", this.depCnt);
				tsh.add("next", this.next);
				return tsh.build();
			}
			
			@Override
			public String toString() {
				return toString0();
			}
			
			public class NodeTask extends TypeHandlerTask implements ToString {
				
				public ChainedTaskMultithreadedExecutor<FUNCTION> exec;
				public AtomicInteger callCnt;
				
				public NodeTask(ITypeHandler<FUNCTION> handler) {
					super(handler);
					callCnt = new AtomicInteger(depCnt);
				}
				
				public NodeTask init(ChainedTaskMultithreadedExecutor<FUNCTION> exec) {
					this.exec = exec;
					return this;
				}
				
				public void call() {
					if (callCnt.decrementAndGet() == 0)
						exec.execute(this);
				}
				
				@Override
				protected synchronized void runHooks() {
					super.runHooks();
					exec.runNodes(next);
				}
				
				public Node getNode() {
					return Node.this;
				}
				
				@Override
				public <T> T toTSH(ToStringHelper<T> api) {
					ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
					tsh.add("executionStarted", this.executionStarted);
					tsh.add("result", this.result);
					tsh.add("exception", this.exception);
					tsh.add("handler", this.handler);
					tsh.add("callCnt", this.callCnt);
					return tsh.build();
				}
				
				@Override
				public String toString() {
					return toString0();
				}
			}
		}
		
		//executor
		public interface ChainedTaskMultithreadedExecutor<FUNCTION> {
			
			/**
			 * execute the {@link ChainedTaskBuilder.ChainedTaskMultithreaded.Node Node} directly, <b>don't</b> do anything else like handle dependencies.
			 *
			 * @param nodeTask the {@link ChainedTaskBuilder.ChainedTaskMultithreaded.Node Node} to execute
			 */
			void execute(ChainedTaskBuilder.ChainedTaskMultithreaded<FUNCTION>.Node.NodeTask nodeTask);
			
			/**
			 * Handles the Dependencies.
			 * Call the {@link NodeTask#call() NodeTasks.call()} method of the corresponding {@link NodeTask NodeTasks} for all of these {@link ChainedTaskBuilder.ChainedTaskMultithreaded.Node Nodes}.
			 *
			 * @param nodes all the {@link ChainedTaskBuilder.ChainedTaskMultithreaded.Node Nodes} to do the descriped Operation on
			 */
			void runNodes(Iterable<ChainedTaskBuilder.ChainedTaskMultithreaded<FUNCTION>.Node> nodes);
		}
		
		public class ChainedTaskMultithreadedTask extends MultiTask implements ChainedTaskMultithreadedExecutor<FUNCTION>, ToString {
			
			public Executor executor;
			public Map<Node, NodeTask> map = new HashMap<>();
			
			public ChainedTaskMultithreadedTask(ITypeHandler<FUNCTION> handler) {
				for (Node node : allNodes)
					map.put(node, node.new NodeTask(handler).init(this));
				init(map.values());
			}
			
			@Override
			public synchronized void submit(Executor executor) {
				startExecution();
				this.executor = executor;
				runNodes(firstNodes);
			}
			
			@Override
			public void execute(NodeTask node) {
				executor.execute(node);
			}
			
			@Override
			public void runNodes(Iterable<Node> nodes) {
				for (Node node : nodes)
					map.get(node).call();
			}
			
			@Override
			public <T> T toTSH(ToStringHelper<T> api) {
				ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
				tsh.add("executionStarted", this.executionStarted);
				tsh.add("result", this.result);
				tsh.add("subTasks", this.subTasks);
				tsh.add("callCnt", this.callCnt);
				tsh.add("exception", this.exception);
				tsh.add("executor", this.executor);
				return tsh.build();
			}
			
			@Override
			public String toString() {
				return toString0();
			}
		}
	}
}
