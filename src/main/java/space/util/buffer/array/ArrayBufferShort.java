package space.util.buffer.array;

import space.util.buffer.alloc.AllocMethod;
import space.util.buffer.alloc.MallocMethod;
import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;

import static space.util.primitive.Primitives.INT16;

public class ArrayBufferShort extends AbstractArrayBuffer<ArrayBufferShort> {
	
	public static ArrayBufferShort alloc(AllocMethod alloc, long address, long length, FreeableStorage... parents) {
		return new ArrayBufferShort(alloc.alloc(address, INT16.multiply(length), parents), length);
	}
	
	public static ArrayBufferShort malloc(MallocMethod alloc, long length, FreeableStorage... parents) {
		return new ArrayBufferShort(alloc.malloc(INT16.multiply(length), parents), length);
	}
	
	public ArrayBufferShort(DirectBuffer buffer) {
		super(buffer, INT16);
	}
	
	protected ArrayBufferShort(DirectBuffer buffer, long length) {
		super(buffer, INT16, length);
	}
	
	//get / put
	public short getShort(long index) {
		return buffer.getShort(getOffset(index));
	}
	
	public void putShort(long index, short b) {
		buffer.putShort(getOffset(index), b);
	}
	
	//array
	public void copyInto(short[] dest) {
		buffer.copyInto(dest);
	}
	
	public void copyInto(long index, short[] dest) {
		buffer.copyInto(getOffset(index), dest);
	}
	
	public void copyInto(long index, short[] dest, int destPos, int length) {
		buffer.copyInto(getOffset(index), dest, destPos, length);
	}
	
	public void copyFrom(short[] src) {
		buffer.copyFrom(src);
	}
	
	public void copyFrom(short[] src, long index) {
		buffer.copyFrom(src, getOffset(index));
	}
	
	public void copyFrom(short[] src, int srcPos, int length, long index) {
		buffer.copyFrom(src, srcPos, length, getOffset(index));
	}
	
	//single
	public static ArrayBufferShortSingle allocSingle(AllocMethod alloc, long address, FreeableStorage... parents) {
		return new ArrayBufferShortSingle(alloc.alloc(address, INT16.BYTES, parents));
	}
	
	public static ArrayBufferShortSingle mallocSingle(MallocMethod alloc, FreeableStorage... parents) {
		return new ArrayBufferShortSingle(alloc.malloc(INT16.BYTES, parents));
	}
	
	public static class ArrayBufferShortSingle extends AbstractArrayBuffer<ArrayBufferShortSingle> {
		
		protected ArrayBufferShortSingle(DirectBuffer buffer) {
			super(check(buffer), INT16, 1);
		}
		
		public static DirectBuffer check(DirectBuffer buffer) {
			if (buffer.capacity() != INT16.BYTES)
				throw new IllegalArgumentException("Buffer too big!");
			return buffer;
		}
		
		public short getShort() {
			return buffer.getShort(0);
		}
		
		public void putShort(short b) {
			buffer.putShort(0, b);
		}
	}
}