package space.util.task.executor.pooled;

import org.jetbrains.annotations.NotNull;
import space.util.task.executor.pooled.AbstractThreadPoolTaskExecutor.State;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadPoolTaskExecutor {
	
	protected volatile State state = State.RUNNING;
	protected volatile long workerCheckTimeoutMillis = 100L;
	protected final AtomicInteger thCount = new AtomicInteger();
	protected final List<ThreadPool> threadPoolList = new ArrayList<>();
	
	public AbstractThreadPoolTaskExecutor createPool(@NotNull BlockingQueue<Runnable> queue, int workerCount) {
		return createPool(queue, workerCount, Executors.defaultThreadFactory());
	}
	
	public AbstractThreadPoolTaskExecutor createPool(@NotNull BlockingQueue<Runnable> queue, int workerCount, @NotNull ThreadFactory threadFactory) {
		if (state == State.OFF)
			throw new IllegalStateException("Manager already shut down!");
		
		ThreadPool threadPool = new ThreadPool(queue, workerCount, threadFactory);
		threadPoolList.add(threadPool);
		return threadPool;
	}
	
	public void setWorkerCheckTimeoutMillis(long workerCheckTimeoutMillis) {
		this.workerCheckTimeoutMillis = workerCheckTimeoutMillis;
	}
	
	public State getState() {
		return state;
	}
	
	//shutdown
	public synchronized void shutdownAll() {
		if (state != State.RUNNING)
			return;
		
		state = State.TURNING_OFF;
	}
	
	private boolean checkExitPossible() {
		if (state == State.OFF)
			return true;
		
		synchronized (this) {
			//actual check here
			if (!threadPoolList.stream().allMatch(pool -> pool.queue.isEmpty()))
				return false;
			
			state = State.OFF;
			return true;
		}
	}
	
	//worker
	private int increaseWorkers(int workerCount) {
		thCount.addAndGet(workerCount);
		return workerCount;
	}
	
	public class ThreadPool extends AbstractThreadPoolTaskExecutor {
		
		public ThreadPool(@NotNull BlockingQueue<Runnable> queue, int workerCount, @NotNull ThreadFactory threadFactory) {
			super(queue, increaseWorkers(workerCount), threadFactory);
		}
		
		@Override
		public void runWorker() {
			while (state == State.RUNNING) {
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
					Runnable work = queue.poll(workerCheckTimeoutMillis, TimeUnit.MILLISECONDS);
					if (work != null)
						work.run();
					else if (checkExitPossible())
						break;
				} catch (Throwable e) {
					if (exceptionHandler != null)
						exceptionHandler.uncaughtException(Thread.currentThread(), e);
				}
			}
		}
		
		@Override
		public State getState() {
			return state;
		}
		
		@Override
		public void shutdown() {
			throw new UnsupportedOperationException();
		}
	}
}
