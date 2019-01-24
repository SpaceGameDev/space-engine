package space.engine.event.typehandler;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A {@link TypeHandler} handles the Execution of FUNCTIONs and abstracts that.
 *
 * @param <FUNCTION> the Function type
 */
@FunctionalInterface
public interface TypeHandler<FUNCTION> extends Consumer<FUNCTION> {
	
	@Override
	void accept(@NotNull FUNCTION function);
}
