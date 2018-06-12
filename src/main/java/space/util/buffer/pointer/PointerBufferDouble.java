package space.util.buffer.pointer;

import space.util.buffer.Allocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.primitive.Primitive;

import static space.util.primitive.Primitives.FP64;

//single
public class PointerBufferDouble extends AbstractPointerBuffer<PointerBufferDouble> {
	
	public static final Primitive<?> TYPE = FP64;
	
	public static PointerAllocator<PointerBufferDouble> createAlloc(Allocator<DirectBuffer> alloc) {
		return new PointerAllocator<>(alloc, TYPE, PointerBufferDouble::new);
	}
	
	public PointerBufferDouble(DirectBuffer buffer) {
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
