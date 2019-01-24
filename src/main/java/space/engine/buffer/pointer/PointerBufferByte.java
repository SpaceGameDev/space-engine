package space.engine.buffer.pointer;

import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.primitive.Primitive;

import static space.engine.primitive.Primitives.INT8;

//single
public class PointerBufferByte extends AbstractPointerBuffer<PointerBufferByte> {
	
	public static final Primitive<?> TYPE = INT8;
	
	public static PointerAllocator<PointerBufferByte> createAlloc(Allocator<DirectBuffer> alloc) {
		return new PointerAllocator<>(alloc, TYPE, PointerBufferByte::new);
	}
	
	public PointerBufferByte(DirectBuffer buffer) {
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
