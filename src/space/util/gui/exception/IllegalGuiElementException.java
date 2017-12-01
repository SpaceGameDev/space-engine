package space.util.gui.exception;

import space.util.gui.GuiElement;

/**
 * thrown if some Gui assembler submits {@link GuiElement}s from the wrong type or implementation to a {@link GuiElement}
 */
public class IllegalGuiElementException extends RuntimeException {
	
	public IllegalGuiElementException() {
	}
	
	public IllegalGuiElementException(String message) {
		super(message);
	}
	
	public IllegalGuiElementException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public IllegalGuiElementException(Throwable cause) {
		super(cause);
	}
	
	public IllegalGuiElementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public IllegalGuiElementException(GuiElement<?> value) {
		this(value.getClass());
	}
	
	public IllegalGuiElementException(Class<?> clazz) {
		this("Illegal GuiElement type " + clazz.getName());
	}
}
