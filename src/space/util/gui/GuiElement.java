package space.util.gui;

public interface GuiElement {
	
	GuiApi<?> getApi();
	
	GuiCreator<?> getCreator();
}
