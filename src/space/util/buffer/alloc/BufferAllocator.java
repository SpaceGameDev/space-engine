package space.util.buffer.alloc;

import space.util.buffer.buffers.Buffer;
import space.util.freeableStorage.IFreeableStorage;
import space.util.primitive.PrimitiveType;

/**
 * An Allocator for {@link Buffer Buffers}. It can:
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
	
	Buffer malloc(long capacity, IFreeableStorage... parents);
	
	Buffer alloc(long address, long capacity, IFreeableStorage... parents);
	
	default Buffer calloc(long capacity, IFreeableStorage... parents) {
		Buffer b = malloc(capacity, parents);
		b.clear();
		return b;
	}
	
	default Buffer allocByte(byte[] v, IFreeableStorage... parents) {
		Buffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocShort(short[] v, IFreeableStorage... parents) {
		Buffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocInt(int[] v, IFreeableStorage... parents) {
		Buffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocLong(long[] v, IFreeableStorage... parents) {
		Buffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocFloat(float[] v, IFreeableStorage... parents) {
		Buffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocDouble(double[] v, IFreeableStorage... parents) {
		Buffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer allocBoolean(boolean[] v, IFreeableStorage... parents) {
		Buffer b = malloc(v.length, parents);
		b.copyFrom(v);
		return b;
	}
	
	default Buffer mallocByte(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << PrimitiveType.BYTE.SHIFT, parents);
	}
	
	default Buffer mallocShort(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << PrimitiveType.INT16.SHIFT, parents);
	}
	
	default Buffer mallocInt(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << PrimitiveType.INT32.SHIFT, parents);
	}
	
	default Buffer mallocLong(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << PrimitiveType.INT64.SHIFT, parents);
	}
	
	default Buffer mallocFloat(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << PrimitiveType.FP32.SHIFT, parents);
	}
	
	default Buffer mallocDouble(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << PrimitiveType.FP64.SHIFT, parents);
	}
	
	default Buffer mallocBoolean(long capacity, IFreeableStorage... parents) {
		return malloc(capacity << PrimitiveType.BOOLEAN.SHIFT, parents);
	}
	
	default Buffer callocByte(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << PrimitiveType.BYTE.SHIFT, parents);
	}
	
	default Buffer callocShort(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << PrimitiveType.INT16.SHIFT, parents);
	}
	
	default Buffer callocInt(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << PrimitiveType.INT32.SHIFT, parents);
	}
	
	default Buffer callocLong(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << PrimitiveType.INT64.SHIFT, parents);
	}
	
	default Buffer callocFloat(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << PrimitiveType.FP32.SHIFT, parents);
	}
	
	default Buffer callocDouble(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << PrimitiveType.FP64.SHIFT, parents);
	}
	
	default Buffer callocBoolean(long capacity, IFreeableStorage... parents) {
		return calloc(capacity << PrimitiveType.BOOLEAN.SHIFT, parents);
	}
	
	default Buffer mallocByteN1(long capacity, IFreeableStorage... parents) {
		Buffer buffer = mallocByte(capacity + 1, parents);
		buffer.putByte(capacity, (byte) 0);
		return buffer;
	}
	
	default Buffer mallocByteN2(long capacity, IFreeableStorage... parents) {
		Buffer buffer = mallocByte(capacity + 2, parents);
		buffer.putShort(capacity, (short) 0);
		return buffer;
	}
}
