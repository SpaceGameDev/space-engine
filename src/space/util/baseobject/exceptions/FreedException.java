package space.util.baseobject.exceptions;

import space.util.baseobject.additional.Freeable;
import space.util.string.builder.CharBufferBuilder2D;

/**
 * thrown if some data was requested but the {@link Freeable} was already released
 */
public class FreedException extends RuntimeException {
	
	public Freeable ref;
	
	public FreedException(Freeable ref) {
		super(new CharBufferBuilder2D<>().append("Reference already released: ").append(ref).toString());
		this.ref = ref;
	}
	
	public FreedException(String message, Freeable ref) {
		super(message);
		this.ref = ref;
	}
	
	public FreedException(String message, Throwable cause, Freeable ref) {
		super(message, cause);
		this.ref = ref;
	}
	
	public FreedException(Throwable cause, Freeable ref) {
		super(cause);
		this.ref = ref;
	}
	
	public FreedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Freeable ref) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.ref = ref;
	}
}
