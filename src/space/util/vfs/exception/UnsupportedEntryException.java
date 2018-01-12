package space.util.vfs.exception;

import space.util.vfs.Entry;

import java.io.IOException;

/**
 * thrown if any {@link space.util.vfs.Entry} is not supported.
 */
public class UnsupportedEntryException extends IOException {
	
	public UnsupportedEntryException() {
	}
	
	public UnsupportedEntryException(String message) {
		super(message);
	}
	
	public UnsupportedEntryException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UnsupportedEntryException(Throwable cause) {
		super(cause);
	}
	
	public UnsupportedEntryException(Entry entry) {
		super("Entry of type " + entry.getClass().getName() + " unsupported: " + entry);
	}
}
