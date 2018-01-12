package space.util.task.function.chained;

import space.util.baseobject.BaseObjectInit;
import space.util.delegate.list.IntList;
import space.util.gui.monofont.MonofontGuiApi;
import space.util.string.builder.CharBufferBuilder2D;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.task.basic.ITask;
import space.util.task.function.typehandler.TypeConsumer;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ChainedTest {
	
	public static final int thCnt = 4;
	public static final boolean prestart = false;
	public static ThreadPoolExecutor pool = new ThreadPoolExecutor(thCnt, thCnt, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
	
	public static final boolean singlethread = true;
	public static final boolean doCancel = true;
	public static final boolean workCheckInterrupt = true;
	
	public static void main(String[] args) throws Exception {
		ToStringHelper.setDefault(MonofontGuiApi.TSH);
		BaseObjectInit.init();
		try {
			if (prestart)
				pool.prestartAllCoreThreads();
			
			ChainedTaskBuilder<Consumer<Integer>> builder = new ChainedTaskBuilder<>(singlethread);
			builder.addTask("last", 5, integer -> doWork("last", integer, ChainedTest::actualWork));
			builder.addTask("require", new String[] {"no"}, integer -> doWork("require", integer, ChainedTest::actualWork));
			builder.addTask("no", integer -> doWork("no", integer, ChainedTest::actualWork));
			builder.addTask("priority", -1, integer -> doWork("priority", integer, ChainedTest::actualWork));
			builder.addTask("requiredBy", null, new String[] {"no"}, integer -> doWork("requiredBy", integer, ChainedTest::actualWork));
			builder.addTask("no2", integer -> doWork("no2", integer, ChainedTest::actualWork));
			builder.addTask("no1", integer -> doWork("no1", integer, ChainedTest::actualWork));
			builder.addTask("no3", integer -> doWork("no3", integer, ChainedTest::actualWork));
			builder.addTask("no4", integer -> doWork("no4", integer, ChainedTest::actualWork));
			builder.addTask("ERROR", integer -> doWork("ERROR", integer, ChainedTest::actualWorkError));
			builder.addTask("no5", integer -> doWork("no5", integer, ChainedTest::actualWork));
			builder.addTask("no6", integer -> doWork("no6", integer, ChainedTest::actualWork));
			builder.addTask("no42", integer -> doWork("no42", integer, ChainedTest::actualWork));
			builder.addTask("requiredByPri", null, new String[] {"priority"}, integer -> doWork("requiredByPri", integer, ChainedTest::actualWork));
			
			System.out.println(builder);
			
			ITask task = builder.execute(pool, new TypeConsumer<>(42));
			
			if (doCancel) {
				Thread.sleep(1000);
				System.out.println("--- CANCEL!");
				task.cancel(true);
			}
			
			task.await();
			System.out.println("--- DONE!");
			Throwable th = task.getException();
			if (th != null)
				th.printStackTrace();
		} finally {
			pool.shutdown();
		}
	}
	
	public static void doWork(String uuid, int num, Runnable work) {
		System.out.println("[" + Thread.currentThread().getName() + "] [" + uuid + "] Working...");
		try {
			work.run();
		} catch (Throwable e) {
			System.out.println("[" + Thread.currentThread().getName() + "] [" + uuid + "] CRASH!!! " + e.getMessage());
			throw e;
		}
		System.out.println("[" + Thread.currentThread().getName() + "] [" + uuid + "] Done! id is " + num);
	}
	
	public static void actualWork() {
		int n = (Integer.MAX_VALUE - 1) / 64;
		IntList list = new IntList();
		int i = 1;
		for (; i < n; i++) {
			if (workCheckInterrupt && Thread.interrupted())
				break;
			if (n % i == 0)
				list.add(i);
		}
		System.out.println(new CharBufferBuilder2D<>().append(i).append(" -> ").append(list).toString());
	}
	
	public static void actualWorkError() {
		throw new RuntimeException("This is a test!");
	}
}
