package space.engine.buffer.pointer;

import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.primitive.Primitive;

import static space.engine.primitive.Primitives.FP64;

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
	public double getDouble() {
		return buffer.getDouble(0);
	}
	
	public void putDouble(double b) {
		buffer.putDouble(0, b);
	}
}
