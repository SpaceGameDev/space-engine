package space.util.buffer.alloc;

import space.util.buffer.buffers.IBuffer;
import space.util.primitive.PrimitiveType;

public interface IBufferAllocator {
	
	IBuffer malloc(long capacity);
	
	IBuffer alloc(long address, long capacity);
	
	default IBuffer calloc(long capacity) {
		IBuffer b = malloc(capacity);
		b.clear();
		return b;
	}
	
	default IBuffer allocByte(byte[] v) {
		IBuffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default IBuffer allocShort(short[] v) {
		IBuffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default IBuffer allocInt(int[] v) {
		IBuffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default IBuffer allocLong(long[] v) {
		IBuffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default IBuffer allocFloat(float[] v) {
		IBuffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default IBuffer allocDouble(double[] v) {
		IBuffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default IBuffer allocBoolean(boolean[] v) {
		IBuffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default IBuffer mallocByte(long capacity) {
		return malloc(capacity << PrimitiveType.BYTE.SHIFT);
	}
	
	default IBuffer mallocShort(long capacity) {
		return malloc(capacity << PrimitiveType.INT16.SHIFT);
	}
	
	default IBuffer mallocInt(long capacity) {
		return malloc(capacity << PrimitiveType.INT32.SHIFT);
	}
	
	default IBuffer mallocLong(long capacity) {
		return malloc(capacity << PrimitiveType.INT64.SHIFT);
	}
	
	default IBuffer mallocFloat(long capacity) {
		return malloc(capacity << PrimitiveType.FP32.SHIFT);
	}
	
	default IBuffer mallocDouble(long capacity) {
		return malloc(capacity << PrimitiveType.FP64.SHIFT);
	}
	
	default IBuffer mallocBoolean(long capacity) {
		return malloc(capacity << PrimitiveType.BOOLEAN.SHIFT);
	}
	
	default IBuffer callocByte(long capacity) {
		return calloc(capacity << PrimitiveType.BYTE.SHIFT);
	}
	
	default IBuffer callocShort(long capacity) {
		return calloc(capacity << PrimitiveType.INT16.SHIFT);
	}
	
	default IBuffer callocInt(long capacity) {
		return calloc(capacity << PrimitiveType.INT32.SHIFT);
	}
	
	default IBuffer callocLong(long capacity) {
		return calloc(capacity << PrimitiveType.INT64.SHIFT);
	}
	
	default IBuffer callocFloat(long capacity) {
		return calloc(capacity << PrimitiveType.FP32.SHIFT);
	}
	
	default IBuffer callocDouble(long capacity) {
		return calloc(capacity << PrimitiveType.FP64.SHIFT);
	}
	
	default IBuffer callocBoolean(long capacity) {
		return calloc(capacity << PrimitiveType.BOOLEAN.SHIFT);
	}
	
	default IBuffer mallocByteN1(long capacity) {
		IBuffer buffer = mallocByte(capacity + 1);
		buffer.putByte(capacity, (byte) 0);
		return buffer;
	}
	
	default IBuffer mallocByteN2(long capacity) {
		IBuffer buffer = mallocByte(capacity + 2);
		buffer.putByte(capacity, (byte) 0);
		buffer.putByte(capacity + 1, (byte) 0);
		return buffer;
	}
	
	default long memAddressSafe(IBuffer buffer) {
		return buffer == null ? 0 : buffer.address();
	}
}
