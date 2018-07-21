package space.util.event.dependency;

import space.util.delegate.specific.IntList;
import space.util.event.typehandler.TypeConsumer;
import space.util.gui.monofont.MonofontGuiApi;
import space.util.string.builder.CharBufferBuilder2D;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.task.Task;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DependencyEventTest {
	
	public static final int thCnt = 4;
	public static final boolean prestart = false;
	public static ThreadPoolExecutor pool = new ThreadPoolExecutor(thCnt, thCnt, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
	
	public static final boolean singlethread = false;
	public static final boolean doCancel = false;
	public static final boolean workCheckInterrupt = true;
	public static final boolean exitOnError = false;
	
	public static void main(String[] args) throws Exception {
		ToStringHelper.setDefault(MonofontGuiApi.TSH);
		try {
			if (prestart)
				pool.prestartAllCoreThreads();
			
			DependencyEventCreator<Consumer<Integer>> builder = singlethread ? new DependencyEventBuilderSinglethread<>() : new DependencyEventBuilderMultithread<>();
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("last", integer, DependencyEventTest::actualWork), "last", 5));
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("require", integer, DependencyEventTest::actualWork), "require", new String[] {"no"}));
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("no", integer, DependencyEventTest::actualWork), "no"));
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("priority", integer, DependencyEventTest::actualWork), "priority", -1));
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("requiredBy", integer, DependencyEventTest::actualWork), "requiredBy", null, new String[] {"no"}));
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("no2", integer, DependencyEventTest::actualWork), "no2"));
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("no1", integer, DependencyEventTest::actualWork), "no1"));
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("no3", integer, DependencyEventTest::actualWork), "no3"));
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("no4", integer, DependencyEventTest::actualWork), "no4"));
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("ERROR", integer, DependencyEventTest::actualWorkError), "ERROR"));
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("no5", integer, DependencyEventTest::actualWork), "no5"));
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("no6", integer, DependencyEventTest::actualWork), "no6"));
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("no42", integer, DependencyEventTest::actualWork), "no42"));
			builder.addHook(DependencyEventEntry.fromFunction(integer -> doWork("requiredByPri", integer, DependencyEventTest::actualWork), "requiredByPri", null, new String[] {"priority"}));
			
			System.out.println(builder);
			
			Task task = builder.execute(new TypeConsumer<>(42), (task1, thread, e) -> {
				System.err.println("Error on Thread " + thread + " with Task: ");
				System.err.println(task1);
				e.printStackTrace(System.err);
				if (exitOnError)
					System.exit(1);
			}, pool);
			
			if (doCancel) {
				Thread.sleep(1000);
				System.out.println("--- CANCEL!");
				task.cancel(true);
			}
			
			task.await();
			System.out.println("--- DONE!");
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
