package space.util.gui.exception;

/**
 * thrown if a {@link space.util.gui.GuiElement} is not supported by a specifiic {@link space.util.gui.GuiApi}
 */
public class GuiElementUnsupportedException extends RuntimeException {
	
	public GuiElementUnsupportedException() {
	}
	
	public GuiElementUnsupportedException(String message) {
		super(message);
	}
	
	public GuiElementUnsupportedException(Throwable cause) {
		super(cause);
	}
	
	public GuiElementUnsupportedException(String message, Throwable cause) {
		super(message, cause);
	}
}
