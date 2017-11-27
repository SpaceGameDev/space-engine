package space.util.gui;

public interface GuiElement<ELEMENT extends GuiElement<ELEMENT, CREATOR>, CREATOR extends GuiCreator<ELEMENT>> {
	
	GuiApi<ELEMENT> getApi();
	
	CREATOR getCreator();
	
	ELEMENT toBaseElement();
}
