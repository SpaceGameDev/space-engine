package space.util.task;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.barrier.Barrier;

public interface CallableTaskWithException<R, EX extends Throwable> extends Task {
	
	@Override
	@NotNull CallableTaskWithException<R, EX> submit();
	
	@Override
	@NotNull CallableTaskWithException<R, EX> submit(Barrier... barriers);
	
	R get() throws EX, InterruptedException;
	
	@Nullable R tryGet() throws EX;
	
	interface CallableTaskWith2Exception<R, EX1 extends Throwable, EX2 extends Throwable> extends Task {
		
		@Override
		@NotNull CallableTaskWith2Exception<R, EX1, EX2> submit();
		
		@Override
		@NotNull CallableTaskWith2Exception<R, EX1, EX2> submit(Barrier... barriers);
		
		R get() throws EX1, EX2, InterruptedException;
		
		@Nullable R tryGet() throws EX1, EX2;
	}
	
	interface CallableTaskWith3Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable> extends Task {
		
		@Override
		@NotNull CallableTaskWith3Exception<R, EX1, EX2, EX3> submit();
		
		@Override
		@NotNull CallableTaskWith3Exception<R, EX1, EX2, EX3> submit(Barrier... barriers);
		
		R get() throws EX1, EX2, EX3, InterruptedException;
		
		@Nullable R tryGet() throws EX1, EX2, EX3;
	}
	
	interface CallableTaskWith4Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable> extends Task {
		
		@Override
		@NotNull CallableTaskWith4Exception<R, EX1, EX2, EX3, EX4> submit();
		
		@Override
		@NotNull CallableTaskWith4Exception<R, EX1, EX2, EX3, EX4> submit(Barrier... barriers);
		
		R get() throws EX1, EX2, EX3, EX4, InterruptedException;
		
		@Nullable R tryGet() throws EX1, EX2, EX3, EX4;
	}
	
	interface CallableTaskWith5Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable, EX5 extends Throwable> extends Task {
		
		@Override
		@NotNull CallableTaskWith5Exception<R, EX1, EX2, EX3, EX4, EX5> submit();
		
		@Override
		@NotNull CallableTaskWith5Exception<R, EX1, EX2, EX3, EX4, EX5> submit(Barrier... barriers);
		
		R get() throws EX1, EX2, EX3, EX4, EX5, InterruptedException;
		
		@Nullable R tryGet() throws EX1, EX2, EX3, EX4, EX5;
	}
}
