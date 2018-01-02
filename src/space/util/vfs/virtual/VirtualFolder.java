package space.util.vfs.virtual;

import space.util.vfs.AbstractEntry;
import space.util.vfs.Entry;
import space.util.vfs.File;
import space.util.vfs.Folder;
import space.util.vfs.Link;
import space.util.vfs.exception.UnsupportedEntry;

import java.util.HashMap;
import java.util.Map;

public class VirtualFolder extends AbstractEntry implements Folder {
	
	protected Map<String, Entry> map = new HashMap<>();
	
	/**
	 * creates a root folder
	 */
	public VirtualFolder() {
		this("");
	}
	
	public VirtualFolder(String name) {
		super(name);
	}
	
	@Override
	public Map<String, Entry> getEntries() {
		return map;
	}
	
	@Override
	public File addFile(String name) {
		VirtualFile file = new VirtualFile(name);
		file.setParent(this);
		Folder.add(map, file);
		return file;
	}
	
	@Override
	public Folder addFolder(String name) {
		VirtualFolder file = new VirtualFolder(name);
		file.setParent(this);
		Folder.add(map, file);
		return file;
	}
	
	@Override
	public Link addLink(String name, Entry pointer) throws UnsupportedEntry {
		VirtualLink file = VirtualLink.create(name, pointer);
		file.setParent(this);
		Folder.add(map, file);
		return file;
	}
	
	@Override
	public void delete(String name) {
		map.remove(name);
	}
}
