package space.engine;

import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.stack.BufferAllocatorStack;
import space.util.buffer.string.BufferStringConverter;
import space.util.key.Key;
import space.util.key.attribute.AttributeListCreator;
import space.util.key.attribute.AttributeListCreator.IAttributeList;
import space.util.key.attribute.AttributeListCreatorImpl;

@SuppressWarnings("unused")
public class Side {
	
	public static final AttributeListCreator<Side> ATTRIBUTE_LIST_CREATOR = new AttributeListCreatorImpl<>();
	
	//buffer
	public static final Key<BufferAllocator> BUFFER_ALLOC = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final Key<BufferAllocatorStack> BUFFER_STACK_ALLOC = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final Key<BufferStringConverter> BUFFER_STRING_CONVERTER = ATTRIBUTE_LIST_CREATOR.generateKey();
	
	private static final ThreadLocal<IAttributeList<Side>> THREAD_LOCAL = ThreadLocal.withInitial(ATTRIBUTE_LIST_CREATOR::create);
	
	public static IAttributeList<Side> getSide() {
		return THREAD_LOCAL.get();
	}
}
