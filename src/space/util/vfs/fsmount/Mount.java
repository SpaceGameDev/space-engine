package space.util.vfs.fsmount;

import space.util.vfs.AbstractEntry;
import space.util.vfs.Entry;
import space.util.vfs.File;
import space.util.vfs.Folder;
import space.util.vfs.Link;

import java.util.Set;

public class Mount extends AbstractEntry implements Folder {
	
	public java.io.File path;
	
	public Mount(String name, String path) {
		this(name, new java.io.File(path));
	}
	
	public Mount(String name, java.io.File path) {
		super(name);
		this.path = path;
	}
	
	@Override
	public Set<Entry> getEntries() {
		String[] names = path.list();
		return null;
	}
	
	@Override
	public File addFile(String name) {
		return null;
	}
	
	@Override
	public Folder addFolder(String name) {
		return null;
	}
	
	@Override
	public Link addLink(String name, Entry pointer) {
		return null;
	}
	
	@Override
	public void delete(String name) {
	
	}
}
