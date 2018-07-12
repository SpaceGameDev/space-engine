package space.util.event.dependency;

import org.jetbrains.annotations.NotNull;
import space.util.baseobject.ToString;
import space.util.event.TaskEvent;
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

public class DependencyEventBuilderSinglethread<FUNCTION> extends DependencyEventBuilder<FUNCTION> implements ToString {
	
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
		return getBuild().create(handler);
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
	protected class Build implements TaskEvent<FUNCTION>, ToString {
		
		public List<DependencyEventEntry<FUNCTION>> sortedList = computeList(list);
		
		@NotNull
		@Override
		public Task create(@NotNull TypeHandler<FUNCTION> handler) {
			return new MultiTask(sortedList.stream().map(entry -> entry.function.create(handler)).collect(Collectors.toList())) {
				@Override
				public synchronized void submit(@NotNull Executor executor) {
					if (startExecution())
						return;
					executor.execute(() -> {
						for (Task task : subTasks)
							task.submit(Runnable::run);
					});
				}
			};
		}
		
		@NotNull
		@Override
		public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("task", this.sortedList);
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
