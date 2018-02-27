package space.util.buffer.array;

import space.util.buffer.alloc.IAllocMethod;
import space.util.buffer.alloc.IMallocMethod;
import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.IFreeableStorage;

import static space.util.primitive.NativeType.POINTER;

public class ArrayBufferPointer extends AbstractArrayBuffer<ArrayBufferPointer> {
	
	public static ArrayBufferPointer alloc(IAllocMethod alloc, long address, long length, IFreeableStorage... parents) {
		return new ArrayBufferPointer(alloc.alloc(address, POINTER.multiply(length), parents), length);
	}
	
	public static ArrayBufferPointer malloc(IMallocMethod alloc, long length, IFreeableStorage... parents) {
		return new ArrayBufferPointer(alloc.malloc(POINTER.multiply(length), parents), length);
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
	public static ArrayBufferPointerSingle allocSingle(IAllocMethod alloc, long address, IFreeableStorage... parents) {
		return new ArrayBufferPointerSingle(alloc.alloc(address, POINTER.BYTES, parents));
	}
	
	public static ArrayBufferPointerSingle mallocSingle(IMallocMethod alloc, IFreeableStorage... parents) {
		return new ArrayBufferPointerSingle(alloc.malloc(POINTER.BYTES, parents));
	}
	
	public static class ArrayBufferPointerSingle extends AbstractArrayBuffer<ArrayBufferPointerSingle> {
		
		protected ArrayBufferPointerSingle(DirectBuffer buffer) {
			super(check(buffer), POINTER, 1);
		}
		
		public static DirectBuffer check(DirectBuffer buffer) {
			if (buffer.capacity() != POINTER.BYTES)
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