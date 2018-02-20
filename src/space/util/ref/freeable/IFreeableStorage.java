package space.util.ref.freeable;

import space.util.baseobject.additional.Freeable;

public interface IFreeableStorage extends Freeable {
	
	IFreeableStorageList ROOT_LIST = FreeableStorageList.createList(0);
	
	/**
	 * Higher numbers are released first, lower later.
	 * Use this to increase efficiency of freeing and removing from any {@link IFreeableStorageList}
	 */
	int freePriority();
	
	/**
	 * Gets the subList of this {@link IFreeableStorage}
	 *
	 * @return a {@link IFreeableStorageList} to hook into
	 */
	IFreeableStorageList getSubList();
}
