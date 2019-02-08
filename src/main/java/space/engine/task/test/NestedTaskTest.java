package space.engine.task.test;

import space.engine.sync.barrier.Barrier;
import space.engine.sync.future.Future;
import space.engine.sync.timer.BarrierTimer;
import space.engine.task.TaskCreator;
import space.engine.task.Tasks;
import space.engine.task.impl.DelayTask;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NestedTaskTest {
	
	public static final BarrierTimer TIMER = BarrierTimer.createUnmodifiable(-System.nanoTime(), 1);
	public static final AtomicInteger COUNTER = new AtomicInteger();
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		try {
			TaskCreator<? extends Future<float[]>> future = Tasks.future(exec, () -> {
				int i = COUNTER.getAndIncrement();
				if (i % 2 == 0)
					throw new DelayTask(TIMER.create(i * 500000000L).toFuture(() -> new float[] {i}));
				
				return new float[] {i};
			});
			
			Barrier.awaitAll(
					IntStream.range(0, 10)
							 .mapToObj(i -> future.submit())
							 .peek(o -> o.addHook(() -> System.out.println(Arrays.toString(o.assertGet()))))
							 .collect(Collectors.toList())
			).await();
		} finally {
			exec.shutdown();
		}
	}
}
