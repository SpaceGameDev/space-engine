package space.util.baseobject.additional;

import space.util.baseobject.exceptions.FreedException;

public interface Freeable {
	
	void free();
	
	boolean isFreed();
	
	default void throwIfFreed() throws FreedException {
		if (isFreed())
			throw new FreedException(this);
	}
}
