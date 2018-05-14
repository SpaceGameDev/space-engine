package space.util.buffer.alloc;

import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;
import space.util.primitive.NativeType;

/**
 * An Allocator for {@link DirectBuffer Buffers}. It can:
 * <ul>
 * <li>{@link BufferAllocator#alloc(long, long, FreeableStorage...)} create a Buffer-Object with address and capacity</li>
 * <li>{@link BufferAllocator#malloc(long, FreeableStorage...)} allocate a Buffer containing uninitialized memory</li>
 * <li>{@link BufferAllocator#calloc(long, FreeableStorage...)} allocate a Buffer containing cleared out data / all zeros</li>
 * <li>{@link BufferAllocator#allocByte(byte[], FreeableStorage...) BufferAllocator.allocTYPE(TYPE[], FreeableStorage...)} allocate a Buffer containing data of an array</li>
 * <li>{@link BufferAllocator#mallocByte(long, FreeableStorage...) BufferAllocator.mallocTYPE(size, FreeableStorage...)} allocate a Buffer having enough capacity for size many entries</li>
 * <li>{@link BufferAllocator#mallocByteN1(long, FreeableStorage...) BufferAllocator.mallocByteNX(size, FreeableStorage...)} allocate a Buffer for C-like Strings with X {1, 2} many Null-terminators</li>
 * </ul>
 */
public interface BufferAllocator {
	
	DirectBuffer alloc(long address, long capacity, FreeableStorage... parents);
	
	DirectBuffer allocNoFree(long address, long capacity, FreeableStorage... parents);
	
	DirectBuffer malloc(long capacity, FreeableStorage... parents);
	
	default DirectBuffer calloc(long capacity, FreeableStorage... parents) {
		DirectBuffer b = malloc(capacity, parents);
		b.clear();
		return b;
	}
	
	default DirectBuffer allocByte(byte[] v, FreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer allocShort(short[] v, FreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer allocInt(int[] v, FreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer allocLong(long[] v, FreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer allocFloat(float[] v, FreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer allocDouble(double[] v, FreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer allocBoolean(boolean[] v, FreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer mallocByte(long capacity, FreeableStorage... parents) {
		return malloc(capacity << NativeType.BYTE.SHIFT, parents);
	}
	
	default DirectBuffer mallocShort(long capacity, FreeableStorage... parents) {
		return malloc(capacity << NativeType.INT16.SHIFT, parents);
	}
	
	default DirectBuffer mallocInt(long capacity, FreeableStorage... parents) {
		return malloc(capacity << NativeType.INT32.SHIFT, parents);
	}
	
	default DirectBuffer mallocLong(long capacity, FreeableStorage... parents) {
		return malloc(capacity << NativeType.INT64.SHIFT, parents);
	}
	
	default DirectBuffer mallocFloat(long capacity, FreeableStorage... parents) {
		return malloc(capacity << NativeType.FP32.SHIFT, parents);
	}
	
	default DirectBuffer mallocDouble(long capacity, FreeableStorage... parents) {
		return malloc(capacity << NativeType.FP64.SHIFT, parents);
	}
	
	default DirectBuffer mallocBoolean(long capacity, FreeableStorage... parents) {
		return malloc(capacity << NativeType.BOOLEAN.SHIFT, parents);
	}
	
	default DirectBuffer mallocPointer(long capacity, FreeableStorage... parents) {
		return malloc(capacity << NativeType.POINTER.SHIFT, parents);
	}
	
	default DirectBuffer callocByte(long capacity, FreeableStorage... parents) {
		return calloc(capacity << NativeType.BYTE.SHIFT, parents);
	}
	
	default DirectBuffer callocShort(long capacity, FreeableStorage... parents) {
		return calloc(capacity << NativeType.INT16.SHIFT, parents);
	}
	
	default DirectBuffer callocInt(long capacity, FreeableStorage... parents) {
		return calloc(capacity << NativeType.INT32.SHIFT, parents);
	}
	
	default DirectBuffer callocLong(long capacity, FreeableStorage... parents) {
		return calloc(capacity << NativeType.INT64.SHIFT, parents);
	}
	
	default DirectBuffer callocFloat(long capacity, FreeableStorage... parents) {
		return calloc(capacity << NativeType.FP32.SHIFT, parents);
	}
	
	default DirectBuffer callocDouble(long capacity, FreeableStorage... parents) {
		return calloc(capacity << NativeType.FP64.SHIFT, parents);
	}
	
	default DirectBuffer callocBoolean(long capacity, FreeableStorage... parents) {
		return calloc(capacity << NativeType.BOOLEAN.SHIFT, parents);
	}
	
	default DirectBuffer callocPointer(long capacity, FreeableStorage... parents) {
		return calloc(capacity << NativeType.POINTER.SHIFT, parents);
	}
	
	default DirectBuffer mallocByteN1(long capacity, FreeableStorage... parents) {
		DirectBuffer buffer = mallocByte(capacity + 1, parents);
		buffer.putByte(capacity, (byte) 0);
		return buffer;
	}
	
	default DirectBuffer mallocByteN2(long capacity, FreeableStorage... parents) {
		DirectBuffer buffer = mallocByte(capacity + 2, parents);
		buffer.putShort(capacity, (short) 0);
		return buffer;
	}
}
