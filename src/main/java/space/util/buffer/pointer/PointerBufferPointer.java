package space.util.buffer.pointer;

import space.util.buffer.Allocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.primitive.Primitive;

import static space.util.primitive.Primitives.POINTER;

//single
public class PointerBufferPointer extends AbstractPointerBuffer<PointerBufferPointer> {
	
	public static final Primitive<?> TYPE = POINTER;
	
	public static PointerAllocator<PointerBufferPointer> createAlloc(Allocator<DirectBuffer> alloc) {
		return new PointerAllocator<>(alloc, TYPE, PointerBufferPointer::new);
	}
	
	public PointerBufferPointer(DirectBuffer buffer) {
		super(buffer, TYPE);
	}
	
	//get / put
	public byte getByte() {
		return buffer.getByte(0);
	}
	
	public void putByte(byte b) {
		buffer.putByte(0, b);
	}
}
