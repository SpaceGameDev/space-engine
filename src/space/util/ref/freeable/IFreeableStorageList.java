package space.util.ref.freeable;

public interface IFreeableStorageList extends IFreeableStorage {
	
	//insert
	Entry insert(IFreeableStorage storage);
	
	//free
	@Override
	void free();
	
	@Override
	boolean isFreed();
	
	@Override
	int freePriority();
	
	//other
	@Override
	default IFreeableStorageList getSubList() {
		return this;
	}
	
	interface Entry {
		
		void remove();
	}
}
