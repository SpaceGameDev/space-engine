package space.util.buffer.pointer;

import space.util.buffer.Allocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.primitive.Primitive;

import static space.util.primitive.Primitives.FP32;

//single
public class PointerBufferFloat extends AbstractPointerBuffer<PointerBufferFloat> {
	
	public static final Primitive<?> TYPE = FP32;
	
	public static PointerAllocator<PointerBufferFloat> createAlloc(Allocator<DirectBuffer> alloc) {
		return new PointerAllocator<>(alloc, TYPE, PointerBufferFloat::new);
	}
	
	public PointerBufferFloat(DirectBuffer buffer) {
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
