package space.util.ref.freeable;

import space.util.baseobject.additional.Freeable;
import space.util.ref.freeable.exception.FreedException;

public interface IFreeableReference extends Freeable {
	
	//prev
	IFreeableReference getPrev();
	
	void setPrev(IFreeableReference ref);
	
	//next
	IFreeableReference getNext();
	
	void setNext(IFreeableReference ref);
	
	//free
	@Override
	void free();
	
	boolean isFreed();
	
	default void throwIfFreed() throws FreedException {
		if (isFreed())
			throw new FreedException(this);
	}
	
	//other
	FreeableReferenceList getParent();
	
	int rootDistance();
	
	//children
	FreeableReferenceList getSubList();
}
