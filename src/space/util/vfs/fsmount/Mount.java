package space.util.vfs.fsmount;

import space.util.vfs.AbstractEntry;
import space.util.vfs.interfaces.Entry;
import space.util.vfs.interfaces.Folder;

import java.io.IOException;
import java.util.Set;

public class Mount extends AbstractEntry implements Folder {
	
	@Override
	public Set<Entry> getEntries() {
		return null;
	}
	
	@Override
	public void add(Entry entry) throws IOException {
	
	}
	
	@Override
	public void delete(String name) throws IOException {
	
	}
}
