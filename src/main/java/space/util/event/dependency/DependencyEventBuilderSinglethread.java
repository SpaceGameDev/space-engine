package space.util.event.dependency;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.baseobject.ToString;
import space.util.event.EventCreator;
import space.util.event.typehandler.TypeHandler;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;
import space.util.task.Task;
import space.util.task.TaskExceptionHandler;
import space.util.task.impl.MultiTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DependencyEventBuilderSinglethread<FUNCTION> extends DependencyEventBuilder<FUNCTION> implements ToString {
	
	//create
	private volatile Build build;
	
	public synchronized Build getBuild() {
		if (build != null)
			return build;
		synchronized (this) {
			if (build != null)
				return build;
			return build = new Build();
		}
	}
	
	@Override
	public @NotNull Task create(@NotNull TypeHandler<FUNCTION> handler, @Nullable TaskExceptionHandler exceptionHandler) {
		return getBuild().create(handler, exceptionHandler);
	}
	
	@Override
	public synchronized void clearCache() {
		build = null;
	}
	
	@Override
	@NotNull
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("list", this.list);
		tsh.add("build", TOSTRING_HIDE_CACHE_VALUES ? (this.build != null ? "exists" : "null") : this.build);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	//build
	public class Build implements EventCreator<FUNCTION>, ToString {
		
		public List<DependencyEventEntry<FUNCTION>> preparedList = computeList(list);
		
		@Override
		public @NotNull Task create(@NotNull TypeHandler<FUNCTION> handler, @Nullable TaskExceptionHandler exceptionHandler) {
			return new MultiTask(preparedList.stream().map(entry -> entry.function.create(handler, exceptionHandler)).collect(Collectors.toList())) {
				@Override
				public synchronized void submit(@NotNull Executor executor) {
					if (startExecution())
						return;
					executor.execute(() -> {
						for (Task task : subTasks)
							task.submit(Runnable::run);
					});
				}
				
				@Override
				@NotNull
				public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
					ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
					tsh.add("executionStarted", this.executionStarted);
					tsh.add("result", this.result);
					tsh.add("subTasks", this.subTasks);
					tsh.add("callCnt", this.callCnt);
					tsh.add("build", Build.this);
					return tsh.build();
				}
			};
		}
		
		@Override
		@NotNull
		public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("preparedList", this.preparedList);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	public static <FUNCTION> List<DependencyEventEntry<FUNCTION>> computeList(List<DependencyEventEntry<FUNCTION>> list) {
		DependencyEventBuilder.Build<FUNCTION> build = new DependencyEventBuilder.Build<>(list, false);
		Map<Node<FUNCTION>, Runnable> map = new HashMap<>();
		List<DependencyEventEntry<FUNCTION>> ret = new ArrayList<>();
		
		for (Node<FUNCTION> node : build.allNodes)
			map.put(node, new Runnable() {
				AtomicInteger callCnt = new AtomicInteger(node.depCnt);
				
				public void run() {
					if (callCnt.decrementAndGet() == 0)
						ret.add(node.entry);
					node.next.forEach(next -> map.get(next).run());
				}
			});
		for (Node node : build.firstNodes)
			map.get(node).run();
		
		return ret;
	}
}
