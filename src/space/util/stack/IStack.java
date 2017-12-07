package space.util.stack;

public interface IStack<T> {
	
	void push(T t);
	
	long pushPointer(T t);
	
	T pop();
	
	T popPointer(long pointer);
}
