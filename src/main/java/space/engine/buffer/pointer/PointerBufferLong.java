package space.engine.buffer.pointer;

import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.primitive.Primitive;

import static space.engine.primitive.Primitives.INT64;

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
	public long getLong() {
		return buffer.getLong(0);
	}
	
	public void putLong(long b) {
		buffer.putLong(0, b);
	}
}
