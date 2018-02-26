package space.util.baseobject.additional;

import space.util.baseobject.exceptions.FreedException;
import space.util.freeableStorage.IFreeableStorage;
import space.util.freeableStorage.IFreeableStorageList;

public interface Freeable {
	
	void free();
	
	boolean isFreed();
	
	default void throwIfFreed() throws FreedException {
		if (isFreed())
			throw new FreedException(this);
	}
	
	interface FreeableWithStorage extends IFreeableStorage {
		
		IFreeableStorage getStorage();
		
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
		
		@Override
		default IFreeableStorageList getSubList() {
			return getStorage().getSubList();
		}
	}
}
