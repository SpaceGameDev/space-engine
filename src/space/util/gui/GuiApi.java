package space.util.gui;

public interface GuiApi<BASE extends GuiElement<BASE, ?>> {
	
	<CREATOR extends GuiCreator<? extends BASE>> CREATOR get(Class<CREATOR> type);
	
	<CREATOR extends GuiCreator<? extends BASE>> boolean isSupported(Class<CREATOR> type);
	
	Class<? extends BASE> getBaseElementClass();
}
