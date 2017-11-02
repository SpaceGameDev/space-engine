package space.util.sync.task.function.typehandler;

import java.util.function.Consumer;

public interface ITypeHandler<FUNCTION> extends Consumer<FUNCTION> {
	
	@Override
	void accept(FUNCTION function);
	
	default boolean allowMultithreading() {
		return true;
	}
}
