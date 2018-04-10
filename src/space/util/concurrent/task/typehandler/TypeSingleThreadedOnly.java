package space.util.concurrent.task.typehandler;

public class TypeSingleThreadedOnly<FUNCTION> implements TypeHandler<FUNCTION> {
	
	public TypeHandler<FUNCTION> handler;
	
	public TypeSingleThreadedOnly(TypeHandler<FUNCTION> handler) {
		this.handler = handler;
	}
	
	@Override
	public void accept(FUNCTION function) {
		handler.accept(function);
	}
	
	@Override
	public boolean allowMultithreading() {
		return false;
	}
}
