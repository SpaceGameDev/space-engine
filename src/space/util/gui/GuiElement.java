package space.util.gui;

public interface GuiElement<BASEELEMENT extends GuiElement<BASEELEMENT>> {
	
	GuiApi<BASEELEMENT> getApi();
}
