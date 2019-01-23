package space.util.baseobject;

import org.jetbrains.annotations.NotNull;
import space.util.baseobject.exceptions.InvalidSetException;

public interface Setable {
	
	void set(@NotNull Object obj) throws InvalidSetException;
}
