package space.util.future;

import org.jetbrains.annotations.NotNull;
import space.util.barrier.Barrier;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface Future<R> extends Barrier {
	
	R awaitGet() throws InterruptedException;
	
	R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	R assertGet() throws FutureNotFinishedException;
	
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
