package space.engine.baseobject;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.exceptions.InvalidSetException;

public interface Setable {
	
	void set(@NotNull Object obj) throws InvalidSetException;
}
