package space.util.gui;

public interface GuiApi<BASEELEMENT extends GuiElement<BASEELEMENT, ?>> {
	
	<ELEMENT extends GuiElement<ELEMENT, CREATOR>, CREATOR extends GuiCreator<ELEMENT>> CREATOR create(Class<ELEMENT> type);
	
	<ELEMENT extends GuiElement<ELEMENT, ?>> boolean isSupported(Class<ELEMENT> type);
	
	Class<? extends BASEELEMENT> getBaseElementClass();
}
