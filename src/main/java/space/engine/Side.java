package space.engine;

import org.jetbrains.annotations.NotNull;
import space.engine.key.attribute.AttributeKey;
import space.engine.key.attribute.AttributeList;
import space.engine.key.attribute.AttributeListCreator;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("unused")
public class Side {
	
	private static ExecutorService GLOBAL_EXECUTOR_POOL = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());
	
	public static final AttributeListCreator<Side> ATTRIBUTE_LIST_CREATOR = new AttributeListCreator<>();
	
	//attributes
	public static final AttributeKey<Executor> EXECUTOR_POOL = ATTRIBUTE_LIST_CREATOR.createKeyWithDefault(GLOBAL_EXECUTOR_POOL);
	
	//internal
	private Side() {
	}
	
	private static final ThreadLocal<AttributeList<Side>> THREAD_LOCAL = ThreadLocal.withInitial(ATTRIBUTE_LIST_CREATOR::create);
	
	public static @NotNull AttributeList<Side> side() {
		return THREAD_LOCAL.get();
	}
	
	public static <T> T sideGet(AttributeKey<T> key) {
		return side().get(key);
	}
	
	public static void exit() {
		GLOBAL_EXECUTOR_POOL.shutdown();
	}
}
