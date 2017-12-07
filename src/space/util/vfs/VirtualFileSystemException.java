package space.util.vfs;

import spaceOld.engine.SpaceException;

public class VirtualFileSystemException extends SpaceException {
	
	public VirtualFileSystemException() {
		super();
	}
	
	public VirtualFileSystemException(String message) {
		super(message);
	}
	
	public VirtualFileSystemException(Throwable cause) {
		super(cause);
	}
	
	public VirtualFileSystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
