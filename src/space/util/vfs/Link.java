package space.util.vfs;

import space.util.string.toStringHelper.ToStringHelper;

public interface Link extends Entry {
	
	/**
	 * gets the pointer to the linked file
	 *
	 * @return the pointer to the linked file
	 */
	Entry getPointer();
	
	@Override
	default <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier(name() + " ->", getPointer().getPath());
	}
}
