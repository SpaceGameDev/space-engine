package space.engine.side;

import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.stack.BufferAllocatorStack;
import space.util.buffer.string.IBufferStringConverter;
import space.util.keygen.IKey;
import space.util.keygen.attribute.AttributeListCreator;
import space.util.keygen.attribute.IAttributeListCreator;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeList;

@SuppressWarnings("unused")
public class Side {
	
	public static final IAttributeListCreator ATTRIBUTE_LIST_CREATOR = new AttributeListCreator();
	
	//buffer
	public static final IKey<BufferAllocator> BUFFER_ALLOC = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<BufferAllocatorStack> BUFFER_STACK_ALLOC = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<IBufferStringConverter> BUFFER_STRING_CONVERTER = ATTRIBUTE_LIST_CREATOR.generateKey();
	
	private static ThreadLocal<IAttributeList> thLocal = ThreadLocal.withInitial(ATTRIBUTE_LIST_CREATOR::create);
	
	public static IAttributeList getSide() {
		return thLocal.get();
	}
}
