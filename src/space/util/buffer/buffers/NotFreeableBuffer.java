package space.util.buffer.buffers;

import space.util.freeableStorage.IFreeableStorage;

/**
 * calling {@link NotFreeableBuffer#free()} will invalidate the Buffer <b>BUT NOT free it</b>
 */
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
