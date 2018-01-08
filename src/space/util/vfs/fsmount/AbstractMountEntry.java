package space.util.vfs.fsmount;

import space.util.vfs.AbstractEntry;
import space.util.vfs.Folder;

import java.io.File;
import java.io.IOException;

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
	
	@Override
	public void rename(String newName) throws IOException {
		File newPath = new File(path.getParent() + File.separatorChar + newName);
		if (!path.renameTo(newPath))
			throw new IOException("unable to rename file '" + path.toString() + "' to '" + newPath + "'");
		path = newPath;
		super.rename(newName);
	}
	
	@Override
	public void setParent(Folder parent) {
		super.setParent(parent);
	}
}
