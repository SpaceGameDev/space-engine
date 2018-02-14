package space.util.ref.freeable.types;

import space.util.ref.freeable.FreeableReferenceCleaner;
import space.util.ref.freeable.FreeableReferenceList;
import space.util.ref.freeable.IFreeableReference;

import java.lang.ref.WeakReference;

public abstract class FreeableReferenceWeak<T> extends WeakReference<T> implements IFreeableReference {
	
	public final FreeableReferenceList parent;
	public IFreeableReference prev;
	public IFreeableReference next;
	private FreeableReferenceList subList;
	
	public FreeableReferenceWeak(T referent, IFreeableReference getSubList) {
		super(referent, FreeableReferenceCleaner.QUEUE);
		this.parent = getSubList.getSubList();
		
		parent.insert(this);
	}
	
	//getter setter prev next
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
	
	@Override
	public boolean isFreed() {
		return prev == null;
	}
	
	//other
	@Override
	public FreeableReferenceList getParent() {
		return parent;
	}
	
	@Override
	public int rootDistance() {
		return parent.rootDistance() + 1;
	}
	
	//children
	@Override
	public synchronized FreeableReferenceList getSubList() {
		return subList != null ? subList : (subList = new FreeableReferenceList(rootDistance()));
	}
}
