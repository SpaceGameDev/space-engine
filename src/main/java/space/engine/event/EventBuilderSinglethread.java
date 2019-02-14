package space.engine.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.event.typehandler.TypeHandler;
import space.engine.sync.DelayTask;
import space.engine.sync.TaskCreator;
import space.engine.sync.Tasks;
import space.engine.sync.barrier.Barrier;
import space.engine.sync.lock.SyncLock;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class EventBuilderSinglethread<FUNCTION> extends AbstractEventBuilder<FUNCTION> {
	
	private volatile @Nullable List<FUNCTION> build;
	
	@Override
	public @NotNull Barrier submit(@NotNull TypeHandler<FUNCTION> typeHandler, @NotNull SyncLock[] locks, @NotNull Barrier... barriers) {
		Iterator<FUNCTION> iterator = getBuild().iterator();
		//noinspection unchecked
		TaskCreator<? extends Barrier>[] runnableWithDelay = new TaskCreator[1];
		runnableWithDelay[0] = Tasks.runnable(() -> {
			try {
				while (iterator.hasNext()) {
					typeHandler.accept(iterator.next());
				}
			} catch (DelayTask e) {
				throw new DelayTask(runnableWithDelay[0].submit(e.barrier));
			}
		});
		return runnableWithDelay[0].submit(locks, barriers);
	}
	
	//build
	public List<FUNCTION> getBuild() {
		//non-synchronized access
		List<FUNCTION> build = this.build;
		if (build != null)
			return build;
		
		synchronized (this) {
			//synchronized access to prevent generating list multiple times
			build = this.build;
			if (build != null)
				return build;
			
			//actual build
			build = computeBuild();
			this.build = build;
			return build;
		}
	}
	
	@NotNull
	private List<FUNCTION> computeBuild() {
		return computeDependencyOrderedList().stream().map(node -> node.entry.function).collect(Collectors.toList());
	}
	
	@Override
	public void clearCache() {
		super.clearCache();
		build = null;
	}
}
