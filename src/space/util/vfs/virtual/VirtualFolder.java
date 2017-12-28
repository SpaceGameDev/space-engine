package space.util.vfs.virtual;

import space.util.delegate.collection.SetCast;
import space.util.vfs.AbstractEntry;
import space.util.vfs.Entry;
import space.util.vfs.File;
import space.util.vfs.Folder;
import space.util.vfs.Link;
import space.util.vfs.exception.UnsupportedEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VirtualFolder extends AbstractEntry implements Folder {
	
	protected Map<String, Entry> list = new HashMap<>();
	
	public VirtualFolder(String name) {
		super(name);
	}
	
	@Override
	public Set<Entry> getEntries() {
		return new SetCast<>(list.values());
	}
	
	@Override
	public File addFile(String name) {
		VirtualFile file = new VirtualFile(name);
		file.setParent(this);
		return file;
	}
	
	@Override
	public Folder addFolder(String name) {
		VirtualFolder file = new VirtualFolder(name);
		file.setParent(this);
		return file;
	}
	
	@Override
	public Link addLink(String name, Entry pointer) throws UnsupportedEntry {
		VirtualLink file = VirtualLink.create(name, pointer);
		file.setParent(this);
		return file;
	}
	
	@Override
	public void delete(String name) {
		list.remove(name);
	}
}
