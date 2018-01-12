package space.util.vfs.virtual;

import space.util.FlagUtil;
import space.util.vfs.AbstractEntry;
import space.util.vfs.Entry;
import space.util.vfs.File;
import space.util.vfs.Folder;
import space.util.vfs.Link;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
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
	
	public VirtualFolder(String name, Folder parent) {
		super(name, parent);
	}
	
	@Override
	public Map<String, Entry> getEntries() {
		return map;
	}
	
	@Override
	public synchronized File addFile(String name, OpenOption... flag) throws IOException {
		if (!FlagUtil.hasFlag(flag, StandardOpenOption.CREATE) && map.containsKey(name))
			throw new IOException("File already exists!");
		
		VirtualFile file = new VirtualFile(name, this);
		add(map, file);
		return file;
	}
	
	@Override
	public synchronized Folder addFolder(String name, OpenOption... flag) throws IOException {
		if (!FlagUtil.hasFlag(flag, StandardOpenOption.CREATE) && map.containsKey(name))
			throw new IOException("File already exists!");
		
		VirtualFolder file = new VirtualFolder(name, this);
		add(map, file);
		return file;
	}
	
	@Override
	public synchronized Link addLink(String name, Entry pointer, OpenOption... flag) throws IOException {
		if (!FlagUtil.hasFlag(flag, StandardOpenOption.CREATE) && map.containsKey(name))
			throw new IOException("File already exists!");
		
		VirtualLink file = VirtualLink.create(name, this, pointer);
		add(map, file);
		return file;
	}
	
	void add(Map<String, Entry> map, Entry ent) {
		Entry old = map.put(ent.name(), ent);
		if (old != null)
			old.onDelete(this);
	}
	
	@Override
	public Entry copy(String newName, Folder newParent, CopyOption... options) {
		return null;
	}
	
	@Override
	public void onDelete(Folder folder) {
	
	}
	
	@Override
	public synchronized void delete(String name) {
		map.remove(name);
	}
}
