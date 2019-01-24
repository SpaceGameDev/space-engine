package space.engine.buffer.array;

import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.primitive.Primitive;

import static space.engine.primitive.Primitives.INT64;

public class ArrayBufferLong extends AbstractArrayBuffer<ArrayBufferLong> {
	
	public static final Primitive<?> TYPE = INT64;
	
	public static ArrayAllocator<ArrayBufferLong> createAlloc(Allocator<DirectBuffer> alloc) {
		return new ArrayAllocator<>(alloc, TYPE, ArrayBufferLong::new);
	}
	
	public ArrayBufferLong(DirectBuffer buffer) {
		super(buffer, TYPE);
	}
	
	protected ArrayBufferLong(DirectBuffer buffer, long length) {
		super(buffer, TYPE, length);
	}
	
	//get / put
	public long getLong(long index) {
		return buffer.getLong(getOffset(index));
	}
	
	public void putLong(long index, long b) {
		buffer.putLong(getOffset(index), b);
	}
	
	//array
	public void copyInto(long[] dest) {
		buffer.copyInto(dest);
	}
	
	public void copyInto(long index, long[] dest) {
		buffer.copyInto(getOffset(index), dest);
	}
	
	public void copyInto(long index, long[] dest, int destPos, int length) {
		buffer.copyInto(getOffset(index), dest, destPos, length);
	}
	
	public void copyFrom(long[] src) {
		buffer.copyFrom(src);
	}
	
	public void copyFrom(long[] src, long index) {
		buffer.copyFrom(src, getOffset(index));
	}
	
	public void copyFrom(long[] src, int srcPos, int length, long index) {
		buffer.copyFrom(src, srcPos, length, getOffset(index));
	}
}