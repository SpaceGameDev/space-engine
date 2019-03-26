package space.engine.baseobject;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.exceptions.FreedException;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.freeableStorage.FreeableStorageList;

/**
 * A Resource which can be freed by calling {@link Freeable#free()}.
 * <b>There is no guarantee that {@link Freeable#free()} will be called only once.</b>
 * Free status can be checked by {@link Freeable#isFreed()}
 */
public interface Freeable {
	
	/**
	 * Frees the resource
	 */
	void free();
	
	/**
	 * Checks if the resource was already freed
	 *
	 * @return true if already freed
	 */
	boolean isFreed();
	
	/**
	 * Throws an {@link FreedException} if the resource was already freed
	 *
	 * @throws FreedException if already freed
	 */
	default void throwIfFreed() throws FreedException {
		if (isFreed())
			throw new FreedException(this);
	}
	
	/**
	 * A Simple implementation if a call is using a backend {@link FreeableStorage} for releasing resources.
	 */
	interface FreeableWithStorage extends FreeableStorage {
		
		@NotNull FreeableStorage getStorage();
		
		@Override
		default void free() {
			getStorage().free();
		}
		
		@Override
		default boolean isFreed() {
			return getStorage().isFreed();
		}
		
		@NotNull
		@Override
		default FreeableStorageList getSubList() {
			return getStorage().getSubList();
		}
	}
}
