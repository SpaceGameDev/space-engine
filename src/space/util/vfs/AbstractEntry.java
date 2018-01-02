package space.util.vfs;

import java.util.Objects;

public class AbstractEntry implements Entry {
	
	protected String name;
	protected Folder parent;
	
	public AbstractEntry(String name) {
		this.name = name;
	}
	
	protected AbstractEntry(String name, Folder parent) {
		this.name = name;
		this.parent = parent;
	}
	
	//name
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public void rename(String newName) {
		this.name = newName;
	}
	
	//parent
	@Override
	public void setParent(Folder parent) {
		if (name.length() == 0)
			throw new IllegalStateException("Folder is root directory!");
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
	
	@Override
	public String toString() {
		return toString0();
	}
}
