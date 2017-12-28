package space.util.vfs;

import java.io.IOException;
import java.util.Set;

public interface Folder extends Entry {
	
	/**
	 * gets all {@link Entry}s of the {@link Folder}
	 *
	 * @return a collection with all {@link Entry}s of this {@link Folder}
	 */
	Set<Entry> getEntries();
	
	/**
	 * creates a new {@link File}
	 *
	 * @param name the name of the {@link File}
	 * @return the new {@link File} object
	 * @throws IOException when an IOError occurred
	 */
	File addFile(String name) throws IOException;
	
	/**
	 * creates a new {@link Folder}
	 *
	 * @param name the name of the {@link Folder}
	 * @return the new {@link Folder} object or null if creation failed
	 * @throws IOException when an IOError occurred
	 */
	Folder addFolder(String name) throws IOException;
	
	/**
	 * creates a new {@link Link}
	 *
	 * @param name the name of the {@link Link}
	 * @return the new {@link Link} object or null if creation failed
	 * @throws IOException when an IOError occurred
	 */
	Link addLink(String name, Entry pointer) throws IOException;
	
	//delete
	void delete(String name) throws IOException;
}
