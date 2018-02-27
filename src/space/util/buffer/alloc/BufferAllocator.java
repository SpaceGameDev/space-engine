package space.util.buffer.alloc;

import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.IFreeableStorage;
import space.util.primitive.NativeType;

/**
 * An Allocator for {@link DirectBuffer Buffers}. It can:
 * <ul>
 * <li>{@link BufferAllocator#alloc(long, long, IFreeableStorage...)} create a Buffer-Object with address and capacity</li>
 * <li>{@link BufferAllocator#malloc(long, IFreeableStorage...)} allocate a Buffer containing uninitialized memory</li>
 * <li>{@link BufferAllocator#calloc(long, IFreeableStorage...)} allocate a Buffer containing cleared out data / all zeros</li>
 * <li>{@link BufferAllocator#allocByte(byte[], IFreeableStorage...) BufferAllocator.allocTYPE(TYPE[], IFreeableStorage...)} allocate a Buffer containing data of an array</li>
 * <li>{@link BufferAllocator#mallocByte(long, IFreeableStorage...) BufferAllocator.mallocTYPE(size, IFreeableStorage...)} allocate a Buffer having enough capacity for size many entries</li>
 * <li>{@link BufferAllocator#mallocByteN1(long, IFreeableStorage...) BufferAllocator.mallocByteNX(size, IFreeableStorage...)} allocate a Buffer for C-like Strings with X {1, 2} many Null-terminators</li>
 * </ul>
 */
public interface BufferAllocator {
	
	DirectBuffer alloc(long address, long capacity, IFreeableStorage... parents);
	
	DirectBuffer allocNoFree(long address, long capacity, IFreeableStorage... parents);
	
	DirectBuffer malloc(long capacity, IFreeableStorage... parents);
	
	default DirectBuffer calloc(long capacity, IFreeableStorage... parents) {
		DirectBuffer b = malloc(capacity, parents);
		b.clear();
		return b;
	}
	
	default DirectBuffer allocByte(byte[] v, IFreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer allocShort(short[] v, IFreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer allocInt(int[] v, IFreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer allocLong(long[] v, IFreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer allocFloat(float[] v, IFreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer allocDouble(double[] v, IFreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer allocBoolean(boolean[] v, IFreeableStorage... parents) {
		DirectBuffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default DirectBuffer mallocByte(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << NativeType.BYTE.SHIFT, parents);
	}
	
	default DirectBuffer mallocShort(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << NativeType.INT16.SHIFT, parents);
	}
	
	default DirectBuffer mallocInt(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << NativeType.INT32.SHIFT, parents);
	}
	
	default DirectBuffer mallocLong(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << NativeType.INT64.SHIFT, parents);
	}
	
	default DirectBuffer mallocFloat(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << NativeType.FP32.SHIFT, parents);
	}
	
	default DirectBuffer mallocDouble(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << NativeType.FP64.SHIFT, parents);
	}
	
	default DirectBuffer mallocBoolean(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << NativeType.BOOLEAN.SHIFT, parents);
	}
	
	default DirectBuffer mallocPointer(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << NativeType.POINTER.SHIFT, parents);
	}
	
	default DirectBuffer callocByte(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << NativeType.BYTE.SHIFT, parents);
	}
	
	default DirectBuffer callocShort(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << NativeType.INT16.SHIFT, parents);
	}
	
	default DirectBuffer callocInt(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << NativeType.INT32.SHIFT, parents);
	}
	
	default DirectBuffer callocLong(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << NativeType.INT64.SHIFT, parents);
	}
	
	default DirectBuffer callocFloat(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << NativeType.FP32.SHIFT, parents);
	}
	
	default DirectBuffer callocDouble(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << NativeType.FP64.SHIFT, parents);
	}
	
	default DirectBuffer callocBoolean(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << NativeType.BOOLEAN.SHIFT, parents);
	}
	
	default DirectBuffer callocPointer(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << NativeType.POINTER.SHIFT, parents);
	}
	
	default DirectBuffer mallocByteN1(long capacity, IFreeableStorage... parents) {
		DirectBuffer buffer = mallocByte(capacity + 1, parents);
		buffer.putByte(capacity, (byte) 0);
		return buffer;
	}
	
	default DirectBuffer mallocByteN2(long capacity, IFreeableStorage... parents) {
		DirectBuffer buffer = mallocByte(capacity + 2, parents);
		buffer.putShort(capacity, (short) 0);
		return buffer;
	}
}
