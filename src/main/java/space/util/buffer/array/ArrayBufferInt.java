package space.util.buffer.array;

import space.util.buffer.Allocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.primitive.Primitive;

import static space.util.primitive.Primitives.INT32;

public class ArrayBufferInt extends AbstractArrayBuffer<ArrayBufferInt> {
	
	public static final Primitive<?> TYPE = INT32;
	
	public static ArrayAllocator<ArrayBufferInt> createAlloc(Allocator<DirectBuffer> alloc) {
		return new ArrayAllocator<>(alloc, TYPE, ArrayBufferInt::new);
	}
	
	public ArrayBufferInt(DirectBuffer buffer) {
		super(buffer, TYPE);
	}
	
	protected ArrayBufferInt(DirectBuffer buffer, long length) {
		super(buffer, TYPE, length);
	}
	
	//get / put
	public int getInt(long index) {
		return buffer.getInt(getOffset(index));
	}
	
	public void putInt(long index, int b) {
		buffer.putInt(getOffset(index), b);
	}
	
	//array
	public void copyInto(int[] dest) {
		buffer.copyInto(dest);
	}
	
	public void copyInto(long index, int[] dest) {
		buffer.copyInto(getOffset(index), dest);
	}
	
	public void copyInto(long index, int[] dest, int destPos, int length) {
		buffer.copyInto(getOffset(index), dest, destPos, length);
	}
	
	public void copyFrom(int[] src) {
		buffer.copyFrom(src);
	}
	
	public void copyFrom(int[] src, long index) {
		buffer.copyFrom(src, getOffset(index));
	}
	
	public void copyFrom(int[] src, int srcPos, int length, long index) {
		buffer.copyFrom(src, srcPos, length, getOffset(index));
	}
}