package space.util.buffer.direct;

import space.util.freeableStorage.FreeableStorage;

/**
 * This Buffer will <b>NOT</b> be freed. <br>
 * Calling {@link NotFreeableDirectBuffer#free()} will invalidate the Buffer <b>BUT NOT free it</b>
 */
public class NotFreeableDirectBuffer extends DirectBufferImpl {
	
	public NotFreeableDirectBuffer(long address, long capacity, FreeableStorage... lists) {
		super();
		this.storage = new Storage(this, address, capacity, lists) {
			@Override
			protected synchronized void handleFree() {
			
			}
		};
	}
}
