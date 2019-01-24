package space.engine.buffer.pointer;

import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.primitive.Primitive;

import static space.engine.primitive.Primitives.POINTER;

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
	public long getPointer() {
		return buffer.getLong(0);
	}
	
	public void putPointer(long b) {
		buffer.putLong(0, b);
	}
}
