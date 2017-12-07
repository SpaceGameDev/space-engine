package space.util.stack.multistack;

import spaceOld.util.stack.PointerList;

import java.util.ArrayList;
import java.util.function.Consumer;

public class MultiStack<T> implements MultiStackWithPointerList<T> {
	
	public static final int defaultPushPopSize = 10;
	public static final float defaultpushPopExpander = 1.5f;
	
	public ArrayList<T> list = new ArrayList<>();
	public Consumer<T> onDelete;
	
	public PointerList pushPopPointer;
	
	public MultiStack() {
		this(null, defaultPushPopSize, defaultpushPopExpander);
	}
	
	public MultiStack(int pushPopSize) {
		this(null, pushPopSize, defaultpushPopExpander);
	}
	
	public MultiStack(Consumer<T> onDelete) {
		this(onDelete, defaultPushPopSize, defaultpushPopExpander);
	}
	
	public MultiStack(Consumer<T> onDelete, int pushPopSize) {
		this(onDelete, pushPopSize, defaultpushPopExpander);
	}
	
	public MultiStack(Consumer<T> onDelete, int pushPopSize, float pushPopExpander) {
		this.onDelete = onDelete;
		this.pushPopPointer = new PointerList();
	}
	
	@Override
	public <X extends T> X put(X t) {
		list.add(t);
		return t;
	}
	
	@Override
	public void setOnDelete(Consumer<T> onDelete) {
		this.onDelete = onDelete;
	}
	
	@Override
	public long pushPointer() {
		return list.size();
	}
	
	@Override
	public void popPointer(long id) {
		boolean activeOnDelete = onDelete != null;
		for (int i = (int) id; i < list.size(); i++) {
			T t = list.remove(i);
			if (activeOnDelete)
				onDelete.accept(t);
		}
	}
	
	@Override
	public PointerList pointerList() {
		return pushPopPointer;
	}
}
