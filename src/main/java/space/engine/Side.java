package space.engine;

import org.jetbrains.annotations.NotNull;
import space.util.buffer.Allocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.buffer.direct.alloc.stack.AllocatorStack;
import space.util.buffer.string.BufferStringConverter;
import space.util.key.Key;
import space.util.key.attribute.AttributeListCreator;
import space.util.key.attribute.AttributeListCreator.IAttributeList;
import space.util.key.attribute.AttributeListCreatorImpl;

@SuppressWarnings("unused")
public class Side {
	
	public static final AttributeListCreator<Side> ATTRIBUTE_LIST_CREATOR = new AttributeListCreatorImpl<>();
	
	//buffer
	public static final Key<Allocator<DirectBuffer>> BUFFER_ALLOC = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final Key<AllocatorStack<DirectBuffer>> BUFFER_STACK_ALLOC = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final Key<BufferStringConverter> BUFFER_STRING_CONVERTER = ATTRIBUTE_LIST_CREATOR.generateKey();
	
	private static final ThreadLocal<IAttributeList<Side>> THREAD_LOCAL = ThreadLocal.withInitial(ATTRIBUTE_LIST_CREATOR::create);
	
	@NotNull
	public static IAttributeList<Side> getSide() {
		return THREAD_LOCAL.get();
	}
}
