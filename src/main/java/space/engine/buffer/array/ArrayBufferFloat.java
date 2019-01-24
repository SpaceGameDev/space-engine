package space.engine.buffer.array;

import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.primitive.Primitive;

import static space.engine.primitive.Primitives.FP32;

public class ArrayBufferFloat extends AbstractArrayBuffer<ArrayBufferFloat> {
	
	public static final Primitive<?> TYPE = FP32;
	
	public static ArrayAllocator<ArrayBufferFloat> createAlloc(Allocator<DirectBuffer> alloc) {
		return new ArrayAllocator<>(alloc, TYPE, ArrayBufferFloat::new);
	}
	
	public ArrayBufferFloat(DirectBuffer buffer) {
		super(buffer, TYPE);
	}
	
	protected ArrayBufferFloat(DirectBuffer buffer, long length) {
		super(buffer, TYPE, length);
	}
	
	//get / put
	public float getFloat(long index) {
		return buffer.getFloat(getOffset(index));
	}
	
	public void putFloat(long index, float b) {
		buffer.putFloat(getOffset(index), b);
	}
	
	//array
	public void copyInto(float[] dest) {
		buffer.copyInto(dest);
	}
	
	public void copyInto(long index, float[] dest) {
		buffer.copyInto(getOffset(index), dest);
	}
	
	public void copyInto(long index, float[] dest, int destPos, int length) {
		buffer.copyInto(getOffset(index), dest, destPos, length);
	}
	
	public void copyFrom(float[] src) {
		buffer.copyFrom(src);
	}
	
	public void copyFrom(float[] src, long index) {
		buffer.copyFrom(src, getOffset(index));
	}
	
	public void copyFrom(float[] src, int srcPos, int length, long index) {
		buffer.copyFrom(src, srcPos, length, getOffset(index));
	}
}