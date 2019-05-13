package space.engine.simpleQueue.pool;

import org.jetbrains.annotations.NotNull;
import space.engine.simpleQueue.SimpleQueue;
import space.engine.sync.barrier.Barrier;
import space.engine.sync.barrier.BarrierImpl;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.VarHandle;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class SimpleMessagePool<MSG> {
	
	public static final long DEFAULT_SLEEP_TIME_WHEN_DRY = 10L;
	private static final VarHandle THREADISSLEEPING;
	
	static {
		try {
			Lookup lookup = MethodHandles.lookup();
			THREADISSLEEPING = lookup.findVarHandle(SimpleMessagePool.class, "threadIsSleeping", boolean.class);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	private final @NotNull SimpleQueue<MSG> queue;
	private final Thread[] threads;
	private volatile boolean threadIsSleeping = false;
	
	private volatile boolean isRunning = true;
	private final AtomicInteger exitCountdown;
	private final BarrierImpl exitBarrier;
	
	public SimpleMessagePool(int threadCnt, @NotNull SimpleQueue<MSG> queue) {
		this(threadCnt, queue, Executors.defaultThreadFactory(), DEFAULT_SLEEP_TIME_WHEN_DRY);
	}
	
	public SimpleMessagePool(int threadCnt, @NotNull SimpleQueue<MSG> queue, ThreadFactory threadFactory) {
		this(threadCnt, queue, threadFactory, DEFAULT_SLEEP_TIME_WHEN_DRY);
	}
	
	public SimpleMessagePool(int threadCnt, @NotNull SimpleQueue<MSG> queue, ThreadFactory threadFactory, long sleepTimeWhenDry) {
		this.queue = queue;
		this.exitCountdown = new AtomicInteger(threadCnt);
		this.exitBarrier = new BarrierImpl();
		
		Runnable poolMain = () -> {
			while (true) {
				MSG msg = queue.remove();
				if (msg != null) {
					//execute Runnable
					try {
						handle(msg);
					} catch (Throwable e) {
						Thread thread = Thread.currentThread();
						thread.getUncaughtExceptionHandler().uncaughtException(thread, e);
					}
				} else {
					//wait a little and retry
					yield();
					if (!isRunning)
						break;
					
					THREADISSLEEPING.set(this, true);
					LockSupport.park();
				}
			}
			
			if (exitCountdown.decrementAndGet() == 0)
				exitBarrier.triggerNow();
		};
		
		this.threads = IntStream.range(0, threadCnt)
								.mapToObj(i -> threadFactory.newThread(poolMain))
								.peek(Thread::start)
								.toArray(Thread[]::new);
	}
	
	//handle
	
	/**
	 * handle the messsage
	 */
	protected abstract void handle(MSG msg);
	
	/**
	 * no more work in the queue -> will sleep a bit
	 */
	protected void yield() {
	}
	
	//add
	public void add(MSG msg) {
		assertRunning();
		queue.add(msg);
		wakeUpThreads();
	}
	
	public void addAll(Collection<MSG> collection) {
		assertRunning();
		queue.addAll(collection);
		wakeUpThreads();
	}
	
	public void addAll(MSG[] collection) {
		assertRunning();
		queue.addAll(collection);
		wakeUpThreads();
	}
	
	public void addAll(Stream<MSG> collection) {
		assertRunning();
		queue.addAll(collection);
		wakeUpThreads();
	}
	
	//execute
	public void assertRunning() throws RejectedExecutionException {
		if (!isRunning)
			throw new RejectedExecutionException("SimpleThreadPool no longer running");
	}
	
	protected void wakeUpThreads() {
		if (THREADISSLEEPING.compareAndSet(this, true, false))
			for (Thread thread : threads)
				LockSupport.unpark(thread);
	}
	
	//getter
	public @NotNull SimpleQueue<MSG> getQueue() {
		return queue;
	}
	
	//stop
	public Barrier stop() {
		isRunning = false;
		wakeUpThreads();
		return exitBarrier;
	}
	
	public Barrier exitBarrier() {
		return exitBarrier;
	}
}
