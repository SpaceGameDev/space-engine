package space.engine.sync.timer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.freeableStorage.FreeableStorageWeak;
import space.engine.sync.barrier.Barrier;
import space.engine.sync.barrier.BarrierImpl;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A {@link BarrierTimer} allows you to {@link #create(long)} a Barrier triggering when this reached the supplied point in time.
 * When it actually triggers depends on the implementation.
 * Some standard Implementations include the {@link #createUnmodifiable(long)} Methods and {@link BarrierTimerWithTimeControl}.
 */
public abstract class BarrierTimer {
	
	//static
	public static BarrierTimer createUnmodifiable(final long offsetNanos) {
		return createUnmodifiable(offsetNanos, 1);
	}
	
	public static BarrierTimer createUnmodifiable(final long offsetNanos, final double speedNanos) {
		return new BarrierTimer() {
			@Override
			public long timeFunction(long input) {
				return (long) (input / speedNanos) - (System.nanoTime() + offsetNanos);
			}
			
			@Override
			public long currTime() {
				return (long) ((System.nanoTime() + offsetNanos) / speedNanos);
			}
			
			@Override
			public double currSpeed() {
				return speedNanos;
			}
		};
	}
	
	//object
	private @Nullable Node first;
	private Runner runner;
	
	public BarrierTimer() {
		runner = new Runner(this);
	}
	
	/**
	 * Converts input time (in nanos) to the time the Thread should sleep (in nanos)
	 *
	 * @param input the input time (in nanos)
	 * @return the time (in nanos) the Thread should sleep before execution
	 */
	public abstract long timeFunction(long input);
	
	/**
	 * The current time this Timer has (in nanos)
	 *
	 * @return the time (in nanos)
	 */
	public abstract long currTime();
	
	/**
	 * @return The current speed of the timer (as a multiplier, normal speed is 1.0)
	 */
	public abstract double currSpeed();
	
	public synchronized Barrier create(long time) {
		if (first == null || time < first.time) {
			
			//insert as first element
			Node e = new Node(time);
			e.next = first;
			first = e;
			recalculateTimer();
			return e;
		} else if (first.time == time) {
			//same time
			return first;
		} else {
			
			@NotNull Node prev = first;
			@Nullable Node curr = first.next;
			while (curr != null && time > curr.time) {
				//traverse
				prev = curr;
				curr = curr.next;
			}
			
			//same time
			if (curr != null && time == curr.time)
				return curr;
			
			//insert here
			Node e = new Node(time);
			prev.next = e;
			e.next = curr;
			return e;
		}
	}
	
	private synchronized void insert(Node e) {
		long time = e.time;
		if (first == null || time < first.time) {
			
			//insert as first element
			e.next = first;
			first = e;
			recalculateTimer();
		} else {
			
			@NotNull Node prev = first;
			@Nullable Node curr = first.next;
			while (curr != null && time > curr.time) {
				//traverse
				prev = curr;
				curr = curr.next;
			}
			
			//insert here
			prev.next = e;
			e.next = curr;
		}
	}
	
	protected synchronized @Nullable Node poll() {
		Node ret = first;
		if (first != null)
			first = first.next;
		return ret;
	}
	
	protected void recalculateTimer() {
		runner.interrupt();
	}
	
	@Override
	public synchronized String toString() {
		if (first == null) {
			return "[]";
		}
		
		StringBuilder b = new StringBuilder("[");
		Node node = first;
		while (true) {
			b.append(node.time);
			node = node.next;
			if (node == null)
				break;
			b.append(", ");
		}
		return b.append("]").toString();
	}
	
	//DON'T make this class static! The Reference is required to prevent gc of this BarrierTimer if it still has any Nodes.
	protected class Node extends BarrierImpl {
		
		private @Nullable Node next;
		public long time;
		
		public Node(long time) {
			this.time = time;
		}
	}
	
	private static class Runner extends FreeableStorageWeak<BarrierTimer> implements Runnable {
		
		private static final AtomicInteger TH_COUNTER = new AtomicInteger();
		
		private Thread th;
		
		public Runner(BarrierTimer timer) {
			super(timer, new Object[] {ROOT_LIST});
			
			th = new Thread(this, "BarrierTimerThread-" + TH_COUNTER.getAndIncrement());
			th.setDaemon(true);
			th.start();
		}
		
		@Override
		public void run() {
			while (true) {
				//DON'T keep the timer reference while waiting
				{
					BarrierTimer timer = this.get();
					if (timer == null)
						return;
					
					Node node;
					while ((node = timer.poll()) != null) {
						long sleepTime = timer.timeFunction(node.time);
						if (sleepTime > 0) {
							try {
								Thread.sleep(sleepTime / 1000000, (int) (sleepTime % 1000000));
							} catch (InterruptedException e) {
								timer.insert(node);
								continue;
							}
						}
						
						node.triggerNow();
					}
				}
				
				try {
					Thread.sleep(Integer.MAX_VALUE);
				} catch (InterruptedException ignored) {
				
				}
			}
		}
		
		private void interrupt() {
			if (Thread.currentThread() != th)
				th.interrupt();
		}
		
		@Override
		protected void handleFree() {
			interrupt();
		}
	}
}
