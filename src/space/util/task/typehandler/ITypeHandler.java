package space.util.task.typehandler;

import java.util.function.Consumer;

/**
 * A {@link ITypeHandler} handles the Execution of FUNCTIONs, abstracting that.
 *
 * @param <FUNCTION> the Function type
 */
@FunctionalInterface
public interface ITypeHandler<FUNCTION> extends Consumer<FUNCTION> {
	
	@Override
	void accept(FUNCTION function);
	
	default boolean allowMultithreading() {
		return true;
	}
}
