package space.util.vfs;

import space.util.baseobject.ToString;
import space.util.string.builder.CharBufferBuilder1D;
import space.util.string.toStringHelper.ToStringHelper;

import java.io.IOException;

public interface Entry extends ToString {
	
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
	void setParent(Folder parent);
	
	/**
	 * @return the parent of this file, or null if it is the root
	 */
	Folder getParent();
	
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
		return resolvePathOfFile(1);
	}
	
	default String[] resolvePathOfFile(int depth) {
		Folder parent = getParent();
		
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
	
	@Override
	default <T> T toTSH(ToStringHelper<T> api) {
		return api.toString("?" + name());
	}
}
