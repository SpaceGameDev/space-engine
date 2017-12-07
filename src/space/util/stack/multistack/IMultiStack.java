package space.util.stack.multistack;

import java.util.function.Consumer;

public interface IMultiStack<T> {
	
	void setOnDelete(Consumer<T> onDelete);
	
	<X extends T> X put(X t);
	
	void push();
	
	long pushPointer();
	
	void pop();
	
	void popPointer(long pointer);
}