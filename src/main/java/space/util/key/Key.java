package space.util.key;

import org.jetbrains.annotations.Nullable;

@FunctionalInterface
@SuppressWarnings("unused")
public interface Key<T> {
	
	/**
	 * gets the id of the key. It can be expected that {@link Key} ids are assigned sequentially.
	 */
	int getID();
	
	/**
	 * gets the default value of this key. If not overridden, it will return null.
	 */
	@Nullable
	default T getDefaultValue() {
		return null;
	}
}
