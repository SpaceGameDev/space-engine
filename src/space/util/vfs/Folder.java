package space.util.vfs;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;

import java.io.IOException;
import java.util.Map;

public interface Folder extends Entry, ToString {
	
	//getEntries
	/**
	 * gets all {@link Entry}s of the {@link Folder}
	 *
	 * @return a collection with all {@link Entry}s of this {@link Folder}
	 */
	Map<String, Entry> getEntries();
	
	//add
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
	Link addLink(String name, Entry pointer) throws IOException, UnsupportedOperationException;
	
	//delete
	void delete(String name) throws IOException;
	
	//utility
	static void add(Map<String, Entry> map, Entry ent) {
		Entry old = map.put(ent.name(), ent);
		if (old != null)
			old.setParent(null);
	}
	
	@Override
	default <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier(name(), getEntries().values());
	}
}
