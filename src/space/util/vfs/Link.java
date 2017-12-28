package space.util.vfs;

public interface Link extends Entry {
	
	/**
	 * gets the pointer to the linked file
	 *
	 * @return the pointer to the linked file
	 */
	Entry getPointer();
}
