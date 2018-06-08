package space.util.buffer.array;

import space.util.buffer.Allocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;

import static space.util.primitive.Primitives.FP64;

public class ArrayBufferDouble extends AbstractArrayBuffer<ArrayBufferDouble> {
	
	public static ArrayBufferDouble alloc(AllocMethod alloc, long address, long length, FreeableStorage... parents) {
		return new ArrayBufferDouble(alloc.alloc(address, length * FP64.bytes, parents), length);
	}
	
	public static ArrayBufferDouble malloc(Allocator alloc, long length, FreeableStorage... parents) {
		return new ArrayBufferDouble(alloc.malloc(length * FP64.bytes, parents), length);
	}
	
	public ArrayBufferDouble(DirectBuffer buffer) {
		super(buffer, FP64);
	}
	
	protected ArrayBufferDouble(DirectBuffer buffer, long length) {
		super(buffer, FP64, length);
	}
	
	//get / put
	public double getDouble(long index) {
		return buffer.getDouble(getOffset(index));
	}
	
	public void putDouble(long index, double b) {
		buffer.putDouble(getOffset(index), b);
	}
	
	//array
	public void copyInto(double[] dest) {
		buffer.copyInto(dest);
	}
	
	public void copyInto(long index, double[] dest) {
		buffer.copyInto(getOffset(index), dest);
	}
	
	public void copyInto(long index, double[] dest, int destPos, int length) {
		buffer.copyInto(getOffset(index), dest, destPos, length);
	}
	
	public void copyFrom(double[] src) {
		buffer.copyFrom(src);
	}
	
	public void copyFrom(double[] src, long index) {
		buffer.copyFrom(src, getOffset(index));
	}
	
	public void copyFrom(double[] src, int srcPos, int length, long index) {
		buffer.copyFrom(src, srcPos, length, getOffset(index));
	}
	
	//single
	public static ArrayBufferDoubleSingle allocSingle(AllocMethod alloc, long address, FreeableStorage... parents) {
		return new ArrayBufferDoubleSingle(alloc.alloc(address, FP64.bytes, parents));
	}
	
	public static ArrayBufferDoubleSingle mallocSingle(Allocator alloc, FreeableStorage... parents) {
		return new ArrayBufferDoubleSingle(alloc.malloc(FP64.bytes, parents));
	}
	
	public static class ArrayBufferDoubleSingle extends AbstractArrayBuffer<ArrayBufferDoubleSingle> {
		
		protected ArrayBufferDoubleSingle(DirectBuffer buffer) {
			super(check(buffer), FP64, 1);
		}
		
		public static DirectBuffer check(DirectBuffer buffer) {
			if (buffer.capacity() != FP64.bytes)
				throw new IllegalArgumentException("Buffer too big!");
			return buffer;
		}
		
		public double getDouble() {
			return buffer.getDouble(0);
		}
		
		public void putDouble(double b) {
			buffer.putDouble(0, b);
		}
	}
}
