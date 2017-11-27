package space.util.gui;

public interface GuiElement<BASE extends GuiElement<BASE, CREATOR>, CREATOR extends GuiCreator<BASE>> {
	
	GuiApi<?> getApi();
	
	CREATOR getCreator();
	
	BASE toBaseElement();
}
