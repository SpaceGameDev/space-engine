package space.engine;

import org.jetbrains.annotations.NotNull;
import space.util.buffer.Allocator;
import space.util.buffer.array.ArrayAllocatorCollection;
import space.util.buffer.direct.DirectBuffer;
import space.util.buffer.direct.alloc.stack.AllocatorStack;
import space.util.buffer.pointer.PointerAllocatorCollection;
import space.util.buffer.string.BufferStringConverter;
import space.util.key.Key;
import space.util.key.attribute.AttributeList;
import space.util.key.attribute.AttributeListCreator;
import space.util.key.attribute.AttributeListModification;

@SuppressWarnings("unused")
public class Side {
	
	public static final AttributeListCreator<Side> ATTRIBUTE_LIST_CREATOR = new AttributeListCreator<>();
	
	//attributes
	//buffer alloc
	public static final Key<Allocator<DirectBuffer>> BUFFER_ALLOC = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final Key<ArrayAllocatorCollection> BUFFER_ALLOC_ARRAY = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final Key<PointerAllocatorCollection> BUFFER_ALLOC_POINTER = ATTRIBUTE_LIST_CREATOR.generateKey();
	
	//buffer alloc stack
	public static final Key<AllocatorStack<DirectBuffer>> BUFFER_ALLOC_STACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final Key<ArrayAllocatorCollection> BUFFER_ALLOC_STACK_ARRAY = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final Key<PointerAllocatorCollection> BUFFER_ALLOC_STACK_POINTER = ATTRIBUTE_LIST_CREATOR.generateKey();
	
	//buffer string converter
	public static final Key<BufferStringConverter> BUFFER_STRING_CONVERTER = ATTRIBUTE_LIST_CREATOR.generateKey();
	
	//initializer
	//buffer alloc
	public static void initBufferAlloc(AttributeListModification<Side> modify, Allocator<DirectBuffer> alloc) {
		modify.put(BUFFER_ALLOC, alloc);
		modify.put(BUFFER_ALLOC_ARRAY, new ArrayAllocatorCollection(alloc));
		modify.put(BUFFER_ALLOC_POINTER, new PointerAllocatorCollection(alloc));
	}
	
	//buffer alloc stack
	public static void initBufferAllocStack(AttributeListModification<Side> modify, AllocatorStack<DirectBuffer> alloc) {
		modify.put(BUFFER_ALLOC_STACK, alloc);
		modify.put(BUFFER_ALLOC_STACK_ARRAY, new ArrayAllocatorCollection(alloc));
		modify.put(BUFFER_ALLOC_STACK_POINTER, new PointerAllocatorCollection(alloc));
	}
	
	//buffer string converter
	public static void initBufferStringConverter(AttributeListModification<Side> modify, BufferStringConverter converter) {
		modify.put(BUFFER_STRING_CONVERTER, converter);
	}
	
	//internal
	private Side() {
	}
	
	private static final ThreadLocal<AttributeList<Side>> THREAD_LOCAL = ThreadLocal.withInitial(ATTRIBUTE_LIST_CREATOR::create);
	
	public static @NotNull AttributeList<Side> getSide() {
		return THREAD_LOCAL.get();
	}
}
