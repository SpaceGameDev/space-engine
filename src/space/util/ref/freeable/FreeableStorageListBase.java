package space.util.ref.freeable;

import space.util.baseobject.additional.Freeable;

abstract class FreeableStorageListBase implements Freeable {
	
	public FreeableStorageListBase prev;
	public FreeableStorageListBase next;
	
	protected void insertBefore(FreeableStorageListBase base) {
		prev = base.prev;
		next = base;
		prev.next = this;
		next.prev = this;
	}
	
	protected void remove(boolean quickRemove) {
		if (!quickRemove) {
			prev.next = next;
			next.prev = prev;
			next = null;
		}
		prev = null;
	}
	
	@Override
	public boolean isFreed() {
		return prev == null;
	}
}
