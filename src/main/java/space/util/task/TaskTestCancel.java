package space.util.task;

import space.util.task.impl.RunnableTaskImpl;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskTestCancel {
	
	public static final Executor GLOBAL_POOL = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
	
	public static Task printTest(String name) {
		return new RunnableTaskImpl() {
			@Override
			protected void execute() {
				System.out.println("create Window: '" + name + "'");
			}
			
			@Override
			protected void submit1(Runnable toRun) {
				GLOBAL_POOL.execute(toRun);
			}
		};
	}
	
	public static void main(String[] args) throws Exception {
		try {
			System.out.print(""); //initialization
			test();
		} finally {
			if (GLOBAL_POOL instanceof ThreadPoolExecutor)
				((ThreadPoolExecutor) GLOBAL_POOL).shutdown();
		}
	}
	
	public static void test() throws InterruptedException {
		Task[] name = new Task[1];
		RunnableTaskImpl.create(GLOBAL_POOL, () -> {
			name[0] = printTest("name").submit();
			name[0].cancel(false);
		}).submit().await();
		name[0].await();
	}
}
