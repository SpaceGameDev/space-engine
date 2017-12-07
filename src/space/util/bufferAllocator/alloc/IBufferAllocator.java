package space.util.bufferAllocator.alloc;

import space.util.bufferAllocator.buffers.IBuffer;
import spaceOld.util.*;

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
		b.putByte(v);
		return b;
	}
	
	default IBuffer allocShort(short[] v) {
		IBuffer b = malloc(v.length);
		b.putShort(v);
		return b;
	}
	
	default IBuffer allocInt(int[] v) {
		IBuffer b = malloc(v.length);
		b.putInt(v);
		return b;
	}
	
	default IBuffer allocLong(long[] v) {
		IBuffer b = malloc(v.length);
		b.putLong(v);
		return b;
	}
	
	default IBuffer allocFloat(float[] v) {
		IBuffer b = malloc(v.length);
		b.putFloat(v);
		return b;
	}
	
	default IBuffer allocDouble(double[] v) {
		IBuffer b = malloc(v.length);
		b.putDouble(v);
		return b;
	}
	
	default IBuffer allocBoolean(boolean[] v) {
		IBuffer b = malloc(v.length);
		b.putBoolean(v);
		return b;
	}
	
	default IBuffer mallocByte(long capacity) {
		return malloc(capacity << PrimitiveSize.ByteShift);
	}
	
	default IBuffer mallocShort(long capacity) {
		return malloc(capacity << PrimitiveSize.int16Shift);
	}
	
	default IBuffer mallocInt(long capacity) {
		return malloc(capacity << PrimitiveSize.int32Shift);
	}
	
	default IBuffer mallocLong(long capacity) {
		return malloc(capacity << PrimitiveSize.int64Shift);
	}
	
	default IBuffer mallocFloat(long capacity) {
		return malloc(capacity << PrimitiveSize.fp32Shift);
	}
	
	default IBuffer mallocDouble(long capacity) {
		return malloc(capacity << PrimitiveSize.fp64Shift);
	}
	
	default IBuffer mallocBoolean(long capacity) {
		return malloc(capacity << PrimitiveSize.BooleanShift);
	}
	
	default IBuffer callocByte(long capacity) {
		return calloc(capacity << PrimitiveSize.ByteShift);
	}
	
	default IBuffer callocShort(long capacity) {
		return calloc(capacity << PrimitiveSize.int16Shift);
	}
	
	default IBuffer callocInt(long capacity) {
		return calloc(capacity << PrimitiveSize.int32Shift);
	}
	
	default IBuffer callocLong(long capacity) {
		return calloc(capacity << PrimitiveSize.int64Shift);
	}
	
	default IBuffer callocFloat(long capacity) {
		return calloc(capacity << PrimitiveSize.fp32Shift);
	}
	
	default IBuffer callocDouble(long capacity) {
		return calloc(capacity << PrimitiveSize.fp64Shift);
	}
	
	default IBuffer callocBoolean(long capacity) {
		return calloc(capacity << PrimitiveSize.BooleanShift);
	}
	
	default IBuffer mallocByteN1(long capacity) {
		IBuffer buffer = mallocByte(capacity + 1);
		buffer.putByteOffset(capacity, (byte) 0);
		return buffer;
	}
	
	default IBuffer mallocByteN2(long capacity) {
		IBuffer buffer = mallocByte(capacity + 2);
		buffer.putByteOffset(capacity, (byte) 0);
		buffer.putByteOffset(capacity + 1, (byte) 0);
		return buffer;
	}
	
	default long memAddressSafe(IBuffer buffer) {
		return buffer == null ? 0 : buffer.address();
	}
	
	default long memAddressSafe(IBuffer buffer, int offset) {
		return buffer == null ? 0 : buffer.address();
	}
}
