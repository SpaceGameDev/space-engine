package space.engine.simpleQueue.pool;

import org.jetbrains.annotations.NotNull;
import space.engine.simpleQueue.SimpleQueue;
import space.engine.sync.barrier.Barrier;
import space.engine.sync.barrier.BarrierImpl;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractSimpleMessagePool<MSG> {
	
	protected final @NotNull SimpleQueue<MSG> queue;
	protected final Thread[] threads;
	
	private volatile boolean isRunning = true;
	private final AtomicInteger exitCountdown;
	private final BarrierImpl exitBarrier;
	
	public AbstractSimpleMessagePool(int threadCnt, @NotNull SimpleQueue<MSG> queue) {
		this(threadCnt, queue, Executors.defaultThreadFactory());
	}
	
	public AbstractSimpleMessagePool(int threadCnt, @NotNull SimpleQueue<MSG> queue, ThreadFactory threadFactory) {
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
					//park thread until woke again
					try {
						handleDone();
					} catch (Throwable e) {
						Thread thread = Thread.currentThread();
						thread.getUncaughtExceptionHandler().uncaughtException(thread, e);
					}
					if (!isRunning)
						break;
					
					park();
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
	protected void handleDone() {
	}
	
	//park
	protected abstract void park();
	
	protected abstract void unparkThreads();
	
	//add
	public void add(MSG msg) {
		assertRunning();
		queue.add(msg);
		unparkThreads();
	}
	
	public void addAll(Collection<MSG> collection) {
		assertRunning();
		queue.addAll(collection);
		unparkThreads();
	}
	
	public void addAll(MSG[] collection) {
		assertRunning();
		queue.addAll(collection);
		unparkThreads();
	}
	
	public void addAll(Stream<MSG> collection) {
		assertRunning();
		queue.addAll(collection);
		unparkThreads();
	}
	
	//execute
	public void assertRunning() throws RejectedExecutionException {
		if (!isRunning)
			throw new RejectedExecutionException("SimpleThreadPool no longer running");
	}
	
	//getter
	public @NotNull SimpleQueue<MSG> getQueue() {
		return queue;
	}
	
	//stop
	public Barrier stop() {
		isRunning = false;
		unparkThreads();
		return exitBarrier;
	}
	
	public Barrier exitBarrier() {
		return exitBarrier;
	}
}
