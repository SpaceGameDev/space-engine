package space.util.ref;

import space.util.baseobject.ToString;
import space.util.string.builder.CharBufferBuilder2D;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class FreeableReferenceList implements IFreeableReference, ToString {
	
	public IFreeableReference next;
	public IFreeableReference tail = this;
	//makes remove() not remove anything, as it is pointless when free()-ing everything
	public boolean wasFreed = false;
	//to prevent any IFreeableReferences to remove themselves twice
	public boolean duringFree = true;
	public int rootDistance;
	
	public FreeableReferenceList(int rootDistance) {
		this.rootDistance = rootDistance;
	}
	
	//getter setter prev next list
	@Override
	public IFreeableReference getPrev() {
		return null;
	}
	
	@Override
	public void setPrev(IFreeableReference prev) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public IFreeableReference getNext() {
		return next;
	}
	
	@Override
	public void setNext(IFreeableReference next) {
		this.next = next;
	}
	
	@Override
	public FreeableReferenceList getParent() {
		return null;
	}
	
	@Override
	public int rootDistance() {
		return rootDistance;
	}
	
	//list operations
	
	/**
	 * inserts a new {@link IFreeableReference} into the list
	 *
	 * @param ref the reference to insert
	 */
	public synchronized void insert(IFreeableReference ref) {
		if (wasFreed)
			throwWasFreed();
		
		ref.setPrev(tail);
		tail.setNext(ref);
		tail = ref;
	}
	
	/**
	 * Removes a reference from the list. Note that if you supply a {@link IFreeableReference} not in the list, it can cause undefined behavior.
	 *
	 * @param ref the reference to remove
	 * @return if the removal was successful / valid
	 */
	public synchronized boolean remove(IFreeableReference ref) {
		if (ref.getParent() != this)
			throwWrongRefList(ref.getParent());
		
		//no actual removal during this.free() operation, removal done there
		if (wasFreed)
			return duringFree;
		
		IFreeableReference prev = ref.getPrev();
		if (prev == null)
			return false;
		IFreeableReference next = ref.getNext();
		
		if (next != null) {
			prev.setNext(next);
			next.setPrev(prev);
		} else {
			prev.setNext(null);
			tail = prev;
		}
		ref.setPrev(null);
		ref.setNext(null);
		
		return true;
	}
	
	//free
	
	/**
	 * frees all {@link IFreeableReference} in the list
	 */
	@Override
	public synchronized void free() {
		if (wasFreed)
			throwWasFreed();
		wasFreed = true;
		
		IFreeableReference ref = next;
		//help gc a little
		next = null;
		tail = null;
		
		while (ref != null) {
			ref.free();
			ref = ref.getNext();
		}
		
		duringFree = false;
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		int size = 0;
		IFreeableReference ref = this;
		while ((ref = ref.getNext()) != null)
			size++;
		
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("size", size);
		tsh.add("wasFreed", this.wasFreed);
		if (wasFreed)
			tsh.add("duringFree", this.duringFree);
		tsh.add("rootDistance", this.rootDistance);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	//errors
	private void throwWrongRefList(FreeableReferenceList list) {
		throw new IllegalArgumentException(new CharBufferBuilder2D<>().append("Wrong FreeableReferenceList! this != ref.getParent(): ").append(this).append(" != ").append(list).toString());
	}
	
	private void throwWasFreed() {
		throw new IllegalStateException(new CharBufferBuilder2D<>().append("FreeableReferenceList ").append(this).append(" already freed!").toString());
	}
}
