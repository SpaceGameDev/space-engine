package space.util.freeableStorage;

public interface FreeableStorageList extends FreeableStorage {
	
	//insert
	Entry insert(FreeableStorage storage);
	
	//free
	@Override
	void free();
	
	@Override
	boolean isFreed();
	
	@Override
	int freePriority();
	
	//other
	@Override
	default FreeableStorageList getSubList() {
		return this;
	}
	
	interface Entry {
		
		void remove();
	}
}
