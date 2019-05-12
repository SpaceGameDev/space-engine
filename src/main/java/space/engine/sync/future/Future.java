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
