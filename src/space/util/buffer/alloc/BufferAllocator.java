package space.util.buffer.alloc;

import space.util.buffer.buffers.Buffer;
import space.util.primitive.PrimitiveType;
import space.util.ref.freeable.IFreeableStorage;

public interface BufferAllocator {
	
	Buffer malloc(long capacity, IFreeableStorage... lists);
	
	Buffer alloc(long address, long capacity, IFreeableStorage... lists);
	
	default Buffer calloc(long capacity, IFreeableStorage... lists) {
		Buffer b = malloc(capacity, lists);
		b.clear();
		return b;
	}
	
	default Buffer allocByte(byte[] v, IFreeableStorage... lists) {
		Buffer b = malloc(v.length, lists);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocShort(short[] v, IFreeableStorage... lists) {
		Buffer b = malloc(v.length, lists);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocInt(int[] v, IFreeableStorage... lists) {
		Buffer b = malloc(v.length, lists);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocLong(long[] v, IFreeableStorage... lists) {
		Buffer b = malloc(v.length, lists);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocFloat(float[] v, IFreeableStorage... lists) {
		Buffer b = malloc(v.length, lists);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocDouble(double[] v, IFreeableStorage... lists) {
		Buffer b = malloc(v.length, lists);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocBoolean(boolean[] v, IFreeableStorage... lists) {
		Buffer b = malloc(v.length, lists);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer mallocByte(long capacity, IFreeableStorage... lists) {
		return malloc(capacity << PrimitiveType.BYTE.SHIFT, lists);
	}
	
	default Buffer mallocShort(long capacity, IFreeableStorage... lists) {
		return malloc(capacity << PrimitiveType.INT16.SHIFT, lists);
	}
	
	default Buffer mallocInt(long capacity, IFreeableStorage... lists) {
		return malloc(capacity << PrimitiveType.INT32.SHIFT, lists);
	}
	
	default Buffer mallocLong(long capacity, IFreeableStorage... lists) {
		return malloc(capacity << PrimitiveType.INT64.SHIFT, lists);
	}
	
	default Buffer mallocFloat(long capacity, IFreeableStorage... lists) {
		return malloc(capacity << PrimitiveType.FP32.SHIFT, lists);
	}
	
	default Buffer mallocDouble(long capacity, IFreeableStorage... lists) {
		return malloc(capacity << PrimitiveType.FP64.SHIFT, lists);
	}
	
	default Buffer mallocBoolean(long capacity, IFreeableStorage... lists) {
		return malloc(capacity << PrimitiveType.BOOLEAN.SHIFT, lists);
	}
	
	default Buffer callocByte(long capacity, IFreeableStorage... lists) {
		return calloc(capacity << PrimitiveType.BYTE.SHIFT, lists);
	}
	
	default Buffer callocShort(long capacity, IFreeableStorage... lists) {
		return calloc(capacity << PrimitiveType.INT16.SHIFT, lists);
	}
	
	default Buffer callocInt(long capacity, IFreeableStorage... lists) {
		return calloc(capacity << PrimitiveType.INT32.SHIFT, lists);
	}
	
	default Buffer callocLong(long capacity, IFreeableStorage... lists) {
		return calloc(capacity << PrimitiveType.INT64.SHIFT, lists);
	}
	
	default Buffer callocFloat(long capacity, IFreeableStorage... lists) {
		return calloc(capacity << PrimitiveType.FP32.SHIFT, lists);
	}
	
	default Buffer callocDouble(long capacity, IFreeableStorage... lists) {
		return calloc(capacity << PrimitiveType.FP64.SHIFT, lists);
	}
	
	default Buffer callocBoolean(long capacity, IFreeableStorage... lists) {
		return calloc(capacity << PrimitiveType.BOOLEAN.SHIFT, lists);
	}
	
	default Buffer mallocByteN1(long capacity, IFreeableStorage... lists) {
		Buffer buffer = mallocByte(capacity + 1, lists);
		buffer.putByte(capacity, (byte) 0);
		return buffer;
	}
	
	default Buffer mallocByteN2(long capacity, IFreeableStorage... lists) {
		Buffer buffer = mallocByte(capacity + 2, lists);
		buffer.putShort(capacity, (short) 0);
		return buffer;
	}
	
	default long memAddressSafe(Buffer buffer) {
		return buffer == null ? 0 : buffer.address();
	}
	
	default long memCapacitySafe(Buffer buffer) {
		return buffer == null ? 0 : buffer.capacity();
	}
}
