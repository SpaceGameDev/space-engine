package space.util.vfs.fsmount;

import space.util.vfs.Entry;
import space.util.vfs.File;
import space.util.vfs.Folder;
import space.util.vfs.Link;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MountFolder extends AbstractMountEntry implements Folder {
	
	private Map<String, Entry> entries;
	
	public MountFolder(java.io.File path) {
		super(path);
	}
	
	protected MountFolder(java.io.File path, Folder parent) {
		super(path, parent);
	}
	
	@Override
	public synchronized Map<String, Entry> getEntries() {
		java.io.File[] names = path.listFiles();
		if (names == null)
			return new HashMap<>(0);
		
		Map<String, Entry> ret = new HashMap<>();
		for (java.io.File file : names) {
			if (file.isDirectory())
				Folder.add(ret, new MountFolder(file, this));
			else if (file.isFile())
				Folder.add(ret, new MountFile(file, this));
		}
		
		entries = ret;
		return ret;
	}
	
	@Override
	public synchronized File addFile(String name) throws IOException {
		java.io.File file = new java.io.File(path.getPath() + java.io.File.separatorChar + name);
		if (!file.createNewFile())
			throw new IOException("File can not be created: " + file.getAbsolutePath());
		
		entries = null;
		return new MountFile(file, this);
	}
	
	@Override
	public Folder addFolder(String name) throws IOException {
		java.io.File file = new java.io.File(path.getPath() + java.io.File.separatorChar + name);
		if (!file.mkdir())
			throw new IOException("Folder can not be created: " + file.getAbsolutePath());
		
		entries = null;
		return new MountFolder(file, this);
	}
	
	@Override
	public Link addLink(String name, Entry pointer) {
		return null;
	}
	
	@Override
	public void delete(String name) {
	
	}
}
