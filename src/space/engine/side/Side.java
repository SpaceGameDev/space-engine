package space.engine.side;

import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.stack.BufferAllocatorStack;
import space.util.buffer.string.BufferStringConverter;
import space.util.key.Key;
import space.util.key.attribute.AttributeListCreator;
import space.util.key.attribute.AttributeListCreator.IAttributeList;
import space.util.key.attribute.AttributeListCreatorImpl;

@SuppressWarnings("unused")
public class Side {
	
	public static final AttributeListCreator ATTRIBUTE_LIST_CREATOR = new AttributeListCreatorImpl();
	
	//buffer
	public static final Key<BufferAllocator> BUFFER_ALLOC = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final Key<BufferAllocatorStack> BUFFER_STACK_ALLOC = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final Key<BufferStringConverter> BUFFER_STRING_CONVERTER = ATTRIBUTE_LIST_CREATOR.generateKey();
	
	private static ThreadLocal<IAttributeList> thLocal = ThreadLocal.withInitial(ATTRIBUTE_LIST_CREATOR::create);
	
	public static IAttributeList getSide() {
		return thLocal.get();
	}
}
