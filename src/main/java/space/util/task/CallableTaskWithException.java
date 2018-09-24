package space.util.task;

import org.jetbrains.annotations.NotNull;
import space.util.barrier.Barrier;
import space.util.future.FutureWithException;

public interface CallableTaskWithException<R, EX extends Throwable> extends Task, FutureWithException<R, EX> {
	
	@Override
	@NotNull CallableTaskWithException<R, EX> submit();
	
	@Override
	@NotNull CallableTaskWithException<R, EX> submit(Barrier... barriers);
	
	interface CallableTaskWith2Exception<R, EX1 extends Throwable, EX2 extends Throwable> extends Task, FutureWith2Exception<R, EX1, EX2> {
		
		@Override
		@NotNull CallableTaskWith2Exception<R, EX1, EX2> submit();
		
		@Override
		@NotNull CallableTaskWith2Exception<R, EX1, EX2> submit(Barrier... barriers);
	}
	
	interface CallableTaskWith3Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable> extends Task, FutureWith3Exception<R, EX1, EX2, EX3> {
		
		@Override
		@NotNull CallableTaskWith3Exception<R, EX1, EX2, EX3> submit();
		
		@Override
		@NotNull CallableTaskWith3Exception<R, EX1, EX2, EX3> submit(Barrier... barriers);
	}
	
	interface CallableTaskWith4Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable> extends Task, FutureWith4Exception<R, EX1, EX2, EX3, EX4> {
		
		@Override
		@NotNull CallableTaskWith4Exception<R, EX1, EX2, EX3, EX4> submit();
		
		@Override
		@NotNull CallableTaskWith4Exception<R, EX1, EX2, EX3, EX4> submit(Barrier... barriers);
	}
	
	interface CallableTaskWith5Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable, EX5 extends Throwable> extends Task, FutureWith5Exception<R, EX1, EX2, EX3, EX4, EX5> {
		
		@Override
		@NotNull CallableTaskWith5Exception<R, EX1, EX2, EX3, EX4, EX5> submit();
		
		@Override
		@NotNull CallableTaskWith5Exception<R, EX1, EX2, EX3, EX4, EX5> submit(Barrier... barriers);
	}
}
