package space.engine;

import org.jetbrains.annotations.NotNull;
import space.engine.buffer.Allocator;
import space.engine.buffer.array.ArrayAllocatorCollection;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.buffer.direct.alloc.stack.AllocatorStack;
import space.engine.buffer.pointer.PointerAllocatorCollection;
import space.engine.buffer.string.BufferStringConverter;
import space.engine.key.attribute.AttributeKey;
import space.engine.key.attribute.AttributeList;
import space.engine.key.attribute.AttributeListCreator;
import space.engine.key.attribute.AttributeListModify;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("unused")
public class Side {
	
	private static ExecutorService GLOBAL_EXECUTOR_POOL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	public static final AttributeListCreator<Side> ATTRIBUTE_LIST_CREATOR = new AttributeListCreator<>();
	
	//attributes
	public static final AttributeKey<Executor> EXECUTOR_POOL = ATTRIBUTE_LIST_CREATOR.createKeyWithDefault(GLOBAL_EXECUTOR_POOL);
	
	//buffer alloc
	public static final AttributeKey<Allocator<DirectBuffer>> BUFFER_ALLOC = ATTRIBUTE_LIST_CREATOR.createKeyNormal();
	public static final AttributeKey<ArrayAllocatorCollection> BUFFER_ALLOC_ARRAY = ATTRIBUTE_LIST_CREATOR.createKeyNormal();
	public static final AttributeKey<PointerAllocatorCollection> BUFFER_ALLOC_POINTER = ATTRIBUTE_LIST_CREATOR.createKeyNormal();
	
	//buffer alloc stack
	public static final AttributeKey<AllocatorStack<DirectBuffer>> BUFFER_ALLOC_STACK = ATTRIBUTE_LIST_CREATOR.createKeyNormal();
	public static final AttributeKey<ArrayAllocatorCollection> BUFFER_ALLOC_STACK_ARRAY = ATTRIBUTE_LIST_CREATOR.createKeyNormal();
	public static final AttributeKey<PointerAllocatorCollection> BUFFER_ALLOC_STACK_POINTER = ATTRIBUTE_LIST_CREATOR.createKeyNormal();
	
	//buffer string converter
	public static final AttributeKey<BufferStringConverter> BUFFER_STRING_CONVERTER = ATTRIBUTE_LIST_CREATOR.createKeyNormal();
	
	//initializer
	//buffer alloc
	public static void initBufferAlloc(AttributeListModify modify, Allocator<DirectBuffer> alloc) {
		modify.put(BUFFER_ALLOC, alloc);
		modify.put(BUFFER_ALLOC_ARRAY, new ArrayAllocatorCollection(alloc));
		modify.put(BUFFER_ALLOC_POINTER, new PointerAllocatorCollection(alloc));
	}
	
	//buffer alloc stack
	public static void initBufferAllocStack(AttributeListModify modify, AllocatorStack<DirectBuffer> alloc) {
		modify.put(BUFFER_ALLOC_STACK, alloc);
		modify.put(BUFFER_ALLOC_STACK_ARRAY, new ArrayAllocatorCollection(alloc));
		modify.put(BUFFER_ALLOC_STACK_POINTER, new PointerAllocatorCollection(alloc));
	}
	
	//buffer string converter
	public static void initBufferStringConverter(AttributeListModify modify, BufferStringConverter converter) {
		modify.put(BUFFER_STRING_CONVERTER, converter);
	}
	
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
