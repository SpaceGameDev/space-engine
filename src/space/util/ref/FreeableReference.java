package space.util.ref;

import java.lang.ref.PhantomReference;

public abstract class FreeableReference extends PhantomReference<Object> implements IFreeableReference {
	
	public final FreeableReferenceList parent;
	public IFreeableReference prev;
	public IFreeableReference next;
	private FreeableReferenceList subList;
	
	public FreeableReference(Object referent, FreeableReference parent) {
		this(referent, parent.getSubList());
	}
	
	public FreeableReference(Object referent, FreeableReferenceList parent) {
		super(referent, FreeableReferenceCleaner.QUEUE);
		this.parent = parent;
		
		parent.insert(this);
	}
	
	/**
	 * here for testing only!
	 */
	FreeableReference(Object referent) {
		super(referent, FreeableReferenceCleaner.QUEUE);
		this.parent = null;
	}
	
	//getter setter prev next list
	@Override
	public IFreeableReference getPrev() {
		return prev;
	}
	
	@Override
	public void setPrev(IFreeableReference prev) {
		this.prev = prev;
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
		return parent;
	}
	
	@Override
	public int rootDistance() {
		return parent.rootDistance() + 1;
	}
	
	//free
	@Override
	public final void free() {
		if (!parent.remove(this))
			return;
		
		synchronized (this) {
			if (subList != null)
				subList.free();
		}
		
		//free
		handleFree();
	}
	
	protected abstract void handleFree();
	
	//children
	
	public synchronized FreeableReferenceList getSubList() {
		return subList != null ? subList : (subList = new FreeableReferenceList(rootDistance()));
	}
}
