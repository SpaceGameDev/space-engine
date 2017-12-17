package space.util.vfs.virtual;

import space.util.delegate.collection.SetCast;
import space.util.vfs.AbstractEntry;
import space.util.vfs.interfaces.Entry;
import space.util.vfs.interfaces.Folder;

import java.io.IOException;
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
	public void add(Entry entry) throws IOException {
		list.put(entry.name(), entry);
		//no hard links, so this "works"
		entry.setParent(this);
	}
	
	@Override
	public void delete(String name) throws IOException {
		list.remove(name);
	}
}
