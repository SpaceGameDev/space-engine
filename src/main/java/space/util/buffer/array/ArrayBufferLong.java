package space.util.buffer.array;

import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;

import static space.util.primitive.Primitives.INT64;

public class ArrayBufferLong extends AbstractArrayBuffer<ArrayBufferLong> {
	
	public static ArrayBufferLong alloc(AllocMethod alloc, long address, long length, FreeableStorage... parents) {
		return new ArrayBufferLong(alloc.alloc(address, length * INT64.bytes, parents), length);
	}
	
	public static ArrayBufferLong malloc(Allocator alloc, long length, FreeableStorage... parents) {
		return new ArrayBufferLong(alloc.malloc(length * INT64.bytes, parents), length);
	}
	
	public ArrayBufferLong(DirectBuffer buffer) {
		super(buffer, INT64);
	}
	
	protected ArrayBufferLong(DirectBuffer buffer, long length) {
		super(buffer, INT64, length);
	}
	
	//get / put
	public long getLong(long index) {
		return buffer.getLong(getOffset(index));
	}
	
	public void putLong(long index, long b) {
		buffer.putLong(getOffset(index), b);
	}
	
	//array
	public void copyInto(long[] dest) {
		buffer.copyInto(dest);
	}
	
	public void copyInto(long index, long[] dest) {
		buffer.copyInto(getOffset(index), dest);
	}
	
	public void copyInto(long index, long[] dest, int destPos, int length) {
		buffer.copyInto(getOffset(index), dest, destPos, length);
	}
	
	public void copyFrom(long[] src) {
		buffer.copyFrom(src);
	}
	
	public void copyFrom(long[] src, long index) {
		buffer.copyFrom(src, getOffset(index));
	}
	
	public void copyFrom(long[] src, int srcPos, int length, long index) {
		buffer.copyFrom(src, srcPos, length, getOffset(index));
	}
	
	//single
	public static ArrayBufferLongSingle allocSingle(AllocMethod alloc, long address, FreeableStorage... parents) {
		return new ArrayBufferLongSingle(alloc.alloc(address, INT64.bytes, parents));
	}
	
	public static ArrayBufferLongSingle mallocSingle(Allocator alloc, FreeableStorage... parents) {
		return new ArrayBufferLongSingle(alloc.malloc(INT64.bytes, parents));
	}
	
	public static class ArrayBufferLongSingle extends AbstractArrayBuffer<ArrayBufferLongSingle> {
		
		protected ArrayBufferLongSingle(DirectBuffer buffer) {
			super(check(buffer), INT64, 1);
		}
		
		public static DirectBuffer check(DirectBuffer buffer) {
			if (buffer.capacity() != INT64.bytes)
				throw new IllegalArgumentException("Buffer too big!");
			return buffer;
		}
		
		public long getLong() {
			return buffer.getLong(0);
		}
		
		public void putLong(long b) {
			buffer.putLong(0, b);
		}
	}
}