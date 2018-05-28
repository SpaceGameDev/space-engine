package space.util.buffer.array;

import space.util.buffer.alloc.AllocMethod;
import space.util.buffer.alloc.MallocMethod;
import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;

import static space.util.primitive.Primitives.INT32;

public class ArrayBufferInt extends AbstractArrayBuffer<ArrayBufferInt> {
	
	public static ArrayBufferInt alloc(AllocMethod alloc, long address, long length, FreeableStorage... parents) {
		return new ArrayBufferInt(alloc.alloc(address, length * INT32.bytes, parents), length);
	}
	
	public static ArrayBufferInt malloc(MallocMethod alloc, long length, FreeableStorage... parents) {
		return new ArrayBufferInt(alloc.malloc(length * INT32.bytes, parents), length);
	}
	
	public ArrayBufferInt(DirectBuffer buffer) {
		super(buffer, INT32);
	}
	
	protected ArrayBufferInt(DirectBuffer buffer, long length) {
		super(buffer, INT32, length);
	}
	
	//get / put
	public int getInt(long index) {
		return buffer.getInt(getOffset(index));
	}
	
	public void putInt(long index, int b) {
		buffer.putInt(getOffset(index), b);
	}
	
	//array
	public void copyInto(int[] dest) {
		buffer.copyInto(dest);
	}
	
	public void copyInto(long index, int[] dest) {
		buffer.copyInto(getOffset(index), dest);
	}
	
	public void copyInto(long index, int[] dest, int destPos, int length) {
		buffer.copyInto(getOffset(index), dest, destPos, length);
	}
	
	public void copyFrom(int[] src) {
		buffer.copyFrom(src);
	}
	
	public void copyFrom(int[] src, long index) {
		buffer.copyFrom(src, getOffset(index));
	}
	
	public void copyFrom(int[] src, int srcPos, int length, long index) {
		buffer.copyFrom(src, srcPos, length, getOffset(index));
	}
	
	//single
	public static ArrayBufferIntSingle allocSingle(AllocMethod alloc, long address, FreeableStorage... parents) {
		return new ArrayBufferIntSingle(alloc.alloc(address, INT32.bytes, parents));
	}
	
	public static ArrayBufferIntSingle mallocSingle(MallocMethod alloc, FreeableStorage... parents) {
		return new ArrayBufferIntSingle(alloc.malloc(INT32.bytes, parents));
	}
	
	public static class ArrayBufferIntSingle extends AbstractArrayBuffer<ArrayBufferIntSingle> {
		
		protected ArrayBufferIntSingle(DirectBuffer buffer) {
			super(check(buffer), INT32, 1);
		}
		
		public static DirectBuffer check(DirectBuffer buffer) {
			if (buffer.capacity() != INT32.bytes)
				throw new IllegalArgumentException("Buffer too big!");
			return buffer;
		}
		
		public int getInt() {
			return buffer.getInt(0);
		}
		
		public void putInt(int b) {
			buffer.putInt(0, b);
		}
	}
}