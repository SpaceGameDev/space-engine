package space.util.vfs.virtual;

import space.util.delegate.collection.SetCast;
import space.util.vfs.AbstractEntry;
import space.util.vfs.interfaces.Directory;
import space.util.vfs.interfaces.Entry;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VirtualDirectory extends AbstractEntry implements Directory {
	
	protected Map<String, Entry> list = new HashMap<>();
	
	public VirtualDirectory(String name) {
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
