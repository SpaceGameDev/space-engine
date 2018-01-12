package space.util.vfs.exception;

import space.util.vfs.Entry;
import space.util.vfs.Folder;

import java.io.IOException;

/**
 * thrown if any Filesystem not supported.
 * Usually the case if something requires an Entry, and that Entry is from an unsupported Filesystem.
 */
public class UnsupportedFileSystemException extends IOException {
	
	public UnsupportedFileSystemException() {
	}
	
	public UnsupportedFileSystemException(String message) {
		super(message);
	}
	
	public UnsupportedFileSystemException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UnsupportedFileSystemException(Throwable cause) {
		super(cause);
	}
	
	public UnsupportedFileSystemException(Folder folder, Entry entry) {
		this("Entry of type " + entry.getClass().getName() + " si not allowed to be added to " + folder + ": " + entry);
	}
}
