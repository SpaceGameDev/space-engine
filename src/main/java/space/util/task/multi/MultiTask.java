package space.util.task.multi;

import org.jetbrains.annotations.NotNull;
import space.util.sync.barrier.Barrier;
import space.util.sync.barrier.BarrierImpl;
import space.util.sync.lock.SyncLock;
import space.util.task.TaskCreator;
import space.util.task.impl.AbstractTask;

import java.util.Collection;
import java.util.Iterator;

public abstract class MultiTask extends AbstractTask {
	
	public static TaskCreator<? extends MultiTask> sequential(Collection<? extends TaskCreator> tasks) {
		return (locks, barriers) -> new MultiTask(locks, barriers) {
			@Override
			protected Barrier setup(Barrier start) {
				Iterator<? extends TaskCreator> iter = tasks.iterator();
				if (!iter.hasNext()) {
					return start;
				}
				
				Barrier current = iter.next().submit(start);
				while (iter.hasNext()) {
					current = iter.next().submit(current);
				}
				return current;
			}
		};
	}
	
	public static TaskCreator<? extends MultiTask> parallel(Collection<? extends TaskCreator> tasks) {
		return (locks, barriers) -> new MultiTask(locks, barriers) {
			@Override
			protected Barrier setup(Barrier start) {
				if (tasks.size() == 0) {
					return start;
				}
				
				//custom handler for fever object allocation
				BarrierImpl end = new BarrierImpl();
				//noinspection SuspiciousToArrayCall
				start.addHook(() -> Barrier.awaitAll(end::triggerNow, tasks.stream().map(TaskCreator::submit).toArray(Barrier[]::new)));
				return end;
			}
		};
	}
	
	protected final BarrierImpl submitBarrier;
	protected final Barrier endBarrier;
	
	public MultiTask(@NotNull Barrier... barriers) {
		this(SyncLock.EMPTY_SYNCLOCK_ARRAY, barriers);
	}
	
	public MultiTask(@NotNull SyncLock[] locks, @NotNull Barrier... barriers) {
		super(locks, barriers);
		endBarrier = setup(submitBarrier = new BarrierImpl());
		endBarrier.addHook(this::executionFinished);
	}
	
	protected abstract Barrier setup(Barrier start);
	
	@Override
	protected void submit0() {
		submitBarrier.triggerNow();
	}
}
