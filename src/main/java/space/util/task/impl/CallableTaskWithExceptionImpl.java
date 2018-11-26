package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import space.util.future.FutureNotFinishedException;
import space.util.sync.barrier.Barrier;
import space.util.task.CallableTaskWithException;
import space.util.task.TaskState;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class CallableTaskWithExceptionImpl<R, EX extends Throwable> extends RunnableTask implements CallableTaskWithException<R, EX> {
	
	protected final Class<EX> exceptionClass;
	
	protected R ret;
	protected EX exception;
	
	public CallableTaskWithExceptionImpl(Class<EX> exceptionClass) {
		this.exceptionClass = exceptionClass;
	}
	
	//delegate
	@Override
	public @NotNull CallableTaskWithException<R, EX> submit() {
		super.submit();
		return this;
	}
	
	@Override
	public synchronized @NotNull CallableTaskWithException<R, EX> submit(@NotNull Barrier... barriers) {
		super.submit(barriers);
		return this;
	}
	
	//execute
	@Override
	protected void execute() {
		try {
			ret = execute0();
		} catch (RuntimeException | Error e) {
			if (exceptionClass.isInstance(e))
				exception = (EX) e;
			else
				throw e;
		} catch (Throwable e) {
			if (exceptionClass.isInstance(e))
				//noinspection unchecked
				exception = (EX) e;
			else
				throw new IllegalStateException("Exception caught that cannot have been thrown", e);
		}
	}
	
	protected abstract R execute0() throws EX;
	
	//get
	@Override
	public R awaitGet() throws InterruptedException, EX {
		await();
		return getInternal();
	}
	
	@Override
	public R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX {
		await(time, unit);
		return getInternal();
	}
	
	@Override
	public R assertGet() throws FutureNotFinishedException, EX {
		if (getState() != TaskState.FINISHED)
			throw new FutureNotFinishedException(this);
		return getInternal();
	}
	
	protected synchronized R getInternal() throws EX {
		if (exception != null)
			throw exception;
		return ret;
	}
	
	public static abstract class BaseCallableTaskWith2Exception<R, EX1 extends Throwable, EX2 extends Throwable> extends RunnableTask implements CallableTaskWith2Exception<R, EX1, EX2> {
		
		protected final Class<EX1> exceptionClass1;
		protected final Class<EX2> exceptionClass2;
		
		protected R ret;
		protected EX1 exception1;
		protected EX2 exception2;
		
		public BaseCallableTaskWith2Exception(Class<EX1> exceptionClass1, Class<EX2> exceptionClass2) {
			this.exceptionClass1 = exceptionClass1;
			this.exceptionClass2 = exceptionClass2;
		}
		
		//delegate
		@Override
		public @NotNull CallableTaskWith2Exception<R, EX1, EX2> submit() {
			super.submit();
			return this;
		}
		
		@Override
		public synchronized @NotNull CallableTaskWith2Exception<R, EX1, EX2> submit(@NotNull Barrier... barriers) {
			super.submit(barriers);
			return this;
		}
		
		//execute
		@Override
		protected void execute() {
			try {
				ret = execute0();
			} catch (RuntimeException | Error e) {
				if (exceptionClass1.isInstance(e))
					exception1 = (EX1) e;
				else if (exceptionClass2.isInstance(e))
					exception2 = (EX2) e;
				else
					throw e;
			} catch (Throwable e) {
				if (exceptionClass1.isInstance(e))
					//noinspection unchecked
					exception1 = (EX1) e;
				else if (exceptionClass2.isInstance(e))
					//noinspection unchecked
					exception2 = (EX2) e;
				else
					throw new IllegalStateException("Exception caught that cannot have been thrown", e);
			}
		}
		
		protected abstract R execute0() throws EX1, EX2;
		
		//get
		@Override
		public R awaitGet() throws InterruptedException, EX1, EX2 {
			await();
			return getInternal();
		}
		
		@Override
		public R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX1, EX2 {
			await(time, unit);
			return getInternal();
		}
		
		@Override
		public R assertGet() throws FutureNotFinishedException, EX1, EX2 {
			if (getState() != TaskState.FINISHED)
				throw new FutureNotFinishedException(this);
			return getInternal();
		}
		
		protected synchronized R getInternal() throws EX1, EX2 {
			if (exception1 != null)
				throw exception1;
			if (exception2 != null)
				throw exception2;
			return ret;
		}
	}
	
	public static abstract class BaseCallableTaskWith3Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable> extends RunnableTask implements CallableTaskWith3Exception<R, EX1, EX2, EX3> {
		
		protected final Class<EX1> exceptionClass1;
		protected final Class<EX2> exceptionClass2;
		protected final Class<EX3> exceptionClass3;
		
		protected R ret;
		protected EX1 exception1;
		protected EX2 exception2;
		protected EX3 exception3;
		
		public BaseCallableTaskWith3Exception(Class<EX1> exceptionClass1, Class<EX2> exceptionClass2, Class<EX3> exceptionClass3) {
			this.exceptionClass1 = exceptionClass1;
			this.exceptionClass2 = exceptionClass2;
			this.exceptionClass3 = exceptionClass3;
		}
		
		//delegate
		@Override
		public @NotNull CallableTaskWith3Exception<R, EX1, EX2, EX3> submit() {
			super.submit();
			return this;
		}
		
		@Override
		public synchronized @NotNull CallableTaskWith3Exception<R, EX1, EX2, EX3> submit(@NotNull Barrier... barriers) {
			super.submit(barriers);
			return this;
		}
		
		//execute
		@Override
		protected void execute() {
			try {
				ret = execute0();
			} catch (RuntimeException | Error e) {
				if (exceptionClass1.isInstance(e))
					exception1 = (EX1) e;
				else if (exceptionClass2.isInstance(e))
					exception2 = (EX2) e;
				else if (exceptionClass3.isInstance(e))
					exception3 = (EX3) e;
				else
					throw e;
			} catch (Throwable e) {
				if (exceptionClass1.isInstance(e))
					//noinspection unchecked
					exception1 = (EX1) e;
				else if (exceptionClass2.isInstance(e))
					//noinspection unchecked
					exception2 = (EX2) e;
				else if (exceptionClass3.isInstance(e))
					//noinspection unchecked
					exception3 = (EX3) e;
				else
					throw new IllegalStateException("Exception caught that cannot have been thrown", e);
			}
		}
		
		protected abstract R execute0() throws EX1, EX2, EX3;
		
		//get
		@Override
		public R awaitGet() throws InterruptedException, EX1, EX2, EX3 {
			await();
			return getInternal();
		}
		
		@Override
		public R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX1, EX2, EX3 {
			await(time, unit);
			return getInternal();
		}
		
		@Override
		public R assertGet() throws FutureNotFinishedException, EX1, EX2, EX3 {
			if (getState() != TaskState.FINISHED)
				throw new FutureNotFinishedException(this);
			return getInternal();
		}
		
		protected synchronized R getInternal() throws EX1, EX2, EX3 {
			if (exception1 != null)
				throw exception1;
			if (exception2 != null)
				throw exception2;
			if (exception3 != null)
				throw exception3;
			return ret;
		}
	}
	
	public static abstract class BaseCallableTaskWith4Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable> extends RunnableTask implements CallableTaskWith4Exception<R, EX1, EX2, EX3, EX4> {
		
		protected final Class<EX1> exceptionClass1;
		protected final Class<EX2> exceptionClass2;
		protected final Class<EX3> exceptionClass3;
		protected final Class<EX4> exceptionClass4;
		
		protected R ret;
		protected EX1 exception1;
		protected EX2 exception2;
		protected EX3 exception3;
		protected EX4 exception4;
		
		public BaseCallableTaskWith4Exception(Class<EX1> exceptionClass1, Class<EX2> exceptionClass2, Class<EX3> exceptionClass3, Class<EX4> exceptionClass4) {
			this.exceptionClass1 = exceptionClass1;
			this.exceptionClass2 = exceptionClass2;
			this.exceptionClass3 = exceptionClass3;
			this.exceptionClass4 = exceptionClass4;
		}
		
		//delegate
		@Override
		public @NotNull CallableTaskWith4Exception<R, EX1, EX2, EX3, EX4> submit() {
			super.submit();
			return this;
		}
		
		@Override
		public synchronized @NotNull CallableTaskWith4Exception<R, EX1, EX2, EX3, EX4> submit(@NotNull Barrier... barriers) {
			super.submit(barriers);
			return this;
		}
		
		//execute
		@Override
		protected void execute() {
			try {
				ret = execute0();
			} catch (RuntimeException | Error e) {
				if (exceptionClass1.isInstance(e))
					exception1 = (EX1) e;
				else if (exceptionClass2.isInstance(e))
					exception2 = (EX2) e;
				else if (exceptionClass3.isInstance(e))
					exception3 = (EX3) e;
				else if (exceptionClass4.isInstance(e))
					exception4 = (EX4) e;
				else
					throw e;
			} catch (Throwable e) {
				if (exceptionClass1.isInstance(e))
					//noinspection unchecked
					exception1 = (EX1) e;
				else if (exceptionClass2.isInstance(e))
					//noinspection unchecked
					exception2 = (EX2) e;
				else if (exceptionClass3.isInstance(e))
					//noinspection unchecked
					exception3 = (EX3) e;
				else if (exceptionClass4.isInstance(e))
					//noinspection unchecked
					exception4 = (EX4) e;
				else
					throw new IllegalStateException("Exception caught that cannot have been thrown", e);
			}
		}
		
		protected abstract R execute0() throws EX1, EX2, EX3, EX4;
		
		//get
		@Override
		public R awaitGet() throws InterruptedException, EX1, EX2, EX3, EX4 {
			await();
			return getInternal();
		}
		
		@Override
		public R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX1, EX2, EX3, EX4 {
			await(time, unit);
			return getInternal();
		}
		
		@Override
		public R assertGet() throws FutureNotFinishedException, EX1, EX2, EX3, EX4 {
			if (getState() != TaskState.FINISHED)
				throw new FutureNotFinishedException(this);
			return getInternal();
		}
		
		protected synchronized R getInternal() throws EX1, EX2, EX3, EX4 {
			if (exception1 != null)
				throw exception1;
			if (exception2 != null)
				throw exception2;
			if (exception3 != null)
				throw exception3;
			if (exception4 != null)
				throw exception4;
			return ret;
		}
	}
	
	public static abstract class BaseCallableTaskWith5Exception<R, EX1 extends Throwable, EX2 extends Throwable, EX3 extends Throwable, EX4 extends Throwable, EX5 extends Throwable> extends RunnableTask implements CallableTaskWith5Exception<R, EX1, EX2, EX3, EX4, EX5> {
		
		protected final Class<EX1> exceptionClass1;
		protected final Class<EX2> exceptionClass2;
		protected final Class<EX3> exceptionClass3;
		protected final Class<EX4> exceptionClass4;
		protected final Class<EX5> exceptionClass5;
		
		protected R ret;
		protected EX1 exception1;
		protected EX2 exception2;
		protected EX3 exception3;
		protected EX4 exception4;
		protected EX5 exception5;
		
		public BaseCallableTaskWith5Exception(Class<EX1> exceptionClass1, Class<EX2> exceptionClass2, Class<EX3> exceptionClass3, Class<EX4> exceptionClass4, Class<EX5> exceptionClass5) {
			this.exceptionClass1 = exceptionClass1;
			this.exceptionClass2 = exceptionClass2;
			this.exceptionClass3 = exceptionClass3;
			this.exceptionClass4 = exceptionClass4;
			this.exceptionClass5 = exceptionClass5;
		}
		
		//delegate
		@Override
		public @NotNull CallableTaskWith5Exception<R, EX1, EX2, EX3, EX4, EX5> submit() {
			super.submit();
			return this;
		}
		
		@Override
		public synchronized @NotNull CallableTaskWith5Exception<R, EX1, EX2, EX3, EX4, EX5> submit(@NotNull Barrier... barriers) {
			super.submit(barriers);
			return this;
		}
		
		//execute
		@Override
		protected void execute() {
			try {
				ret = execute0();
			} catch (RuntimeException | Error e) {
				if (exceptionClass1.isInstance(e))
					exception1 = (EX1) e;
				else if (exceptionClass2.isInstance(e))
					exception2 = (EX2) e;
				else if (exceptionClass3.isInstance(e))
					exception3 = (EX3) e;
				else if (exceptionClass4.isInstance(e))
					exception4 = (EX4) e;
				else if (exceptionClass5.isInstance(e))
					exception5 = (EX5) e;
				else
					throw e;
			} catch (Throwable e) {
				if (exceptionClass1.isInstance(e))
					//noinspection unchecked
					exception1 = (EX1) e;
				else if (exceptionClass2.isInstance(e))
					//noinspection unchecked
					exception2 = (EX2) e;
				else if (exceptionClass3.isInstance(e))
					//noinspection unchecked
					exception3 = (EX3) e;
				else if (exceptionClass4.isInstance(e))
					//noinspection unchecked
					exception4 = (EX4) e;
				else if (exceptionClass5.isInstance(e))
					//noinspection unchecked
					exception5 = (EX5) e;
				else
					throw new IllegalStateException("Exception caught that cannot have been thrown", e);
			}
		}
		
		protected abstract R execute0() throws EX1, EX2, EX3, EX4, EX5;
		
		//get
		@Override
		public R awaitGet() throws InterruptedException, EX1, EX2, EX3, EX4, EX5 {
			await();
			return getInternal();
		}
		
		@Override
		public R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException, EX1, EX2, EX3, EX4, EX5 {
			await(time, unit);
			return getInternal();
		}
		
		@Override
		public R assertGet() throws FutureNotFinishedException, EX1, EX2, EX3, EX4, EX5 {
			if (getState() != TaskState.FINISHED)
				throw new FutureNotFinishedException(this);
			return getInternal();
		}
		
		protected synchronized R getInternal() throws EX1, EX2, EX3, EX4, EX5 {
			if (exception1 != null)
				throw exception1;
			if (exception2 != null)
				throw exception2;
			if (exception3 != null)
				throw exception3;
			if (exception4 != null)
				throw exception4;
			if (exception5 != null)
				throw exception5;
			return ret;
		}
	}
}
