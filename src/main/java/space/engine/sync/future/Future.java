package space.engine.sync.future;

import org.jetbrains.annotations.NotNull;
import space.engine.sync.barrier.Barrier;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface Future<R> extends BaseFuture<R>, Barrier {
	
	//static
	@SuppressWarnings({"ConstantConditions", "unused"})
	Future<Void> FINISHED_VOID = finished(null);
	
	//abstract
	R awaitGet() throws InterruptedException;
	
	R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	/**
	 * Waits until event is triggered and doesn't return when interrupted.
	 * The interrupt status of this {@link Thread} will be restored.
	 */
	default R awaitGetUninterrupted() {
		boolean interrupted = false;
		try {
			while (true) {
				try {
					return awaitGet();
				} catch (InterruptedException e) {
					interrupted = true;
				}
			}
		} finally {
			if (interrupted)
				Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * Waits until event is triggered with a timeout and doesn't return when interrupted.
	 * The interrupt status of this {@link Thread} will be restored.
	 *
	 * @throws TimeoutException thrown if waiting takes longer than the specified timeout
	 */
	default R awaitGetUninterrupted(long time, TimeUnit unit) throws TimeoutException {
		boolean interrupted = false;
		try {
			while (true) {
				try {
					return awaitGet(time, unit);
				} catch (InterruptedException e) {
					interrupted = true;
				}
			}
		} finally {
			if (interrupted)
				Thread.currentThread().interrupt();
		}
	}
	
	R assertGet() throws FutureNotFinishedException;
	
	//anyException
	@Override
	default R awaitGetAnyException() throws Throwable {
		return awaitGet();
	}
	
	@Override
	default R awaitGetAnyException(long time, TimeUnit unit) throws Throwable {
		return awaitGet(time, unit);
	}
	
	@Override
	default R assertGetAnyException() {
		return assertGet();
	}
	
	//default
	@Override
	default Future<R> dereference() {
		CompletableFuture<R> future = new CompletableFuture<>();
		this.addHook(() -> future.complete(this.assertGet()));
		return future;
	}
	
	//static
	static <R> Future<R> finished(R get) {
		return new Future<>() {
			@Override
			public R awaitGet() {
				return get;
			}
			
			@Override
			public R awaitGet(long time, TimeUnit unit) {
				return get;
			}
			
			@Override
			public R assertGet() throws FutureNotFinishedException {
				return get;
			}
			
			@Override
			public boolean isFinished() {
				return true;
			}
			
			@Override
			public void addHook(@NotNull Runnable run) {
				run.run();
			}
			
			@Override
			public void removeHook(@NotNull Runnable run) {
				run.run();
			}
			
			@Override
			public void await() {
			
			}
			
			@Override
			public void await(long time, TimeUnit unit) {
			
			}
		};
	}
}
