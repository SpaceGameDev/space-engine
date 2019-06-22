package space.engine.simpleQueue.pool;

import org.jetbrains.annotations.NotNull;
import space.engine.simpleQueue.SimpleQueue;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.VarHandle;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.LockSupport;

public abstract class SimpleMessagePool<MSG> extends AbstractSimpleMessagePool<MSG> {
	
	private static final VarHandle THREADISSLEEPING;
	
	static {
		try {
			Lookup lookup = MethodHandles.lookup();
			THREADISSLEEPING = lookup.findVarHandle(SimpleMessagePool.class, "threadIsSleeping", boolean.class);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	private volatile boolean threadIsSleeping = false;
	
	public SimpleMessagePool(int threadCnt, @NotNull SimpleQueue<MSG> queue) {
		super(threadCnt, queue);
	}
	
	public SimpleMessagePool(int threadCnt, @NotNull SimpleQueue<MSG> queue, ThreadFactory threadFactory) {
		super(threadCnt, queue, threadFactory);
	}
	
	//park
	protected void park() {
		THREADISSLEEPING.set(this, true);
		LockSupport.park();
	}
	
	protected void unparkThreads() {
		if (THREADISSLEEPING.compareAndSet(this, true, false))
			for (Thread thread : threads)
				LockSupport.unpark(thread);
	}
}
