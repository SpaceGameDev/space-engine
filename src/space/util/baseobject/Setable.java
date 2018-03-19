package space.util.baseobject;

import space.util.baseobject.exceptions.InvalidSetException;

public interface Setable {
	
	void set(Object obj) throws InvalidSetException;
}
