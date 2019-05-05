package space.engine.simpleQueue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class SimpleQueueTest {
	
	@Parameters
	public static Collection<Supplier<? extends SimpleQueue<Integer>>> parameters() {
		return List.of(LinkedSimpleQueue::new, ConcurrentLinkedSimpleQueue::new);
	}
	
	private final SimpleQueue<Integer> queue;
	
	public SimpleQueueTest(Supplier<? extends SimpleQueue<Integer>> supplier) {
		this.queue = supplier.get();
	}
	
	@Test
	public void testNormalUse() {
		queue.add(0);
		queue.add(1);
		queue.add(2);
		queue.add(3);
		
		Assert.assertEquals((Integer) 0, queue.remove());
		Assert.assertEquals((Integer) 1, queue.remove());
		Assert.assertEquals((Integer) 2, queue.remove());
		//no remove for 3 as it will dry up the queue
	}
	
	@Test
	public void testQueueDryup() {
		assertNull(queue.remove());
		
		queue.add(0);
		Assert.assertEquals((Integer) 0, queue.remove());
		assertNull(queue.remove());
	}
	
	@Test
	public void testAddAll() {
		Runnable remove4Numbers = () -> {
			Assert.assertEquals((Integer) 0, queue.remove());
			Assert.assertEquals((Integer) 1, queue.remove());
			Assert.assertEquals((Integer) 2, queue.remove());
			Assert.assertEquals((Integer) 3, queue.remove());
		};
		
		//collection
		queue.addAll(List.of(0, 1, 2, 3));
		remove4Numbers.run();
		
		//array
		queue.addAll(new Integer[] {0, 1, 2, 3});
		remove4Numbers.run();
		
		//stream
		queue.addAll(Arrays.stream(new Integer[] {0, 1, 2, 3}));
		remove4Numbers.run();
	}
}
