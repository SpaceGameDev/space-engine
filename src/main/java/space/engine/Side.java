package space.engine;

import org.jetbrains.annotations.Nullable;
import space.engine.event.Event;
import space.engine.event.EventEntry;
import space.engine.event.SequentialEventBuilder;
import space.engine.simpleQueue.ConcurrentLinkedSimpleQueue;
import space.engine.simpleQueue.pool.Executor;
import space.engine.simpleQueue.pool.SimpleThreadPool;
import space.engine.sync.Tasks.RunnableWithDelay;
import space.engine.sync.barrier.Barrier;

@SuppressWarnings("unused")
public class Side {
	
	//pool
	private static final SimpleThreadPool POOL = new SimpleThreadPool(Runtime.getRuntime().availableProcessors(), new ConcurrentLinkedSimpleQueue<>());
	private static final ThreadLocal<Executor> THLOCAL_POOL_OVERRIDE = new ThreadLocal<>();
	
	public static void overrideThreadlocalPool(@Nullable Executor executor) {
		THLOCAL_POOL_OVERRIDE.set(executor);
	}
	
	public static Executor pool() {
		Executor executor = THLOCAL_POOL_OVERRIDE.get();
		return executor != null ? executor : POOL;
	}
	
	//event exit
	public static final Event<RunnableWithDelay> EVENT_EXIT = new SequentialEventBuilder<>();
	public static final EventEntry<RunnableWithDelay> EXIT_EVENT_ENTRY_BEFORE_APPLICATION_SHUTDOWN;
	public static final EventEntry<RunnableWithDelay> EXIT_EVENT_ENTRY_POOL_EXIT;
	
	static {
		EVENT_EXIT.addHook(EXIT_EVENT_ENTRY_BEFORE_APPLICATION_SHUTDOWN = new EventEntry<>(() -> {}));
		//don't wait on pool exit -> will cause deadlock
		EVENT_EXIT.addHook(EXIT_EVENT_ENTRY_POOL_EXIT = new EventEntry<>(POOL::stop, EXIT_EVENT_ENTRY_BEFORE_APPLICATION_SHUTDOWN));
	}
	
	public static Barrier exit() {
		return EVENT_EXIT.submit(RunnableWithDelay::run);
	}
}
