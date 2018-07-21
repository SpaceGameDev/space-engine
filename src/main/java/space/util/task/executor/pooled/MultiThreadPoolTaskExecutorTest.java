package space.util.task.executor.pooled;

import space.util.task.executor.TaskExecutor;

import java.util.concurrent.LinkedBlockingQueue;

public class MultiThreadPoolTaskExecutorTest {
	
	public static void main(String[] args) throws InterruptedException {
		MultiThreadPoolTaskExecutor manager = new MultiThreadPoolTaskExecutor();
		AbstractThreadPoolTaskExecutor pool = manager.createPool(new LinkedBlockingQueue<>(), 8);
		AbstractThreadPoolTaskExecutor printPool = manager.createPool(new LinkedBlockingQueue<>(), 1);
		for (int i = 0; i < 1024; i++)
			pool.execute(new TestRunnable("Submit" + i, pool, printPool, 400));
		
		Thread.sleep(1000);
		System.out.println("shut down!");
		
		manager.shutdownAll();
	}
	
	public static class TestRunnable implements Runnable {
		
		final String name;
		final TaskExecutor pool;
		final TaskExecutor printPool;
		final int id;
		
		public TestRunnable(String name, TaskExecutor pool, TaskExecutor printPool, int id) {
			this.name = name;
			this.pool = pool;
			this.printPool = printPool;
			this.id = id;
		}
		
		@Override
		public void run() {
//			try {
//				Thread.sleep(20);
//			} catch (InterruptedException ignored) {
//
//			}
			final int res = work(id, 40960);
			printPool.execute(() -> System.out.println(name + ": " + id + " on " + Thread.currentThread().getName() + " -> " + res + " - " + work(id, 32000)));
			if (id > 0)
				pool.execute(new TestRunnable(name, pool, printPool, id - 1));
		}
	}
	
	public static int work(int id, int until) {
		int res = 0;
		for (int i = 1; i < until; i++)
			if (id % i == 0)
				res++;
		return res;
	}
}
