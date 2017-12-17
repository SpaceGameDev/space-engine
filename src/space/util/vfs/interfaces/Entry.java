package space.util.vfs.interfaces;

import java.io.IOException;

public interface Entry {
	
	//name
	
	/**
	 * @return the name of this entry
	 */
	String name();
	
	/**
	 * renames the entry
	 *
	 * @param newName the new name
	 */
	void rename(String newName) throws IOException;
	
	//parent
	
	/**
	 * @param parent the new parent of this file
	 */
	void setParent(Directory parent);
	
	/**
	 * @return the parent of this file, or null if it is the root
	 */
	Directory getParent();
	
	//path
	
	/**
	 * @return the path to this file, in a POSIX-like way
	 */
	default String[] getPath() {
		return resolvePathOfFile(1);
	}
	
	default String[] resolvePathOfFile(int depth) {
		Directory parent = getParent();
		
		if (parent == null) {
			String[] str = new String[depth];
			str[0] = name();
			return str;
		} else {
			String[] str = parent.resolvePathOfFile(depth + 1);
			str[str.length - depth] = name();
			return str;
		}
	}
}
