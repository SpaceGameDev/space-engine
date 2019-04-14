package space.engine;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("unused")
public class Side {
	
	private static final ExecutorService POOL = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());
	private static final ThreadLocal<Executor> THLOCAL_POOL = new ThreadLocal<>();
	
	public static void overrideThreadlocalPool(@Nullable Executor executor) {
		THLOCAL_POOL.set(executor);
	}
	
	public static void exit() {
		POOL.shutdown();
	}
	
	public static Executor pool() {
		Executor executor = THLOCAL_POOL.get();
		return executor != null ? executor : POOL;
	}
}
