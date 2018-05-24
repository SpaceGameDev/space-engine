package space.util.freeableStorage;

import org.jetbrains.annotations.NotNull;

public interface FreeableStorageList extends FreeableStorage {
	
	//insert
	@NotNull Entry insert(@NotNull FreeableStorage storage);
	
	//free
	@Override
	void free();
	
	@Override
	boolean isFreed();
	
	@Override
	int freePriority();
	
	//other
	@NotNull
	@Override
	default FreeableStorageList getSubList() {
		return this;
	}
	
	interface Entry {
		
		void remove();
	}
}
