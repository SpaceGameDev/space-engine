package space.util.bufferAllocator.buffers;

import space.util.releasable.IReleasable;

import static spaceOld.util.UnsafeInstance.unsafe;

public class BufferStorage implements IReleasable {
	
	public long address;
	public long capacity;
	
	public BufferStorage() {
	
	}
	
	public BufferStorage(long capacity) {
		this.address = unsafe.allocateMemory(capacity);
		this.capacity = capacity;
	}
	
	public BufferStorage(long address, long capacity) {
		this.address = address;
		this.capacity = capacity;
	}
	
	@Override
	public synchronized void release() {
		if (address != 0) {
			unsafe.freeMemory(address);
			address = 0;
		}
	}
}
