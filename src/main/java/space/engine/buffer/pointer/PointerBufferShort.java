package space.engine.buffer.pointer;

import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.primitive.Primitive;

import static space.engine.primitive.Primitives.INT16;

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
	public short getShort() {
		return buffer.getShort(0);
	}
	
	public void putShort(short b) {
		buffer.putShort(0, b);
	}
}
