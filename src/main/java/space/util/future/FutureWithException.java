package space.util.future;

import space.util.barrier.Barrier;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface FutureWithException<R, EX extends Throwable> extends Barrier {
	
	R awaitGet() throws InterruptedException, EX;
	
	R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX;
	
	R assertGet() throws FutureNotFinishedException, EX;
	
	interface FutureWith2Exception<R, EX1 extends Throwable, EX2 extends Throwable> extends Barrier {
		
		R awaitGet() throws InterruptedException, EX1, EX2;
		
		R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX1, EX2;
		
		R assertGet() throws FutureNotFinishedException, EX1, EX2;
	}
	
	interface FutureWith3Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable> extends Barrier {
		
		R awaitGet() throws InterruptedException, EX1, EX2, EX3;
		
		R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX1, EX2, EX3;
		
		R assertGet() throws FutureNotFinishedException, EX1, EX2, EX3;
	}
	
	interface FutureWith4Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable> extends Barrier {
		
		R awaitGet() throws InterruptedException, EX1, EX2, EX3, EX4;
		
		R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX1, EX2, EX3, EX4;
		
		R assertGet() throws FutureNotFinishedException, EX1, EX2, EX3, EX4;
	}
	
	interface FutureWith5Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable, EX5 extends Throwable> extends Barrier {
		
		R awaitGet() throws InterruptedException, EX1, EX2, EX3, EX4, EX5;
		
		R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX1, EX2, EX3, EX4, EX5;
		
		R assertGet() throws FutureNotFinishedException, EX1, EX2, EX3, EX4, EX5;
	}
}
