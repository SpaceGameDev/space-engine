package space.util.gui;

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
