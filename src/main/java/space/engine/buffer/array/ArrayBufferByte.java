package space.engine.buffer.array;

import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.primitive.Primitive;

import static space.engine.primitive.Primitives.INT8;

public class ArrayBufferByte extends AbstractArrayBuffer<ArrayBufferByte> {
	
	public static final Primitive<?> TYPE = INT8;
	
	public static ArrayAllocator<ArrayBufferByte> createAlloc(Allocator<DirectBuffer> alloc) {
		return new AbstractArrayBuffer.ArrayAllocator<>(alloc, TYPE, ArrayBufferByte::new);
	}
	
	public ArrayBufferByte(DirectBuffer buffer) {
		super(buffer, TYPE);
	}
	
	protected ArrayBufferByte(DirectBuffer buffer, long length) {
		super(buffer, TYPE, length);
	}
	
	//get / put
	public byte getByte(long index) {
		return buffer.getByte(getOffset(index));
	}
	
	public void putByte(long index, byte b) {
		buffer.putByte(getOffset(index), b);
	}
	
	//array
	public void copyInto(byte[] dest) {
		buffer.copyInto(dest);
	}
	
	public void copyInto(long index, byte[] dest) {
		buffer.copyInto(getOffset(index), dest);
	}
	
	public void copyInto(long index, byte[] dest, int destPos, int length) {
		buffer.copyInto(getOffset(index), dest, destPos, length);
	}
	
	public void copyFrom(byte[] src) {
		buffer.copyFrom(src);
	}
	
	public void copyFrom(byte[] src, long index) {
		buffer.copyFrom(src, getOffset(index));
	}
	
	public void copyFrom(byte[] src, int srcPos, int length, long index) {
		buffer.copyFrom(src, srcPos, length, getOffset(index));
	}
}