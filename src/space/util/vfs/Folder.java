package space.util.vfs;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.vfs.exception.UnsupportedEntryException;

import java.io.IOException;
import java.nio.file.OpenOption;
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
	 * creates a new {@link File} <br>
	 * <br>
	 * <b>Allowed</b> Flags: <br>
	 * <ul>
	 * <li>{@link java.nio.file.StandardOpenOption#CREATE} to create a new file, overriding any existing one </li>
	 * </ul>
	 *
	 * @param name the name of the {@link File}
	 * @param flag any flags
	 * @return the new {@link File} object
	 * @throws IOException when an IOError occurred
	 */
	File addFile(String name, OpenOption... flag) throws IOException;
	
	/**
	 * creates a new {@link Folder} <br>
	 * <br>
	 * <b>Allowed</b> Flags: <br>
	 * <ul>
	 * <li>{@link java.nio.file.StandardOpenOption#CREATE} to create a new file, overriding any existing one </li>
	 * </ul>
	 *
	 * @param name the name of the {@link Folder}
	 * @param flag any flags
	 * @return the new {@link Folder} object or null if creation failed
	 * @throws IOException when an IOError occurred
	 */
	Folder addFolder(String name, OpenOption... flag) throws IOException;
	
	/**
	 * creates a new {@link Link} <br>
	 * <br>
	 * <b>Allowed</b> Flags: <br>
	 * <ul>
	 * <li>{@link java.nio.file.StandardOpenOption#CREATE} to create a new file, overriding any existing one </li>
	 * </ul>
	 *
	 * @param name    the name of the {@link Link}
	 * @param pointer an Entry to link to
	 * @param flag    any flags
	 * @return the new {@link Link} object or null if creation failed
	 * @throws IOException                   when an IOError occurred
	 * @throws UnsupportedEntryException     if the Entry pointer is not supported
	 * @throws UnsupportedOperationException if Links are not supported
	 */
	Link addLink(String name, Entry pointer, OpenOption... flag) throws IOException, UnsupportedOperationException;
	
	//delete
	void delete(String name) throws IOException;
	
	@Override
	default <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier(name(), getEntries().values());
	}
}
