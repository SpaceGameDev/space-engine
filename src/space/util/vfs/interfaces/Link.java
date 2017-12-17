package space.util.vfs.interfaces;

public interface Link extends Entry {
	
	/**
	 * gets the pointer to the linked file
	 *
	 * @return the pointer to the linked file
	 */
	String[] getPointer();
}
