package space.util.gui.simple;

import space.util.gui.GuiApi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleGuiElement {
	
	Class<? extends GuiApi> api();
	
	int type();
}
