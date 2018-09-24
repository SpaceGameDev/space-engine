package space.util.future;

import space.util.barrier.Barrier;

public interface FutureWithException<R, EX extends Throwable> extends Barrier {
	
	R get() throws InterruptedException, EX;
	
	R tryGet() throws FutureNotFinishedException, EX;
	
	interface FutureWith2Exception<R, EX1 extends Throwable, EX2 extends Throwable> extends Barrier {
		
		R get() throws InterruptedException, EX1, EX2;
		
		R tryGet() throws FutureNotFinishedException, EX1, EX2;
	}
	
	interface FutureWith3Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable> extends Barrier {
		
		R get() throws InterruptedException, EX1, EX2, EX3;
		
		R tryGet() throws FutureNotFinishedException, EX1, EX2, EX3;
	}
	
	interface FutureWith4Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable> extends Barrier {
		
		R get() throws InterruptedException, EX1, EX2, EX3, EX4;
		
		R tryGet() throws FutureNotFinishedException, EX1, EX2, EX3, EX4;
	}
	
	interface FutureWith5Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable, EX5 extends Throwable> extends Barrier {
		
		R get() throws InterruptedException, EX1, EX2, EX3, EX4, EX5;
		
		R tryGet() throws FutureNotFinishedException, EX1, EX2, EX3, EX4, EX5;
	}
}
