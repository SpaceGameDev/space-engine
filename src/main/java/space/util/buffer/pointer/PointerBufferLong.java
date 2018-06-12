package space.util.buffer.pointer;

import space.util.buffer.Allocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.primitive.Primitive;

import static space.util.primitive.Primitives.INT64;

//single
public class PointerBufferLong extends AbstractPointerBuffer<PointerBufferLong> {
	
	public static final Primitive<?> TYPE = INT64;
	
	public static PointerAllocator<PointerBufferLong> createAlloc(Allocator<DirectBuffer> alloc) {
		return new PointerAllocator<>(alloc, TYPE, PointerBufferLong::new);
	}
	
	public PointerBufferLong(DirectBuffer buffer) {
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
