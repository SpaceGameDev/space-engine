package space.engine.buffer.pointer;

import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.buffer.pointer.AbstractPointerBuffer.PointerAllocator;

public class PointerAllocatorCollection {
	
	public final Allocator<DirectBuffer> alloc;
	
	public final PointerAllocator<PointerBufferByte> allocByte;
	public final PointerAllocator<PointerBufferShort> allocShort;
	public final PointerAllocator<PointerBufferInt> allocInt;
	public final PointerAllocator<PointerBufferLong> allocLong;
	public final PointerAllocator<PointerBufferFloat> allocFloat;
	public final PointerAllocator<PointerBufferDouble> allocDouble;
	public final PointerAllocator<PointerBufferPointer> allocPointer;
	
	public PointerAllocatorCollection(Allocator<DirectBuffer> alloc) {
		this.alloc = alloc;
		allocByte = PointerBufferByte.createAlloc(this.alloc);
		allocShort = PointerBufferShort.createAlloc(this.alloc);
		allocInt = PointerBufferInt.createAlloc(this.alloc);
		allocLong = PointerBufferLong.createAlloc(this.alloc);
		allocFloat = PointerBufferFloat.createAlloc(this.alloc);
		allocDouble = PointerBufferDouble.createAlloc(this.alloc);
		allocPointer = PointerBufferPointer.createAlloc(this.alloc);
	}
}
