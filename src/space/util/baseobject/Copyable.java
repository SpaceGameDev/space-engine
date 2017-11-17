package space.util.baseobject;

import static space.util.GetClass.gClass;

/**
 * the {@link Copyable#copy(Object)}-Methods should create a copy of the object with the same values in the fields
 * looping references will cause {@link StackOverflowError}
 */
public interface Copyable {
	
	@SuppressWarnings("unchecked")
	static <OBJ> OBJ copy(OBJ object) {
		if (object instanceof BaseObject)
			return copy((OBJ & BaseObject) object);
	}
	
	@SuppressWarnings("unchecked")
	static <OBJ extends BaseObject> OBJ copy(OBJ object) {
		OBJ n = Makeable.make(gClass(object));
		n.set(object);
		return n;
	}
}
