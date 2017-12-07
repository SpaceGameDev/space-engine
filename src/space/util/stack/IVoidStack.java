package space.util.stack;

public interface IVoidStack extends IStack<Void> {
	
	@Override
	default void push(Void t) {
		push0();
	}
	
	@Override
	default long pushPointer(Void t) {
		return pushPointer0();
	}
	
	void push0();
	
	long pushPointer0();
	
	@Override
	default Void pop() {
		pop0();
		return null;
	}
	
	@Override
	default Void popPointer(long pointer) {
		popPointer0(pointer);
		return null;
	}
	
	void pop0();
	
	void popPointer0(long pointer);
}
