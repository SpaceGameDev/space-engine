package space.util.sync.future;

import org.jetbrains.annotations.NotNull;
import space.util.sync.barrier.Barrier;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SuppressWarnings("unused")
public interface FutureWithException<R, EX extends Throwable> extends Barrier {
	
	R awaitGet() throws InterruptedException, EX;
	
	R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX;
	
	R assertGet() throws FutureNotFinishedException, EX;
	
	static <R, EX extends Throwable> FutureWithException<R, EX> finished(R get) {
		return new FutureWithException<>() {
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
	
	static <R, EX extends Throwable> FutureWithException<R, EX> finishedException(EX ex) {
		return new FutureWithException<>() {
			@Override
			public R awaitGet() throws EX {
				throw ex;
			}
			
			@Override
			public R awaitGet(long time, TimeUnit unit) throws EX {
				throw ex;
			}
			
			@Override
			public R assertGet() throws FutureNotFinishedException, EX {
				throw ex;
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
	
	interface FutureWith2Exception<R, EX1 extends Throwable, EX2 extends Throwable> extends Barrier {
		
		R awaitGet() throws InterruptedException, EX1, EX2;
		
		R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX1, EX2;
		
		R assertGet() throws FutureNotFinishedException, EX1, EX2;
		
		static <R, EX1 extends Throwable, EX2 extends Throwable> FutureWith2Exception<R, EX1, EX2> finished(R get) {
			return new FutureWith2Exception<>() {
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
	
	interface FutureWith3Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable> extends Barrier {
		
		R awaitGet() throws InterruptedException, EX1, EX2, EX3;
		
		R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX1, EX2, EX3;
		
		R assertGet() throws FutureNotFinishedException, EX1, EX2, EX3;
		
		static <R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable> FutureWith3Exception<R, EX1, EX2, EX3> finished(R get) {
			return new FutureWith3Exception<>() {
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
	
	interface FutureWith4Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable> extends Barrier {
		
		R awaitGet() throws InterruptedException, EX1, EX2, EX3, EX4;
		
		R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX1, EX2, EX3, EX4;
		
		R assertGet() throws FutureNotFinishedException, EX1, EX2, EX3, EX4;
		
		static <R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable> FutureWith4Exception<R, EX1, EX2, EX3, EX4> finished(R get) {
			return new FutureWith4Exception<>() {
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
	
	interface FutureWith5Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable, EX5 extends Throwable> extends Barrier {
		
		R awaitGet() throws InterruptedException, EX1, EX2, EX3, EX4, EX5;
		
		R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX1, EX2, EX3, EX4, EX5;
		
		R assertGet() throws FutureNotFinishedException, EX1, EX2, EX3, EX4, EX5;
		
		static <R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable, EX5 extends Throwable> FutureWith5Exception<R, EX1, EX2, EX3, EX4, EX5> finished(R get) {
			return new FutureWith5Exception<>() {
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
}
