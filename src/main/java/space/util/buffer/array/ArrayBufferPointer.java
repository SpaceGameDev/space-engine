package space.util.buffer.array;

import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;

import static space.util.primitive.Primitives.POINTER;

public class ArrayBufferPointer extends AbstractArrayBuffer<ArrayBufferPointer> {
	
	public static ArrayBufferPointer alloc(AllocMethod alloc, long address, long length, FreeableStorage... parents) {
		return new ArrayBufferPointer(alloc.alloc(address, length * POINTER.bytes, parents), length);
	}
	
	public static ArrayBufferPointer malloc(Allocator alloc, long length, FreeableStorage... parents) {
		return new ArrayBufferPointer(alloc.malloc(length * POINTER.bytes, parents), length);
	}
	
	public ArrayBufferPointer(DirectBuffer buffer) {
		super(buffer, POINTER);
	}
	
	protected ArrayBufferPointer(DirectBuffer buffer, long length) {
		super(buffer, POINTER, length);
	}
	
	//get / put
	public long getPointer(long index) {
		return buffer.getPointer(getOffset(index));
	}
	
	public void putPointer(long index, long b) {
		buffer.putPointer(getOffset(index), b);
	}
	
	//single
	public static ArrayBufferPointerSingle allocSingle(AllocMethod alloc, long address, FreeableStorage... parents) {
		return new ArrayBufferPointerSingle(alloc.alloc(address, POINTER.bytes, parents));
	}
	
	public static ArrayBufferPointerSingle mallocSingle(Allocator alloc, FreeableStorage... parents) {
		return new ArrayBufferPointerSingle(alloc.malloc(POINTER.bytes, parents));
	}
	
	public static class ArrayBufferPointerSingle extends AbstractArrayBuffer<ArrayBufferPointerSingle> {
		
		protected ArrayBufferPointerSingle(DirectBuffer buffer) {
			super(check(buffer), POINTER, 1);
		}
		
		public static DirectBuffer check(DirectBuffer buffer) {
			if (buffer.capacity() != POINTER.bytes)
				throw new IllegalArgumentException("Buffer too big!");
			return buffer;
		}
		
		public long getPointer() {
			return buffer.getPointer(0);
		}
		
		public void putPointer(long b) {
			buffer.putPointer(0, b);
		}
	}
}