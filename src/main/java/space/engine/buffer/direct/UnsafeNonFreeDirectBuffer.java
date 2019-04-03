package space.engine.buffer.direct;

/**
 * This Buffer will <b>NOT</b> be freed. <br>
 * Calling {@link UnsafeNonFreeDirectBuffer#free()} will invalidate the Buffer <b>BUT NOT free it</b>
 */
public class UnsafeNonFreeDirectBuffer extends UnsafeDirectBuffer {
	
	public UnsafeNonFreeDirectBuffer(long address, long capacity, Object[] parents) {
		this.storage = new Storage(this, address, capacity, parents) {
			@Override
			protected synchronized void handleFree() {
			
			}
		};
	}
}
