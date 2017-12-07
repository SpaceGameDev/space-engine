package space.util.stack.multistack;

import spaceOld.util.stack.PointerList;

public interface MultiStackWithPointerList<T> extends IMultiStack<T> {
	
	PointerList pointerList();
	
	@Override
	default void push() {
		pointerList().push(pushPointer());
	}
	
	@Override
	default void pop() {
		popPointer(pointerList().pop());
	}
}
