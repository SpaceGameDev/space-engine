package space.engine.buffer.direct;

import org.jetbrains.annotations.NotNull;
import space.engine.sync.barrier.Barrier;

/**
 * This Buffer will <b>NOT</b> be freed. <br>
 * Calling {@link UnsafeNonFreeDirectBuffer#free()} will invalidate the Buffer <b>BUT NOT free it</b>
 */
public class UnsafeNonFreeDirectBuffer extends UnsafeDirectBuffer {
	
	public UnsafeNonFreeDirectBuffer(long address, long capacity, Object[] parents) {
		this.storage = new Storage(this, address, capacity, parents) {
			@Override
			protected synchronized @NotNull Barrier handleFree() {
				return Barrier.ALWAYS_TRIGGERED_BARRIER;
			}
		};
	}
}
