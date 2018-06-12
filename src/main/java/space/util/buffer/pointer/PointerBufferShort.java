package space.util.buffer.pointer;

import space.util.buffer.Allocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.primitive.Primitive;

import static space.util.primitive.Primitives.INT16;

//single
public class PointerBufferShort extends AbstractPointerBuffer<PointerBufferShort> {
	
	public static final Primitive<?> TYPE = INT16;
	
	public static PointerAllocator<PointerBufferShort> createAlloc(Allocator<DirectBuffer> alloc) {
		return new PointerAllocator<>(alloc, TYPE, PointerBufferShort::new);
	}
	
	public PointerBufferShort(DirectBuffer buffer) {
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
