package space.util.buffer.buffers;

import space.util.ref.freeable.IFreeableStorage;

public class NotFreeableBuffer extends BufferImpl {
	
	public NotFreeableBuffer(long address, long capacity, IFreeableStorage... lists) {
		super();
		this.storage = new Storage(this, address, capacity, lists) {
			@Override
			protected synchronized void handleFree() {
			
			}
		};
	}
}
