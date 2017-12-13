package space.util.buffer.buffers;

import space.util.baseobject.additional.Freeable;
import space.util.unsafe.UnsafeInstance;

import static space.util.unsafe.UnsafeInstance.UNSAFE;

public class SimpleBuffer implements Freeable {
	
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
	public synchronized void free() {
		if (address != 0) {
			UNSAFE.freeMemory(address);
			address = 0;
			capacity = 0;
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			free();
		} finally {
			super.finalize();
		}
	}
}
