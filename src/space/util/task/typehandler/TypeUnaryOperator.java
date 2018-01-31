package space.util.task.typehandler;

import java.util.function.UnaryOperator;

public class TypeUnaryOperator<T> implements ITypeHandler<UnaryOperator<T>> {
	
	public T obj;
	
	public TypeUnaryOperator(T obj) {
		this.obj = obj;
	}
	
	@Override
	public void accept(UnaryOperator<T> unary) {
		obj = unary.apply(obj);
	}
	
	@Override
	public boolean allowMultithreading() {
		return false;
	}
}
