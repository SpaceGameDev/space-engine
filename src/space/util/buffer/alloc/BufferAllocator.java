package space.util.buffer.alloc;

import space.util.buffer.buffers.Buffer;
import space.util.primitive.PrimitiveType;

public interface BufferAllocator {
	
	Buffer malloc(long capacity);
	
	Buffer alloc(long address, long capacity);
	
	default Buffer calloc(long capacity) {
		Buffer b = malloc(capacity);
		b.clear();
		return b;
	}
	
	default Buffer allocByte(byte[] v) {
		Buffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocShort(short[] v) {
		Buffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocInt(int[] v) {
		Buffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocLong(long[] v) {
		Buffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocFloat(float[] v) {
		Buffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocDouble(double[] v) {
		Buffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocBoolean(boolean[] v) {
		Buffer b = malloc(v.length);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer mallocByte(long capacity) {
		return malloc(capacity << PrimitiveType.BYTE.SHIFT);
	}
	
	default Buffer mallocShort(long capacity) {
		return malloc(capacity << PrimitiveType.INT16.SHIFT);
	}
	
	default Buffer mallocInt(long capacity) {
		return malloc(capacity << PrimitiveType.INT32.SHIFT);
	}
	
	default Buffer mallocLong(long capacity) {
		return malloc(capacity << PrimitiveType.INT64.SHIFT);
	}
	
	default Buffer mallocFloat(long capacity) {
		return malloc(capacity << PrimitiveType.FP32.SHIFT);
	}
	
	default Buffer mallocDouble(long capacity) {
		return malloc(capacity << PrimitiveType.FP64.SHIFT);
	}
	
	default Buffer mallocBoolean(long capacity) {
		return malloc(capacity << PrimitiveType.BOOLEAN.SHIFT);
	}
	
	default Buffer callocByte(long capacity) {
		return calloc(capacity << PrimitiveType.BYTE.SHIFT);
	}
	
	default Buffer callocShort(long capacity) {
		return calloc(capacity << PrimitiveType.INT16.SHIFT);
	}
	
	default Buffer callocInt(long capacity) {
		return calloc(capacity << PrimitiveType.INT32.SHIFT);
	}
	
	default Buffer callocLong(long capacity) {
		return calloc(capacity << PrimitiveType.INT64.SHIFT);
	}
	
	default Buffer callocFloat(long capacity) {
		return calloc(capacity << PrimitiveType.FP32.SHIFT);
	}
	
	default Buffer callocDouble(long capacity) {
		return calloc(capacity << PrimitiveType.FP64.SHIFT);
	}
	
	default Buffer callocBoolean(long capacity) {
		return calloc(capacity << PrimitiveType.BOOLEAN.SHIFT);
	}
	
	default Buffer mallocByteN1(long capacity) {
		Buffer buffer = mallocByte(capacity + 1);
		buffer.putByte(capacity, (byte) 0);
		return buffer;
	}
	
	default Buffer mallocByteN2(long capacity) {
		Buffer buffer = mallocByte(capacity + 2);
		buffer.putByte(capacity, (byte) 0);
		buffer.putByte(capacity + 1, (byte) 0);
		return buffer;
	}
	
	default long memAddressSafe(Buffer buffer) {
		return buffer == null ? 0 : buffer.address();
	}
}
