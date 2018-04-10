package space.util.buffer.array;

import space.util.buffer.alloc.AllocMethod;
import space.util.buffer.alloc.MallocMethod;
import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;

import static space.util.primitive.NativeType.INT8;

public class ArrayBufferByte extends AbstractArrayBuffer<ArrayBufferByte> {
	
	public static ArrayBufferByte alloc(AllocMethod alloc, long address, long length, FreeableStorage... parents) {
		return new ArrayBufferByte(alloc.alloc(address, INT8.multiply(length), parents), length);
	}
	
	public static ArrayBufferByte malloc(MallocMethod alloc, long length, FreeableStorage... parents) {
		return new ArrayBufferByte(alloc.malloc(INT8.multiply(length), parents), length);
	}
	
	public ArrayBufferByte(DirectBuffer buffer) {
		super(buffer, INT8);
	}
	
	protected ArrayBufferByte(DirectBuffer buffer, long length) {
		super(buffer, INT8, length);
	}
	
	//get / put
	public byte getByte(long index) {
		return buffer.getByte(getOffset(index));
	}
	
	public void putByte(long index, byte b) {
		buffer.putByte(getOffset(index), b);
	}
	
	//array
	public void copyInto(byte[] dest) {
		buffer.copyInto(dest);
	}
	
	public void copyInto(long index, byte[] dest) {
		buffer.copyInto(getOffset(index), dest);
	}
	
	public void copyInto(long index, byte[] dest, int destPos, int length) {
		buffer.copyInto(getOffset(index), dest, destPos, length);
	}
	
	public void copyFrom(byte[] src) {
		buffer.copyFrom(src);
	}
	
	public void copyFrom(byte[] src, long index) {
		buffer.copyFrom(src, getOffset(index));
	}
	
	public void copyFrom(byte[] src, int srcPos, int length, long index) {
		buffer.copyFrom(src, srcPos, length, getOffset(index));
	}
	
	//single
	public static ArrayBufferByteSingle allocSingle(AllocMethod alloc, long address, FreeableStorage... parents) {
		return new ArrayBufferByteSingle(alloc.alloc(address, INT8.BYTES, parents));
	}
	
	public static ArrayBufferByteSingle mallocSingle(MallocMethod alloc, FreeableStorage... parents) {
		return new ArrayBufferByteSingle(alloc.malloc(INT8.BYTES, parents));
	}
	
	public static class ArrayBufferByteSingle extends AbstractArrayBuffer<ArrayBufferByteSingle> {
		
		protected ArrayBufferByteSingle(DirectBuffer buffer) {
			super(check(buffer), INT8, 1);
		}
		
		public static DirectBuffer check(DirectBuffer buffer) {
			if (buffer.capacity() != INT8.BYTES)
				throw new IllegalArgumentException("Buffer too big!");
			return buffer;
		}
		
		public byte getByte() {
			return buffer.getByte(0);
		}
		
		public void putByte(byte b) {
			buffer.putByte(0, b);
		}
	}
}