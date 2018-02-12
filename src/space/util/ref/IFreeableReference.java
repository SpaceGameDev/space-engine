package space.util.ref;

import space.util.baseobject.additional.Freeable;

interface IFreeableReference extends Freeable {
	
	//prev
	IFreeableReference getPrev();
	
	void setPrev(IFreeableReference ref);
	
	//next
	IFreeableReference getNext();
	
	void setNext(IFreeableReference ref);
	
	//list
	FreeableReferenceList getParent();
	
	int rootDistance();
}
