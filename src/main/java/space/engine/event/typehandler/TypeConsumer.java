package space.engine.event.typehandler;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TypeConsumer<T> implements TypeHandler<Consumer<T>>, AllowMultithreading {
	
	public T obj;
	
	public TypeConsumer(T obj) {
		this.obj = obj;
	}
	
	@Override
	public void accept(@NotNull Consumer<T> c) {
		c.accept(obj);
	}
}
