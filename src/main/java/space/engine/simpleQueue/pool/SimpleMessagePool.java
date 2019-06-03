package space.engine.simpleQueue.pool;

import org.jetbrains.annotations.NotNull;
import space.engine.simpleQueue.SimpleQueue;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public abstract class SimpleMessagePool<MSG> extends AbstractSimpleMessagePool<MSG> {
	
	public SimpleMessagePool(int threadCnt, @NotNull SimpleQueue<MSG> queue) {
		this(threadCnt, queue, Executors.defaultThreadFactory());
	}
	
	public SimpleMessagePool(int threadCnt, @NotNull SimpleQueue<MSG> queue, ThreadFactory threadFactory) {
		this(threadCnt, queue, threadFactory, true);
	}
	
	protected SimpleMessagePool(int threadCnt, @NotNull SimpleQueue<MSG> queue, ThreadFactory threadFactory, boolean callinit) {
		super(threadCnt, queue, threadFactory, callinit);
	}
	
	//park
	protected synchronized void park() {
		try {
			this.wait();
		} catch (InterruptedException ignored) {
		
		}
	}
	
	protected synchronized void unparkThreads() {
		this.notifyAll();
	}
}
