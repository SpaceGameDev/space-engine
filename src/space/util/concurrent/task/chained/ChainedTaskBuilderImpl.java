package space.util.concurrent.task.chained;

import space.util.baseobject.Cache;
import space.util.baseobject.ToString;
import space.util.concurrent.task.Task;
import space.util.concurrent.task.TinyWorkload;
import space.util.concurrent.task.chained.ChainedTaskBuilderImpl.ChainedTaskPart.Node;
import space.util.concurrent.task.creator.TaskCreator;
import space.util.concurrent.task.impl.AbstractRunnableTask;
import space.util.concurrent.task.impl.MultiTask;
import space.util.concurrent.task.impl.TypeHandlerTaskCreator;
import space.util.concurrent.task.typehandler.TypeHandler;
import space.util.delegate.list.ModificationAwareList;
import space.util.dependency.Dependency;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ChainedTaskBuilderImpl<FUNCTION> implements ChainedTaskBuilder<FUNCTION>, ToString, Cache {
	
	public static boolean TOSTRING_HIDE_CACHE_VALUES = true;
	
	//boolean fields
	/**
	 * for debugging purpose. If you have issues with multithreading, set this to true. Remember that this may have a significant performance impact.
	 */
	public volatile boolean singlethreadedOnly;
	
	/**
	 * resort the {@link List} of next {@link Node}s, in order to put Nodes with more Dependencies further at the top, and with less towards the end
	 */
	public volatile boolean multithreadedOptimizeExecutionPriority;
	
	/**
	 * if Multithreaded Tasks marked as {@link TinyWorkload} should be respected and executed directly instead of being submitted into a pool.
	 */
	public volatile boolean multithreadedAllowTinyWorkload;
	
	public List<ChainedTaskEntry<FUNCTION>> list = new ModificationAwareList<>(new ArrayList<>(), this::clearCache);
	
	//cache
	protected final Object cacheLock = new Object();
	protected volatile ChainedTaskSinglethreaded<FUNCTION> singlethread;
	protected volatile ChainedTaskMultithreaded<FUNCTION> multithread;
	
	//const
	public ChainedTaskBuilderImpl() {
		this(false, false, false);
	}
	
	public ChainedTaskBuilderImpl(boolean singlethreadedOnly) {
		this(singlethreadedOnly, true, true);
	}
	
	public ChainedTaskBuilderImpl(boolean preferSinglethreaded, boolean preferMultithreaded) {
		this(preferSinglethreaded && !preferMultithreaded, true, true);
	}
	
	public ChainedTaskBuilderImpl(boolean singlethreadedOnly, boolean multithreadedOptimizeExecutionPriority, boolean multithreadedAllowTinyWorkload) {
		this.singlethreadedOnly = singlethreadedOnly;
		this.multithreadedOptimizeExecutionPriority = multithreadedOptimizeExecutionPriority;
		this.multithreadedAllowTinyWorkload = multithreadedAllowTinyWorkload;
	}
	
	public ChainedTaskBuilderImpl setSinglethreadedOnly(boolean singlethreadedOnly) {
		this.singlethreadedOnly = singlethreadedOnly;
		return this;
	}
	
	//hooks
	@Override
	public void addHook(ChainedTaskEntry<FUNCTION> task) {
		list.add(task);
	}
	
	@Override
	public boolean removeHook(ChainedTaskEntry<FUNCTION> task) {
		return list.remove(task);
	}
	
	//create
	@Override
	public Task create(TypeHandler<FUNCTION> handler) {
		return (singlethreadedOnly || !handler.allowMultithreading()) ? getSinglethread().create(handler) : getMultithread().create(handler);
	}
	
	//getter
	protected synchronized ChainedTaskSinglethreaded<FUNCTION> getSinglethread() {
		if (singlethread != null)
			return singlethread;
		synchronized (cacheLock) {
			if (singlethread != null)
				return singlethread;
			return singlethread = new ChainedTaskSinglethreaded<>(ChainedTaskBuilderImpl.this.list);
		}
	}
	
	protected synchronized ChainedTaskMultithreaded<FUNCTION> getMultithread() {
		if (multithread != null)
			return multithread;
		synchronized (cacheLock) {
			if (multithread != null)
				return multithread;
			return multithread = new ChainedTaskMultithreaded<>(ChainedTaskBuilderImpl.this.list, multithreadedOptimizeExecutionPriority, multithreadedAllowTinyWorkload);
		}
	}
	
	//clear
	@Override
	public synchronized void clearCache() {
		synchronized (cacheLock) {
			singlethread = null;
			multithread = null;
		}
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("singlethreadedOnly", this.singlethreadedOnly);
		tsh.add("optimizeExecutionPriority", this.multithreadedOptimizeExecutionPriority);
		tsh.add("allowTinyWorkload", this.multithreadedAllowTinyWorkload);
		tsh.add("list", this.list);
		tsh.add("singlethread", TOSTRING_HIDE_CACHE_VALUES ? (this.singlethread != null ? "exists" : "null") : this.singlethread);
		tsh.add("multithread", TOSTRING_HIDE_CACHE_VALUES ? (this.singlethread != null ? "exists" : "null") : this.multithread);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	//ChainedTaskPart
	protected static abstract class ChainedTaskPart<FUNCTION> implements ToString {
		
		public List<Node> allNodes = new ArrayList<>();
		public List<Node> firstNodes = new ArrayList<>();
		
		/**
		 * @param list                      List of {@link ChainedTaskEntry}
		 * @param optimizeExecutionPriority whether to resort the {@link List} of next {@link Node}s, in order to put Nodes with more Dependencies further at the top, and with less towards the end
		 */
		public ChainedTaskPart(List<ChainedTaskEntry<FUNCTION>> list, boolean optimizeExecutionPriority) {
			//adding
			list.forEach(entry -> allNodes.add(new Node(entry.function, entry.dependency)));
			
			//adding Dependencies
			for (int i = 0; i < allNodes.size(); i++) {
				Node node = allNodes.get(i);
				for (int k = i + 1; k < allNodes.size(); k++) {
					Node test = allNodes.get(k);
					int comp = Dependency.COMPARATOR.compare(node.dep, test.dep);
					if (comp < 0)
						node.addDependencyAfter(test);
					else if (comp > 0)
						test.addDependencyAfter(node);
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
		
		/**
		 * one {@link Node} exists for each FUNCTION
		 */
		public class Node implements ToString {
			
			public FUNCTION func;
			public Dependency dep;
			public int depCnt;
			public List<Node> next = new ArrayList<>();
			
			public Node(FUNCTION func, Dependency dep) {
				this.func = func;
				this.dep = dep;
			}
			
			public void addDependencyAfter(Node node) {
				next.add(node);
				node.depCnt++;
			}
			
			@Override
			public boolean equals(Object o) {
				if (this == o)
					return true;
				if (!(o instanceof ChainedTaskPart.Node))
					return false;
				ChainedTaskPart.Node node = (ChainedTaskPart.Node) o;
				return Objects.equals(func, node.func);
			}
			
			@Override
			public int hashCode() {
				return Objects.hash(dep.uuid());
			}
			
			@Override
			public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
				ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
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
		}
		
		@Override
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("allNodes", this.allNodes);
			tsh.add("firstNodes", this.firstNodes);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
		

	}
	
	//ChainedTaskMultithreaded
	private static class ChainedTaskMultithreaded<FUNCTION> extends ChainedTaskPart<FUNCTION> implements TaskCreator<FUNCTION> {
		
		/**
		 * if Multithreaded Tasks marked as {@link TinyWorkload} should be respected and executed directly instead of being submitted into a pool.
		 */
		public boolean allowTinyWorkload;
		
		public ChainedTaskMultithreaded(List<ChainedTaskEntry<FUNCTION>> list, boolean optimizeExecutionPriority, boolean allowTinyWorkload) {
			super(list, optimizeExecutionPriority);
			this.allowTinyWorkload = allowTinyWorkload;
		}
		
		private class ChainedTaskMultithreadedTask extends MultiTask implements ToString {
			
			public TypeHandler<FUNCTION> handler;
			public Executor executor;
			public Map<Node, NodeTaskMultithreaded> map = new HashMap<>();
			
			public ChainedTaskMultithreadedTask(TypeHandler<FUNCTION> handler) {
				this.handler = handler;
				
				for (Node node : allNodes)
					map.put(node, new NodeTaskMultithreaded(node));
				init(map.values());
			}
			
			@Override
			public synchronized void submit(Executor executor) {
				if (startExecution())
					return;
				this.executor = executor;
				for (Node node : firstNodes)
					map.get(node).call();
			}
			
			public class NodeTaskMultithreaded extends AbstractRunnableTask implements ToString {
				
				public Node node;
				public AtomicInteger callCnt;
				
				public NodeTaskMultithreaded(Node node) {
					this.node = node;
					callCnt = new AtomicInteger(node.depCnt);
				}
				
				public void call() {
					if (callCnt.decrementAndGet() == 0) {
						if (allowTinyWorkload && node.func instanceof TinyWorkload)
							this.run();
						else
							ChainedTaskMultithreadedTask.this.executor.execute(this);
					}
				}
				
				@Override
				protected void run0() {
					handler.accept(node.func);
				}
				
				@Override
				protected synchronized void runHooks() {
					super.runHooks();
					for (Node node : node.next)
						map.get(node).call();
				}
				
				@Override
				public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
					ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
					tsh.add("executionStarted", this.executionStarted);
					tsh.add("result", this.result);
					tsh.add("executor", this.executor);
					tsh.add("node", this.node);
					tsh.add("callCnt", this.callCnt);
					return tsh.build();
				}
				
				@Override
				public String toString() {
					return toString0();
				}
			}
			
			@Override
			public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
				ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
				tsh.add("executionStarted", this.executionStarted);
				tsh.add("result", this.result);
				tsh.add("subTasks", this.subTasks);
				tsh.add("callCnt", this.callCnt);
				tsh.add("exception", this.exception);
				tsh.add("handler", this.handler);
				tsh.add("executor", this.executor);
				return tsh.build();
			}
			
			@Override
			public String toString() {
				return toString0();
			}
		}
		
		@Override
		public Task create(TypeHandler<FUNCTION> handler) {
			return new ChainedTaskMultithreadedTask(handler);
		}
		

	}
	
	//ChainedTaskSinglethreaded
	private static class ChainedTaskSinglethreaded<FUNCTION> implements TaskCreator<FUNCTION>, ToString {
		
		public List<TypeHandlerTaskCreator<FUNCTION>> task = new ArrayList<>();
		
		public ChainedTaskSinglethreaded(List<ChainedTaskEntry<FUNCTION>> list) {
			new ChainedTaskSinglethreadedCreator(list).run();
		}
		
		/**
		 * mainly for figuring out the order of the tasks
		 */
		private class ChainedTaskSinglethreadedCreator implements Runnable {
			
			private ChainedTaskPart<FUNCTION> part;
			private Map<Node, NodeTaskSinglethreaded> map = new HashMap<>();
			
			public ChainedTaskSinglethreadedCreator(List<ChainedTaskEntry<FUNCTION>> list) {
				part = new ChainedTaskPart<>(list, false) {
				};
				
				for (ChainedTaskPart<FUNCTION>.Node node : part.allNodes)
					map.put(node, new NodeTaskSinglethreaded(node));
			}
			
			@Override
			public void run() {
				for (Node node : part.firstNodes)
					map.get(node).call();
			}
			
			private class NodeTaskSinglethreaded {
				
				private ChainedTaskPart<FUNCTION>.Node node;
				public AtomicInteger callCnt;
				
				public NodeTaskSinglethreaded(ChainedTaskPart<FUNCTION>.Node node) {
					this.node = node;
					callCnt = new AtomicInteger(node.depCnt);
				}
				
				public void call() {
					if (callCnt.decrementAndGet() == 0) {
						task.add(new TypeHandlerTaskCreator<>(node.func));
						for (Node node : node.next)
							map.get(node).call();
					}
				}
			}
		}
		
		@Override
		public Task create(TypeHandler<FUNCTION> handler) {
			return new MultiTask(task.stream().map((TypeHandlerTaskCreator<FUNCTION> task) -> task.create(handler)).collect(Collectors.toList())) {
				@Override
				public synchronized void submit(Executor executor) {
					if (startExecution())
						return;
					executor.execute(() -> {
						for (Task task : subTasks)
							task.submit(Runnable::run);
					});
				}
			};
		}
		
		@Override
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("task", this.task);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
}
