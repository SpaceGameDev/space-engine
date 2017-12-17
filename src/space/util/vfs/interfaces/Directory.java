package space.util.vfs.interfaces;

import java.io.IOException;
import java.util.Set;

public interface Directory extends Entry {
	
	/**
	 * gets all {@link Entry}s of the {@link Directory}
	 *
	 * @return a collection with all {@link Entry}s of this {@link Directory}
	 */
	Set<Entry> getEntries();
	
	/**
	 * creates a new {@link Entry}
	 *
	 * @param entry the {@link Entry}
	 * @throws IOException when an IOError occurred
	 */
	void add(Entry entry) throws IOException;
	
	//delete
	void delete(String name) throws IOException;
}
