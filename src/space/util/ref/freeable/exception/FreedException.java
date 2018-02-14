package space.util.ref.freeable.exception;

import space.util.ref.freeable.IFreeableReference;
import space.util.string.builder.CharBufferBuilder2D;

/**
 * thrown if some data was requested but the {@link IFreeableReference} was already released
 */
public class FreedException extends RuntimeException {
	
	public IFreeableReference ref;
	
	public FreedException(IFreeableReference ref) {
		super(new CharBufferBuilder2D<>().append("Reference already released: ").append(ref).toString());
		this.ref = ref;
	}
	
	public FreedException(String message, IFreeableReference ref) {
		super(message);
		this.ref = ref;
	}
	
	public FreedException(String message, Throwable cause, IFreeableReference ref) {
		super(message, cause);
		this.ref = ref;
	}
	
	public FreedException(Throwable cause, IFreeableReference ref) {
		super(cause);
		this.ref = ref;
	}
	
	public FreedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, IFreeableReference ref) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.ref = ref;
	}
}
