package space.util.keygen;

@FunctionalInterface
@SuppressWarnings("unused")
public interface IKey<T> {
	
	/**
	 * gets the id of the key. It can be expected that {@link IKey} ids are assigned sequentially.
	 */
	int getID();
	
	/**
	 * gets the default value of this key. If not overridden, it will return null.
	 */
	default T getDefaultValue() {
		return null;
	}
}
