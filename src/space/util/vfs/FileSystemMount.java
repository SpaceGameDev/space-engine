package space.util.vfs;

import spaceOld.util.*;

public abstract class FileSystemMount extends Thing {
	
	public String location;
	public String splitRegex;
	
	public FileSystemMount(String name, String location, String splitRegex) {
		super(name);
		this.location = location.length() == 0 ? "" : location.endsWith(PathUtils.fileSeparator) ? location : location + splitRegex;
		this.splitRegex = splitRegex;
	}
	
	public FileSystemMount(String name, String location) {
		this(name, location, "/");
	}
	
	private void compress(StringBuilder b, int index, String[] path) {
		for (int i = index; i < path.length; i++) {
			b.append(path[i]);
			b.append(splitRegex);
		}
		b.setLength(b.length() - splitRegex.length());
	}
	
	protected String makeAddress(int index, String[] path) {
		StringBuilder b = new StringBuilder();
		b.append(location);
		compress(b, index, path);
		return b.toString();
	}
}
