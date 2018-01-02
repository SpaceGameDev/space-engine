package space.util.vfs.fsmount;

import space.util.vfs.AbstractEntry;
import space.util.vfs.Folder;

import java.io.File;

public class AbstractMountEntry extends AbstractEntry {
	
	protected java.io.File path;
	
	public AbstractMountEntry(File path) {
		super(path.getName());
		this.path = path;
	}
	
	protected AbstractMountEntry(File path, Folder parent) {
		super(path.getName(), parent);
		this.path = path;
	}
}
