package space.util.concurrent.task.typehandler;

import java.util.function.Consumer;

public class TypeConsumer<T> implements TypeHandler<Consumer<T>> {
	
	public T obj;
	
	public TypeConsumer(T obj) {
		this.obj = obj;
	}
	
	@Override
	public void accept(Consumer<T> c) {
		c.accept(obj);
	}
}
