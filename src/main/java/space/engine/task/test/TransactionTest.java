package space.engine.task.test;

import space.engine.sync.barrier.Barrier;
import space.engine.sync.barrier.BarrierImpl;
import space.engine.sync.lock.SyncLock;
import space.engine.sync.lock.SyncLockImpl;
import space.engine.task.TaskCreator;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static space.engine.Side.GLOBAL_EXECUTOR;
import static space.engine.task.Tasks.*;

public class TransactionTest {
	
	public static int[] TRANSACTION_COUNT = new int[] {2, 4, 6, 8, 10, 15, 20, 50, 100, 500, 1000, 5000, 10000};
	public static boolean FANCY_PRINTOUT = false;
	
	public static AtomicInteger COUNTER;
	
	public static class Entity {
		
		public SyncLock lock = new SyncLockImpl();
		public int count = 0;
	}
	
	public static void main(String[] args) throws InterruptedException {
		try {
			GLOBAL_EXECUTOR = Executors.newFixedThreadPool(8);
			System.out.print(""); //initialization
			
			//run
			for (int i : TRANSACTION_COUNT) {
				run(i);
			}
		} finally {
			if (GLOBAL_EXECUTOR instanceof ThreadPoolExecutor)
				((ThreadPoolExecutor) GLOBAL_EXECUTOR).shutdown();
		}
	}
	
	public static void run(int transactionCount) throws InterruptedException {
		COUNTER = new AtomicInteger();
		Entity entity1 = new Entity();
		Entity entity2 = new Entity();
		
		TaskCreator<? extends Barrier> taskCreator = parallel(
				IntStream.range(0, transactionCount)
						 .mapToObj(i -> i % 2 == 0 ? createTransaction(entity1, entity2) : createTransaction(entity2, entity1))
						 .collect(Collectors.toList())
		);
		
		BarrierImpl barrier = new BarrierImpl();
		if (FANCY_PRINTOUT)
			System.out.println(transactionCount + " Transactions: submitting");
		Barrier task = taskCreator.submit(barrier);
		if (FANCY_PRINTOUT)
			System.out.println(transactionCount + " Transactions: launching");
		barrier.triggerNow();
		if (FANCY_PRINTOUT)
			System.out.println(transactionCount + " Transactions: awaiting");
		task.await();
		if (FANCY_PRINTOUT)
			System.out.println(transactionCount + " Transactions: done!");
		
		if (transactionCount % 2 == 0) {
			if (!(entity1.count == 0 && entity2.count == 0))
				throw new RuntimeException("Transaction result invalid: " + entity1.count + " - " + entity2.count + " have to be 0 - 0");
		} else {
			if (!(entity1.count == -1 && entity2.count == 1))
				throw new RuntimeException("Transaction result invalid: " + entity1.count + " - " + entity2.count + " have to be -1 - 1");
		}
		
		int count = COUNTER.get();
		if (FANCY_PRINTOUT)
			System.out.println(transactionCount + " Transactions: " + count + " calls to acquireLocks(). That's " + ((double) count / transactionCount) + " times!");
		else
			System.out.println(count + "\t" + Double.toString((double) count / transactionCount).replace('.', ','));
	}
	
	public static TaskCreator<? extends Barrier> createTransaction(Entity from, Entity to) {
		return sequential(new SyncLock[] {from.lock, to.lock}, Barrier.EMPTY_BARRIER_ARRAY, List.of(
				runnable(() -> from.count--),
				runnable(() -> to.count++)
		));
	}
}
