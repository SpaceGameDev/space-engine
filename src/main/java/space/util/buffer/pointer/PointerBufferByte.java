package space.util.buffer.pointer;

import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;

import static space.util.primitive.Primitives.INT8;

//single
public class PointerBufferByte extends AbstractPointerBuffer {
	
	public static PointerBufferByte allocSingle(AllocMethod alloc, long address, FreeableStorage... parents) {
		return new PointerBufferByte(alloc.alloc(address, INT8.bytes, parents));
	}
	
	public static PointerBufferByte mallocSingle(Allocator alloc, FreeableStorage... parents) {
		return new PointerBufferByte(alloc.malloc(INT8.bytes, parents));
	}
	
	protected PointerBufferByte(DirectBuffer buffer) {
		super(check(buffer), INT8);
	}
	
	public static DirectBuffer check(DirectBuffer buffer) {
		if (buffer.capacity() < INT8.bytes)
			throw new IllegalArgumentException("Buffer too tiny!");
		return buffer;
	}
	
	public byte getByte() {
		return buffer.getByte(0);
	}
	
	public void putByte(byte b) {
		buffer.putByte(0, b);
	}
}
