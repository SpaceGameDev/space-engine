package space.util.bufferAllocator.buffers;

import space.util.baseobject.additional.IReleasable;
import space.util.unsafe.UnsafeInstance;

import static space.util.unsafe.UnsafeInstance.UNSAFE;

public class SimpleBuffer implements IReleasable {
	
	static {
		UnsafeInstance.throwIfUnavailable();
	}
	
	public long address;
	public long capacity;
	
	public SimpleBuffer(long capacity) {
		this.address = UNSAFE.allocateMemory(capacity);
		this.capacity = capacity;
	}
	
	public SimpleBuffer(long address, long capacity) {
		this.address = address;
		this.capacity = capacity;
	}
	
	@Override
	public synchronized void release() {
		if (address != 0) {
			UNSAFE.freeMemory(address);
			address = 0;
		}
	}
}
