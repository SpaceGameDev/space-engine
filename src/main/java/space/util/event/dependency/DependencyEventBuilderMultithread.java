package space.util.event.dependency;

import org.jetbrains.annotations.NotNull;
import space.util.baseobject.ToString;
import space.util.event.TaskEvent;
import space.util.event.typehandler.AllowMultithreading;
import space.util.event.typehandler.TypeHandler;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;
import space.util.task.Task;
import space.util.task.impl.MultiTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DependencyEventBuilderMultithread<FUNCTION> extends DependencyEventBuilder<FUNCTION> implements ToString {
	
	//settings
	/**
	 * For debugging purpose. If you have issues with multithreading, set this to true. Remember that this may have a significant performance impact.
	 */
	public volatile boolean forceSinglethread;
	
	/**
	 * Resort the {@link List} of next {@link Node}s, in order to put Nodes with more Dependencies further at the top, and with less towards the end
	 */
	public volatile boolean optimizeExecutionPriority;
	
	public DependencyEventBuilderMultithread setForceSinglethread(boolean forceSinglethread) {
		this.forceSinglethread = forceSinglethread;
		return this;
	}
	
	//constructor
	public DependencyEventBuilderMultithread() {
		this(true);
	}
	
	public DependencyEventBuilderMultithread(boolean optimizeExecutionPriority) {
		this.optimizeExecutionPriority = optimizeExecutionPriority;
	}
	
	//create
	private volatile Build build;
	
	protected synchronized Build getBuild() {
		if (build != null)
			return build;
		synchronized (this) {
			if (build != null)
				return build;
			return build = new Build();
		}
	}
	
	@NotNull
	@Override
	public Task create(@NotNull TypeHandler<FUNCTION> handler) {
		if (!(handler instanceof AllowMultithreading))
			throw new IllegalArgumentException("TypeHandler does NOT allow multithreading!");
		return getBuild().create(handler);
	}
	
	@Override
	public synchronized void clearCache() {
		build = null;
	}
	
	//toString
	@Override
	@NotNull
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("list", this.list);
		tsh.add("forceSinglethread", this.forceSinglethread);
		tsh.add("optimizeExecutionPriority", this.optimizeExecutionPriority);
		tsh.add("build", TOSTRING_HIDE_CACHE_VALUES ? (this.build != null ? "exists" : "null") : this.build);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	//build
	protected class Build extends DependencyEventBuilder.Build<FUNCTION> implements TaskEvent<FUNCTION> {
		
		public Build() {
			super(list, optimizeExecutionPriority);
		}
		
		@NotNull
		@Override
		public Task create(@NotNull TypeHandler<FUNCTION> handler) {
			return new Execution(handler);
		}
		
		private class Execution extends MultiTask implements ToString {
			
			@NotNull
			public TypeHandler<FUNCTION> handler;
			public Executor executor;
			@NotNull
			public Map<Node<FUNCTION>, NodeExecution> map = new HashMap<>();
			
			public Execution(@NotNull TypeHandler<FUNCTION> handler) {
				this.handler = handler;
				for (Node<FUNCTION> node : allNodes)
					map.put(node, new NodeExecution(node));
				init(map.values().stream().map(exec -> exec.task).collect(Collectors.toCollection(ArrayList::new)));
			}
			
			@Override
			public synchronized void submit(@NotNull Executor executor) {
				if (startExecution())
					return;
				this.executor = forceSinglethread ? Runnable::run : executor;
				for (Node node : firstNodes)
					map.get(node).call();
			}
			
			public class NodeExecution implements ToString {
				
				public Node<FUNCTION> node;
				public Task task;
				public AtomicInteger callCnt;
				
				public NodeExecution(Node<FUNCTION> node) {
					this.node = node;
					this.task = node.entry.function.create(handler);
					this.callCnt = new AtomicInteger(node.depCnt);
					
					task.addHook(taskIgnore -> this.node.next.forEach(next -> map.get(next).call()));
				}
				
				public void call() {
					if (callCnt.decrementAndGet() == 0)
						task.submit(executor);
				}
				
				@Override
				@NotNull
				public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
					ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
					tsh.add("node", this.node);
					tsh.add("task", this.task);
					tsh.add("callCnt", this.callCnt);
					return tsh.build();
				}
				
				@Override
				public String toString() {
					return toString0();
				}
			}
			
			@Override
			@NotNull
			public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
				ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
				tsh.add("executionStarted", this.executionStarted);
				tsh.add("result", this.result);
				tsh.add("callCnt", this.callCnt);
				tsh.add("handler", this.handler);
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
