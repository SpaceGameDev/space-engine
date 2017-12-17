package space.util.vfs.virtual;

import space.util.vfs.AbstractEntry;
import space.util.vfs.interfaces.Link;

public class VirtualLink extends AbstractEntry implements Link {
	
	protected String[] pointer;
	
	public VirtualLink(String name, String[] pointer) {
		super(name);
		this.pointer = pointer;
	}
	
	@Override
	public String[] getPointer() {
		return pointer;
	}
}
