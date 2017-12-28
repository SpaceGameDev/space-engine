package space.util.vfs.exception;

import space.util.vfs.Entry;

import java.io.IOException;

/**
 * thrown if any {@link space.util.vfs.Entry} is not supported.
 */
public class UnsupportedEntry extends IOException {
	
	public UnsupportedEntry() {
	}
	
	public UnsupportedEntry(String message) {
		super(message);
	}
	
	public UnsupportedEntry(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UnsupportedEntry(Throwable cause) {
		super(cause);
	}
	
	public UnsupportedEntry(Entry entry) {
		super("Entry of type " + entry.getClass().getName() + " unsupported: " + entry);
	}
}
