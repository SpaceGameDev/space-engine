package space.util.vfs.exception;

import space.util.vfs.Entry;
import space.util.vfs.Folder;

import java.io.IOException;

/**
 * thrown if any Filesystem not supported.
 * Usually the case if something requires an Entry, and that Entry is from an unsupported Filesystem.
 */
public class UnsupportedFileSystem extends IOException {
	
	public UnsupportedFileSystem() {
	}
	
	public UnsupportedFileSystem(String message) {
		super(message);
	}
	
	public UnsupportedFileSystem(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UnsupportedFileSystem(Throwable cause) {
		super(cause);
	}
	
	public UnsupportedFileSystem(Folder folder, Entry entry) {
		this("Entry of type " + entry.getClass().getName() + " si not allowed to be added to " + folder + ": " + entry);
	}
}
