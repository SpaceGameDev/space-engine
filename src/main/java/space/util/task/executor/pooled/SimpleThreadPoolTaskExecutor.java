package space.util.task.executor.pooled;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static space.util.task.executor.pooled.AbstractThreadPoolTaskExecutor.State.*;

public class SimpleThreadPoolTaskExecutor extends AbstractThreadPoolTaskExecutor {
	
	protected volatile long workerCheckTimeoutMillis = 100L;
	protected volatile State state = RUNNING;
	
	public SimpleThreadPoolTaskExecutor(@NotNull BlockingQueue<Runnable> queue, int workersCount) {
		super(queue, workersCount);
	}
	
	public SimpleThreadPoolTaskExecutor(@NotNull BlockingQueue<Runnable> queue, int workersCount, @NotNull ThreadFactory threadFactory) {
		super(queue, workersCount, threadFactory);
	}
	
	//setter getter
	public void setWorkerCheckTimeoutMillis(long workerCheckTimeoutMillis) {
		this.workerCheckTimeoutMillis = workerCheckTimeoutMillis;
	}
	
	@Override
	public State getState() {
		return state;
	}
	
	//worker
	@Override
	public void runWorker() {
		while (state == RUNNING) {
			try {
				Runnable work = queue.poll(workerCheckTimeoutMillis, TimeUnit.MILLISECONDS);
				if (work != null)
					work.run();
			} catch (Throwable e) {
				if (exceptionHandler != null)
					exceptionHandler.uncaughtException(Thread.currentThread(), e);
			}
		}
		
		while (true) {
			try {
				Runnable runnable = queue.poll();
				if (runnable == null)
					break;
				runnable.run();
			} catch (Throwable e) {
				if (exceptionHandler != null)
					exceptionHandler.uncaughtException(Thread.currentThread(), e);
			}
		}
		
		state = OFF;
	}
	
	@Override
	public synchronized void shutdown() {
		if (state != RUNNING)
			return;
		state = TURNING_OFF;
	}
}
