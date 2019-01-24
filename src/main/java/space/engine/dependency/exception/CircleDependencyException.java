package space.engine.dependency.exception;

import space.engine.dependency.Dependency;

/**
 * thrown if Dependencies form a circle, which would be causing something like an infinite loop with no entry
 */
public class CircleDependencyException extends InvalidDependencyException {
	
	public CircleDependencyException() {
	}
	
	public CircleDependencyException(String message) {
		super(message);
	}
	
	public CircleDependencyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CircleDependencyException(Throwable cause) {
		super(cause);
	}
	
	public CircleDependencyException(Dependency dep1, Dependency dep2) {
		this("Dependency Circle of " + dep1 + " and " + dep2);
	}
}
