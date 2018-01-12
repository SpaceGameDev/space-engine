package space.util.vfs;

import space.util.baseobject.ToString;
import space.util.string.builder.CharBufferBuilder1D;
import space.util.string.toStringHelper.ToStringHelper;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.OpenOption;
import java.util.Arrays;

public interface Entry extends ToString {
	
	//name and parent
	
	/**
	 * @return the name of this entry
	 */
	String name();
	
	/**
	 * @return the parent of this file, or null if it is the root
	 */
	Folder getParent();
	
	/**
	 * Renames and/or moves the entry. <br>
	 * <br>
	 * <b>Allowed</b> Flags: <br>
	 * <ul>
	 * <li>{@link java.nio.file.StandardCopyOption#REPLACE_EXISTING} to force an override of any existing file </li>
	 * </ul>
	 *
	 * @param newName   the new name, may be null
	 * @param newParent the new parent folder, may be null
	 * @throws IOException if an IOError occurs
	 */
	Entry move(String newName, Folder newParent, CopyOption... options) throws IOException;
	
	/**
	 * Copies the entry. <br>
	 * Folders will be copied empty on default. <br>
	 * <br>
	 * <b>Allowed</b> Flags: <br>
	 * <ul>
	 * <li>{@link java.nio.file.StandardCopyOption#REPLACE_EXISTING} to force an override of any existing file </li>
	 * </ul>
	 *
	 * @param newName   the new name, may be null
	 * @param newParent the new parent folder, may be null
	 * @throws IOException if an IOError occurs
	 */
	Entry copy(String newName, Folder newParent, CopyOption... options) throws IOException;
	
	void onDelete(Folder folder);
	
	//path
	
	/**
	 * @return the path to this file, in a POSIX-like way
	 */
	default String getPath() {
		CharBufferBuilder1D<?> b = new CharBufferBuilder1D<>();
		for (String s : getPathArray())
			b.append(s).append('/');
		b.addLength(-1);
		return b.toString();
	}
	
	/**
	 * @return the path to this file, separated in an array
	 */
	default String[] getPathArray() {
		return resolvePathOfFile(this, 1);
	}
	
	/**
	 * internal method used to resolve the file path recursively
	 */
	static String[] resolvePathOfFile(Entry entry, int depth) {
		Folder parent = entry.getParent();
		
		if (parent == null) {
			String[] str = new String[depth];
			str[0] = entry.name();
			return str;
		} else {
			String[] str = resolvePathOfFile(parent, depth + 1);
			str[str.length - depth] = entry.name();
			return str;
		}
	}
	
	@Override
	default <T> T toTSH(ToStringHelper<T> api) {
		return api.toString("?" + name());
	}
	
	/**
	 * little utility function for checking {@link OpenOption OpenOptions}
	 */
	static boolean containsOption(OpenOption[] options, OpenOption test) {
		return Arrays.binarySearch(options, test) != -1;
	}
	
	/**
	 * little utility function for checking {@link CopyOption CopyOptions}
	 */
	static boolean containsOption(CopyOption[] options, CopyOption test) {
		return Arrays.binarySearch(options, test) != -1;
	}
}
