package space.util.gui;

public interface GuiApi<BASEELEMENT> {
	
	<ELEMENT extends GuiElement<?>, RETURN extends ELEMENT> RETURN create(Class<ELEMENT> type);
	
	<ELEMENT extends GuiElement<?>> boolean isSupported(Class<ELEMENT> type);
	
	Class<? extends BASEELEMENT> getBaseElementClass();
}
