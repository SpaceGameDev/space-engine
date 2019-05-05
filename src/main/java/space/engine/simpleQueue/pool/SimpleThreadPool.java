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
import java.util.stream.Stream;

public class SimpleThreadPool implements Executor {
	
	public static final long DEFAULT_SLEEP_TIME_WHEN_DRY = 10L;
	
	private final @NotNull SimpleQueue<Runnable> queue;
	
	private volatile boolean isRunning = true;
	private final AtomicInteger exitCountdown;
	private final BarrierImpl exitBarrier;
	
	public SimpleThreadPool(int threadCnt, @NotNull SimpleQueue<Runnable> queue) {
		this(threadCnt, queue, Executors.defaultThreadFactory(), DEFAULT_SLEEP_TIME_WHEN_DRY);
	}
	
	public SimpleThreadPool(int threadCnt, @NotNull SimpleQueue<Runnable> queue, ThreadFactory threadFactory) {
		this(threadCnt, queue, threadFactory, DEFAULT_SLEEP_TIME_WHEN_DRY);
	}
	
	public SimpleThreadPool(int threadCnt, @NotNull SimpleQueue<Runnable> queue, ThreadFactory threadFactory, long sleepTimeWhenDry) {
		this.queue = queue;
		this.exitCountdown = new AtomicInteger(threadCnt);
		this.exitBarrier = new BarrierImpl();
		
		Runnable poolMain = () -> {
			while (true) {
				Runnable run = queue.remove();
				if (run != null) {
					//execute Runnable
					try {
						run.run();
					} catch (Throwable e) {
						Thread thread = Thread.currentThread();
						thread.getUncaughtExceptionHandler().uncaughtException(thread, e);
					}
				} else {
					//wait a little and retry
					if (!isRunning)
						break;
					try {
						Thread.sleep(sleepTimeWhenDry);
					} catch (InterruptedException ignored) {
					
					}
				}
			}
			
			if (exitCountdown.decrementAndGet() == 0)
				exitBarrier.triggerNow();
		};
		
		for (int i = 0; i < threadCnt; i++)
			threadFactory.newThread(poolMain).start();
	}
	
	//execute
	public void assertRunning() throws RejectedExecutionException {
		if (!isRunning)
			throw new RejectedExecutionException("SimpleThreadPool no longer running");
	}
	
	@Override
	public void execute(@NotNull Runnable command) {
		assertRunning();
		queue.add(command);
	}
	
	public void executeAll(@NotNull Collection<@NotNull Runnable> commands) {
		assertRunning();
		queue.addAll(commands);
	}
	
	public void executeAll(@NotNull Runnable[] commands) {
		assertRunning();
		queue.addAll(commands);
	}
	
	public void executeAll(@NotNull Stream<@NotNull Runnable> commands) {
		assertRunning();
		queue.addAll(commands);
	}
	
	//getter
	public @NotNull SimpleQueue<Runnable> getQueue() {
		return queue;
	}
	
	//stop
	public Barrier stop() {
		isRunning = false;
		return exitBarrier;
	}
	
	public Barrier exitBarrier() {
		return exitBarrier;
	}
}
