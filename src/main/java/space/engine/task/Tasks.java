package space.engine.task;

import space.engine.sync.barrier.Barrier;
import space.engine.sync.barrier.BarrierImpl;
import space.engine.sync.future.Future;
import space.engine.sync.lock.SyncLock;
import space.engine.task.impl.DelayTask;
import space.engine.task.impl.FutureTask;
import space.engine.task.impl.MultiTask;
import space.engine.task.impl.RunnableTask;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.function.Function;

import static space.engine.ArrayUtils.mergeIfNeeded;
import static space.engine.sync.barrier.Barrier.EMPTY_BARRIER_ARRAY;
import static space.engine.sync.lock.SyncLock.EMPTY_SYNCLOCK_ARRAY;

public class Tasks {
	
	private Tasks() {
	}
	
	//Runnable
	@FunctionalInterface
	public interface RunnableWithDelay {
		
		void run() throws DelayTask;
	}
	
	public static TaskCreator<? extends Barrier> runnableMinimal(RunnableWithDelay run) {
		return runnable(Runnable::run, EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, run);
	}
	
	public static TaskCreator<? extends Barrier> runnableMinimal(Barrier[] staticBarriers, RunnableWithDelay run) {
		return runnable(Runnable::run, EMPTY_SYNCLOCK_ARRAY, staticBarriers, run);
	}
	
	public static TaskCreator<? extends Barrier> runnableMinimal(SyncLock[] staticLocks, Barrier[] staticBarriers, RunnableWithDelay run) {
		return runnable(Runnable::run, staticLocks, staticBarriers, run);
	}
	
	public static TaskCreator<? extends Barrier> runnable(Executor exec, Barrier[] staticBarriers, RunnableWithDelay run) {
		return runnable(exec, EMPTY_SYNCLOCK_ARRAY, staticBarriers, run);
	}
	
	public static TaskCreator<? extends Barrier> runnable(Executor exec, RunnableWithDelay run) {
		return runnable(exec, EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, run);
	}
	
	public static TaskCreator<? extends Barrier> runnable(Executor exec, SyncLock[] staticLocks, Barrier[] staticBarriers, RunnableWithDelay run) {
		return (locks, barriers) -> new RunnableTask(mergeIfNeeded(SyncLock[]::new, staticLocks, locks), mergeIfNeeded(Barrier[]::new, staticBarriers, barriers)) {
			@Override
			protected synchronized void submit1(Runnable toRun) {
				exec.execute(toRun);
			}
			
			@Override
			protected void execute() throws DelayTask {
				run.run();
			}
		};
	}
	
	//Future
	@FunctionalInterface
	public interface SupplierWithDelay<T> {
		
		T get() throws DelayTask;
	}
	
	public static <R> TaskCreator<? extends Future<R>> futureMinimal(SupplierWithDelay<R> run) {
		return future(Runnable::run, EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, run);
	}
	
	public static <R> TaskCreator<? extends Future<R>> futureMinimal(Barrier[] staticBarriers, SupplierWithDelay<R> run) {
		return future(Runnable::run, EMPTY_SYNCLOCK_ARRAY, staticBarriers, run);
	}
	
	public static <R> TaskCreator<? extends Future<R>> futureMinimal(SyncLock[] staticLocks, Barrier[] staticBarriers, SupplierWithDelay<R> run) {
		return future(Runnable::run, staticLocks, staticBarriers, run);
	}
	
	public static <R> TaskCreator<? extends Future<R>> future(Executor exec, SupplierWithDelay<R> run) {
		return future(exec, EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, run);
	}
	
	public static <R> TaskCreator<? extends Future<R>> future(Executor exec, Barrier[] staticBarriers, SupplierWithDelay<R> run) {
		return future(exec, EMPTY_SYNCLOCK_ARRAY, staticBarriers, run);
	}
	
	public static <R> TaskCreator<? extends Future<R>> future(Executor exec, SyncLock[] staticLocks, Barrier[] staticBarriers, SupplierWithDelay<R> run) {
		return (locks, barriers) -> new FutureTask<>(mergeIfNeeded(SyncLock[]::new, staticLocks, locks), mergeIfNeeded(Barrier[]::new, staticBarriers, barriers)) {
			@Override
			protected synchronized void submit1(Runnable toRun) {
				exec.execute(toRun);
			}
			
			@Override
			protected R execute0() throws DelayTask {
				return run.get();
			}
		};
	}
	
	//MultiTask custom
	public static TaskCreator<? extends Barrier> multiCustom(Function<Barrier, Barrier> setup) {
		return multiCustom(EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, setup);
	}
	
	public static TaskCreator<? extends Barrier> multiCustom(Barrier[] staticBarriers, Function<Barrier, Barrier> setup) {
		return multiCustom(EMPTY_SYNCLOCK_ARRAY, staticBarriers, setup);
	}
	
	public static TaskCreator<? extends Barrier> multiCustom(SyncLock[] staticLocks, Barrier[] staticBarriers, Function<Barrier, Barrier> setup) {
		return (locks, barriers) -> new MultiTask(mergeIfNeeded(SyncLock[]::new, staticLocks, locks), mergeIfNeeded(Barrier[]::new, staticBarriers, barriers)) {
			@Override
			protected Barrier setup(Barrier start) {
				return setup.apply(start);
			}
		};
	}
	
	//MultiTask sequential
	public static TaskCreator<? extends Barrier> sequential(Collection<? extends TaskCreator> tasks) {
		return sequential(EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, tasks);
	}
	
	public static TaskCreator<? extends Barrier> sequential(Barrier[] staticBarriers, Collection<? extends TaskCreator> tasks) {
		return sequential(EMPTY_SYNCLOCK_ARRAY, staticBarriers, tasks);
	}
	
	public static TaskCreator<? extends Barrier> sequential(SyncLock[] staticLocks, Barrier[] staticBarriers, Collection<? extends TaskCreator> tasks) {
		return (locks, barriers) -> new MultiTask(mergeIfNeeded(SyncLock[]::new, staticLocks, locks), mergeIfNeeded(Barrier[]::new, staticBarriers, barriers)) {
			@Override
			protected Barrier setup(Barrier start) {
				Barrier current = start;
				for (TaskCreator taskCreator : tasks) {
					current = taskCreator.submit(current);
				}
				return current;
			}
		};
	}
	
	//MultiTask parallel
	public static TaskCreator<? extends Barrier> parallel(Collection<? extends TaskCreator> tasks) {
		return parallel(EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY, tasks);
	}
	
	public static TaskCreator<? extends Barrier> parallel(Barrier[] staticBarriers, Collection<? extends TaskCreator> tasks) {
		return parallel(EMPTY_SYNCLOCK_ARRAY, staticBarriers, tasks);
	}
	
	public static TaskCreator<? extends Barrier> parallel(SyncLock[] staticLocks, Barrier[] staticBarriers, Collection<? extends TaskCreator> tasks) {
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
