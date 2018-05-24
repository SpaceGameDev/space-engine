package space.util.baseobject;

import org.jetbrains.annotations.NotNull;
import space.util.baseobject.exceptions.FreedException;
import space.util.freeableStorage.FreeableStorage;
import space.util.freeableStorage.FreeableStorageList;

public interface Freeable {
	
	void free();
	
	boolean isFreed();
	
	default void throwIfFreed() throws FreedException {
		if (isFreed())
			throw new FreedException(this);
	}
	
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
		
		@Override
		default int freePriority() {
			return getStorage().freePriority();
		}
		
		@NotNull
		@Override
		default FreeableStorageList getSubList() {
			return getStorage().getSubList();
		}
	}
}
