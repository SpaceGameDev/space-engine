package space.util.buffer.array;

import space.util.buffer.alloc.IAllocMethod;
import space.util.buffer.alloc.IMallocMethod;
import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.IFreeableStorage;

import static space.util.primitive.NativeType.FP32;

public class ArrayBufferFloat extends AbstractArrayBuffer<ArrayBufferFloat> {
	
	public static ArrayBufferFloat alloc(IAllocMethod alloc, long address, long length, IFreeableStorage... parents) {
		return new ArrayBufferFloat(alloc.alloc(address, FP32.multiply(length), parents), length);
	}
	
	public static ArrayBufferFloat malloc(IMallocMethod alloc, long length, IFreeableStorage... parents) {
		return new ArrayBufferFloat(alloc.malloc(FP32.multiply(length), parents), length);
	}
	
	public ArrayBufferFloat(DirectBuffer buffer) {
		super(buffer, FP32);
	}
	
	protected ArrayBufferFloat(DirectBuffer buffer, long length) {
		super(buffer, FP32, length);
	}
	
	//get / put
	public float getFloat(long index) {
		return buffer.getFloat(getOffset(index));
	}
	
	public void putFloat(long index, float b) {
		buffer.putFloat(getOffset(index), b);
	}
	
	//array
	public void copyInto(float[] dest) {
		buffer.copyInto(dest);
	}
	
	public void copyInto(long index, float[] dest) {
		buffer.copyInto(getOffset(index), dest);
	}
	
	public void copyInto(long index, float[] dest, int destPos, int length) {
		buffer.copyInto(getOffset(index), dest, destPos, length);
	}
	
	public void copyFrom(float[] src) {
		buffer.copyFrom(src);
	}
	
	public void copyFrom(float[] src, long index) {
		buffer.copyFrom(src, getOffset(index));
	}
	
	public void copyFrom(float[] src, int srcPos, int length, long index) {
		buffer.copyFrom(src, srcPos, length, getOffset(index));
	}
	
	//single
	public static ArrayBufferFloatSingle allocSingle(IAllocMethod alloc, long address, IFreeableStorage... parents) {
		return new ArrayBufferFloatSingle(alloc.alloc(address, FP32.BYTES, parents));
	}
	
	public static ArrayBufferFloatSingle mallocSingle(IMallocMethod alloc, IFreeableStorage... parents) {
		return new ArrayBufferFloatSingle(alloc.malloc(FP32.BYTES, parents));
	}
	
	public static class ArrayBufferFloatSingle extends AbstractArrayBuffer<ArrayBufferFloatSingle> {
		
		protected ArrayBufferFloatSingle(DirectBuffer buffer) {
			super(check(buffer), FP32, 1);
		}
		
		public static DirectBuffer check(DirectBuffer buffer) {
			if (buffer.capacity() != FP32.BYTES)
				throw new IllegalArgumentException("Buffer too big!");
			return buffer;
		}
		
		public float getFloat() {
			return buffer.getFloat(0);
		}
		
		public void putFloat(float b) {
			buffer.putFloat(0, b);
		}
	}
}