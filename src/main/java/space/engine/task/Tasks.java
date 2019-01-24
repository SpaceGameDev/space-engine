package space.engine.task;

import space.engine.sync.barrier.Barrier;
import space.engine.sync.barrier.BarrierImpl;
import space.engine.sync.lock.SyncLock;
import space.engine.task.impl.FutureTask;
import space.engine.task.impl.MultiTask;
import space.engine.task.impl.RunnableTask;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;

import static space.engine.ArrayUtils.mergeIfNeeded;
import static space.engine.sync.barrier.Barrier.EMPTY_BARRIER_ARRAY;
import static space.engine.sync.lock.SyncLock.EMPTY_SYNCLOCK_ARRAY;

public class Tasks {
	
	private Tasks() {
	}
	
	//Runnable
	public static TaskCreator<? extends RunnableTask> runnableMinimal(Runnable run) {
		return runnable(Runnable::run, EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, run);
	}
	
	public static TaskCreator<? extends RunnableTask> runnableMinimal(Barrier[] staticBarriers, Runnable run) {
		return runnable(Runnable::run, EMPTY_SYNCLOCK_ARRAY, staticBarriers, run);
	}
	
	public static TaskCreator<? extends RunnableTask> runnableMinimal(SyncLock[] staticLocks, Barrier[] staticBarriers, Runnable run) {
		return runnable(Runnable::run, staticLocks, staticBarriers, run);
	}
	
	public static TaskCreator<? extends RunnableTask> runnable(Executor exec, Barrier[] staticBarriers, Runnable run) {
		return runnable(exec, EMPTY_SYNCLOCK_ARRAY, staticBarriers, run);
	}
	
	public static TaskCreator<? extends RunnableTask> runnable(Executor exec, Runnable run) {
		return runnable(exec, EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, run);
	}
	
	public static TaskCreator<? extends RunnableTask> runnable(Executor exec, SyncLock[] staticLocks, Barrier[] staticBarriers, Runnable run) {
		return (locks, barriers) -> new RunnableTask(mergeIfNeeded(SyncLock[]::new, staticLocks, locks), mergeIfNeeded(Barrier[]::new, staticBarriers, barriers)) {
			@Override
			protected synchronized void submit1(Runnable toRun) {
				exec.execute(toRun);
			}
			
			@Override
			protected void execute() {
				run.run();
			}
		};
	}
	
	//Future
	public static <R> TaskCreator<? extends FutureTask<R>> futureMinimal(Supplier<R> run) {
		return future(Runnable::run, EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, run);
	}
	
	public static <R> TaskCreator<? extends FutureTask<R>> futureMinimal(Barrier[] staticBarriers, Supplier<R> run) {
		return future(Runnable::run, EMPTY_SYNCLOCK_ARRAY, staticBarriers, run);
	}
	
	public static <R> TaskCreator<? extends FutureTask<R>> futureMinimal(SyncLock[] staticLocks, Barrier[] staticBarriers, Supplier<R> run) {
		return future(Runnable::run, staticLocks, staticBarriers, run);
	}
	
	public static <R> TaskCreator<? extends FutureTask<R>> future(Executor exec, Supplier<R> run) {
		return future(exec, EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, run);
	}
	
	public static <R> TaskCreator<? extends FutureTask<R>> future(Executor exec, Barrier[] staticBarriers, Supplier<R> run) {
		return future(exec, EMPTY_SYNCLOCK_ARRAY, staticBarriers, run);
	}
	
	public static <R> TaskCreator<? extends FutureTask<R>> future(Executor exec, SyncLock[] staticLocks, Barrier[] staticBarriers, Supplier<R> run) {
		return (locks, barriers) -> new FutureTask<>(mergeIfNeeded(SyncLock[]::new, staticLocks, locks), mergeIfNeeded(Barrier[]::new, staticBarriers, barriers)) {
			@Override
			protected synchronized void submit1(Runnable toRun) {
				exec.execute(toRun);
			}
			
			@Override
			protected R execute0() {
				return run.get();
			}
		};
	}
	
	//MultiTask custom
	public static TaskCreator<? extends MultiTask> multiCustom(Function<Barrier, Barrier> setup) {
		return multiCustom(EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, setup);
	}
	
	public static TaskCreator<? extends MultiTask> multiCustom(Barrier[] staticBarriers, Function<Barrier, Barrier> setup) {
		return multiCustom(EMPTY_SYNCLOCK_ARRAY, staticBarriers, setup);
	}
	
	public static TaskCreator<? extends MultiTask> multiCustom(SyncLock[] staticLocks, Barrier[] staticBarriers, Function<Barrier, Barrier> setup) {
		return (locks, barriers) -> new MultiTask(mergeIfNeeded(SyncLock[]::new, staticLocks, locks), mergeIfNeeded(Barrier[]::new, staticBarriers, barriers)) {
			@Override
			protected Barrier setup(Barrier start) {
				return setup.apply(start);
			}
		};
	}
	
	//MultiTask sequential
	public static TaskCreator<? extends MultiTask> sequential(Collection<? extends TaskCreator> tasks) {
		return sequential(EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, tasks);
	}
	
	public static TaskCreator<? extends MultiTask> sequential(Barrier[] staticBarriers, Collection<? extends TaskCreator> tasks) {
		return sequential(EMPTY_SYNCLOCK_ARRAY, staticBarriers, tasks);
	}
	
	public static TaskCreator<? extends MultiTask> sequential(SyncLock[] staticLocks, Barrier[] staticBarriers, Collection<? extends TaskCreator> tasks) {
		return (locks, barriers) -> new MultiTask(mergeIfNeeded(SyncLock[]::new, staticLocks, locks), mergeIfNeeded(Barrier[]::new, staticBarriers, barriers)) {
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
	
	//MultiTask parallel
	public static TaskCreator<? extends MultiTask> parallel(Collection<? extends TaskCreator> tasks) {
		return parallel(EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, tasks);
	}
	
	public static TaskCreator<? extends MultiTask> parallel(Barrier[] staticBarriers, Collection<? extends TaskCreator> tasks) {
		return parallel(EMPTY_SYNCLOCK_ARRAY, staticBarriers, tasks);
	}
	
	public static TaskCreator<? extends MultiTask> parallel(SyncLock[] staticLocks, Barrier[] staticBarriers, Collection<? extends TaskCreator> tasks) {
		return (locks, barriers) -> new MultiTask(mergeIfNeeded(SyncLock[]::new, staticLocks, locks), mergeIfNeeded(Barrier[]::new, staticBarriers, barriers)) {
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
}
