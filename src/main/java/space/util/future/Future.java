package space.util.future;

import space.util.barrier.Barrier;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface Future<R> extends Barrier {
	
	R awaitGet() throws InterruptedException;
	
	R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	R assertGet() throws FutureNotFinishedException;
}
