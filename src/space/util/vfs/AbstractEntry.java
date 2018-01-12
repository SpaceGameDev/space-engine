package space.util.vfs;

import java.nio.file.CopyOption;
import java.util.Objects;

public abstract class AbstractEntry implements Entry {
	
	protected String name;
	protected Folder parent;
	
	public AbstractEntry(String name) {
		this.name = name;
	}
	
	protected AbstractEntry(String name, Folder parent) {
		this.name = name;
		this.parent = parent;
	}
	
	//name and parent
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public Entry move(String newName, Folder newParent, CopyOption... options) {
		if (newName != null)
			name = newName;
		if (newParent != null)
			parent = newParent;
		return this;
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
	
	@Override
	public String toString() {
		return toString0();
	}
}
