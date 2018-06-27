package space.util.concurrent.task.executor.pool;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.concurrent.task.Task;
import space.util.concurrent.task.executor.TaskExecutor;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;

import static space.util.concurrent.task.executor.pool.AbstractThreadPoolTaskExecutor.State.OFF;

public abstract class AbstractThreadPoolTaskExecutor implements TaskExecutor {
	
	@NotNull
	protected final BlockingQueue<Runnable> queue;
	@NotNull
	public final ThreadFactory threadFactory;
	@Nullable
	protected UncaughtExceptionHandler exceptionHandler;
	@NotNull
	protected final List<Worker> workers = new ArrayList<>();
	
	public AbstractThreadPoolTaskExecutor(@NotNull BlockingQueue<Runnable> queue, int workerCount) {
		this(queue, workerCount, Executors.defaultThreadFactory());
	}
	
	public AbstractThreadPoolTaskExecutor(@NotNull BlockingQueue<Runnable> queue, int workerCount, @NotNull ThreadFactory threadFactory) {
		this.queue = queue;
		this.threadFactory = threadFactory;
		
		for (int i = 0; i < workerCount; i++) {
			Thread th = threadFactory.newThread(this::runWorker);
			workers.add(new Worker(th, i));
			th.start();
		}
	}
	
	//execute
	@Override
	public void executeTask(@NotNull Task task) {
		if (getState() == OFF)
			throw new RejectedExecutionException();
		task.submit(queue::add);
	}
	
	public abstract void runWorker();
	
	//state
	public abstract State getState();
	
	public abstract void shutdown();
	
	//class
	public static class Worker {
		
		@NotNull
		public final Thread th;
		public final int id;
		
		public Worker(@NotNull Thread th, int id) {
			this.th = th;
			this.id = id;
		}
	}
	
	public enum State {
		
		RUNNING,
		TURNING_OFF,
		OFF
		
	}
}
