package space.util.future;

import space.util.barrier.Barrier;

public interface Future<R> extends Barrier {
	
	R get() throws InterruptedException;
	
	R tryGet() throws FutureNotFinishedException;
}
