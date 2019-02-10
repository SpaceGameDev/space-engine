package space.engine.event.typehandler;

import org.jetbrains.annotations.NotNull;
import space.engine.sync.DelayTask;

/**
 * A {@link TypeHandler} handles the Execution of FUNCTIONs and abstracts that.
 *
 * @param <FUNCTION> the Function type
 */
@FunctionalInterface
public interface TypeHandler<FUNCTION> {
	
	void accept(@NotNull FUNCTION function) throws DelayTask;
}
