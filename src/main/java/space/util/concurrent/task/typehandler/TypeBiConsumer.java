package space.util.concurrent.task.typehandler;

import java.util.function.BiConsumer;

public class TypeBiConsumer<T, U> implements TypeHandler<BiConsumer<T, U>> {
	
	public T t;
	public U u;
	
	public TypeBiConsumer(T t, U u) {
		this.t = t;
		this.u = u;
	}
	
	@Override
	public void accept(BiConsumer<T, U> consumer) {
		consumer.accept(t, u);
	}
}
