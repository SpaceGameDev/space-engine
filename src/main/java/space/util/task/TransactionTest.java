package space.util.task;

import space.util.sync.barrier.Barrier;
import space.util.sync.lock.SyncLock;
import space.util.sync.lock.SyncLockImpl;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static space.util.Global.GLOBAL_EXECUTOR;
import static space.util.task.Tasks.*;

public class TransactionTest {
	
	public static int TRANSACTION_COUNT = 1000;
	
	public static class Entity {
		
		public SyncLock lock = new SyncLockImpl();
		public int count = 0;
	}
	
	public static final Entity entity1 = new Entity();
	public static final Entity entity2 = new Entity();
	
	public static void main(String[] args) throws InterruptedException {
		try {
			GLOBAL_EXECUTOR = Executors.newFixedThreadPool(8);
			System.out.print(""); //initialization
			test();
		} finally {
			if (GLOBAL_EXECUTOR instanceof ThreadPoolExecutor)
				((ThreadPoolExecutor) GLOBAL_EXECUTOR).shutdown();
		}
	}
	
	public static void test() throws InterruptedException {
		TaskCreator<? extends Barrier> task = parallel(
				IntStream.range(0, TRANSACTION_COUNT)
						 .mapToObj(i -> i % 2 == 0 ? createTransaction(entity1, entity2) : createTransaction(entity2, entity1))
						 .collect(Collectors.toList())
		);

//		TaskCreator<? extends Barrier> task = parallel(List.of(
//				createTransaction(entity1, entity2),
//				createTransaction(entity2, entity1)
//		));

//		TaskCreator<? extends Barrier> task = createTransaction(entity1, entity2);
		
		printState();
		task.submit().await();
		printState();
	}
	
	public static void printState() {
		System.out.println("ent1: " + entity1.count + " - ent2: " + entity2.count + "\n Counts should be " + (TRANSACTION_COUNT % 2 == 0 ? "0 and 0" : "-1 and 1"));
	}
	
	public static TaskCreator<? extends Barrier> createTransaction(Entity from, Entity to) {
		return sequential(new SyncLock[] {from.lock, to.lock}, Barrier.EMPTY_BARRIER_ARRAY, List.of(
				runnable(() -> from.count--),
				runnable(() -> to.count++)
		));
	}
}
