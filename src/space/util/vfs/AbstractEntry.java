package space.util.vfs;

import space.util.vfs.interfaces.Entry;
import space.util.vfs.interfaces.Folder;

import java.util.Objects;

public class AbstractEntry implements Entry {
	
	protected String name;
	protected Folder parent;
	
	public AbstractEntry(String name) {
		this.name = name;
	}
	
	//name
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public void rename(String newName) {
		this.name = name;
	}
	
	//parent
	@Override
	public void setParent(Folder parent) {
		this.parent = parent;
	}
	
	@Override
	public Folder getParent() {
		return parent;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AbstractEntry))
			return false;
		
		AbstractEntry that = (AbstractEntry) o;
		return Objects.equals(name, that.name) && Objects.equals(parent, that.parent);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
