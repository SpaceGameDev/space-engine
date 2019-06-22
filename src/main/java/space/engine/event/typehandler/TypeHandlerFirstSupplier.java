package space.engine.event.typehandler;

import org.jetbrains.annotations.NotNull;
import space.engine.sync.Tasks.SupplierWithDelay;

import java.util.function.Supplier;

/**
 * The {@link #result()} will be the first {@link SupplierWithDelay} returning something != null.
 * Sequential only.
 */
public class TypeHandlerFirstSupplier<T> implements TypeHandler<Supplier<T>> {
	
	private T result;
	
	@Override
	public void accept(@NotNull Supplier<T> task) {
		if (result == null)
			result = task.get();
	}
	
	public T result() {
		return result;
	}
}
