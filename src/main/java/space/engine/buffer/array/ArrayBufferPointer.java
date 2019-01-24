package space.engine.buffer.array;

import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.primitive.Primitive;

import static space.engine.primitive.Primitives.POINTER;

public class ArrayBufferPointer extends AbstractArrayBuffer<ArrayBufferPointer> {
	
	public static final Primitive<?> TYPE = POINTER;
	
	public static ArrayAllocator<ArrayBufferPointer> createAlloc(Allocator<DirectBuffer> alloc) {
		return new ArrayAllocator<>(alloc, TYPE, ArrayBufferPointer::new);
	}
	
	public ArrayBufferPointer(DirectBuffer buffer) {
		super(buffer, TYPE);
	}
	
	protected ArrayBufferPointer(DirectBuffer buffer, long length) {
		super(buffer, TYPE, length);
	}
	
	//get / put
	public long getPointer(long index) {
		return buffer.getLong(getOffset(index));
	}
	
	public void putPointer(long index, long b) {
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