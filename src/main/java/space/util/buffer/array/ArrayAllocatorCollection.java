package space.util.buffer.array;

import space.util.buffer.Allocator;
import space.util.buffer.array.AbstractArrayBuffer.ArrayAllocator;
import space.util.buffer.direct.DirectBuffer;

public class ArrayAllocatorCollection {
	
	public final Allocator<DirectBuffer> alloc;
	
	public final ArrayAllocator<ArrayBufferByte> allocByte;
	public final ArrayAllocator<ArrayBufferShort> allocShort;
	public final ArrayAllocator<ArrayBufferInt> allocInt;
	public final ArrayAllocator<ArrayBufferLong> allocLong;
	public final ArrayAllocator<ArrayBufferFloat> allocFloat;
	public final ArrayAllocator<ArrayBufferDouble> allocDouble;
	public final ArrayAllocator<ArrayBufferPointer> allocPointer;
	
	public ArrayAllocatorCollection(Allocator<DirectBuffer> alloc) {
		this.alloc = alloc;
		allocByte = ArrayBufferByte.createAlloc(this.alloc);
		allocShort = ArrayBufferShort.createAlloc(this.alloc);
		allocInt = ArrayBufferInt.createAlloc(this.alloc);
		allocLong = ArrayBufferLong.createAlloc(this.alloc);
		allocFloat = ArrayBufferFloat.createAlloc(this.alloc);
		allocDouble = ArrayBufferDouble.createAlloc(this.alloc);
		allocPointer = ArrayBufferPointer.createAlloc(this.alloc);
	}
}
