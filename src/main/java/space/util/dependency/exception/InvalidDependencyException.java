package space.util.dependency.exception;

import space.util.dependency.Dependency;

/**
 * Thrown if an Dependency is invalid for any reason.
 */
public class InvalidDependencyException extends RuntimeException {
	
	public InvalidDependencyException() {
	}
	
	public InvalidDependencyException(String message) {
		super(message);
	}
	
	public InvalidDependencyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidDependencyException(Throwable cause) {
		super(cause);
	}
	
	public InvalidDependencyException(Dependency dep) {
		this("Invalid Dependency: " + dep.toString());
	}
}
