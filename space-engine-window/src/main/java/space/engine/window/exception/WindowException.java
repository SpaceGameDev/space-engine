package space.engine.window.exception;

import space.util.string.builder.CharBufferBuilder1D;

public class WindowException extends RuntimeException {
	
	public static final String UNKNOWN_ERROR_ID = "UNKNOWN_ERROR_ID";
	
	public WindowException() {
	}
	
	public WindowException(String message) {
		super(message);
	}
	
	public WindowException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public WindowException(Throwable cause) {
		super(cause);
	}
	
	public WindowException(int errorId, String errorName, String desc) {
		//@formatter:off
		super(new CharBufferBuilder1D<>()
				.append(errorName != null ? errorName : UNKNOWN_ERROR_ID)
				.append("[0x").append(Integer.toHexString(errorId)).append("]: ")
				.append(desc).toString());
		//@formatter:on
	}
}
