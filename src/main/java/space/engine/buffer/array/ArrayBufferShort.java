package space.engine.buffer.array;

import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.primitive.Primitive;

import static space.engine.primitive.Primitives.INT16;

public class ArrayBufferShort extends AbstractArrayBuffer<ArrayBufferShort> {
	
	public static final Primitive<?> TYPE = INT16;
	
	public static ArrayAllocator<ArrayBufferShort> createAlloc(Allocator<DirectBuffer> alloc) {
		return new ArrayAllocator<>(alloc, TYPE, ArrayBufferShort::new);
	}
	
	public ArrayBufferShort(DirectBuffer buffer) {
		super(buffer, TYPE);
	}
	
	protected ArrayBufferShort(DirectBuffer buffer, long length) {
		super(buffer, TYPE, length);
	}
	
	//get / put
	public short getShort(long index) {
		return buffer.getShort(getOffset(index));
	}
	
	public void putShort(long index, short b) {
		buffer.putShort(getOffset(index), b);
	}
	
	//array
	public void copyInto(short[] dest) {
		buffer.copyInto(dest);
	}
	
	public void copyInto(long index, short[] dest) {
		buffer.copyInto(getOffset(index), dest);
	}
	
	public void copyInto(long index, short[] dest, int destPos, int length) {
		buffer.copyInto(getOffset(index), dest, destPos, length);
	}
	
	public void copyFrom(short[] src) {
		buffer.copyFrom(src);
	}
	
	public void copyFrom(short[] src, long index) {
		buffer.copyFrom(src, getOffset(index));
	}
	
	public void copyFrom(short[] src, int srcPos, int length, long index) {
		buffer.copyFrom(src, srcPos, length, getOffset(index));
	}
}