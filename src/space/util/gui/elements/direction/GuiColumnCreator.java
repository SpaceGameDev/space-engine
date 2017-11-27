package space.util.gui.elements.direction;

import space.util.gui.GuiElement;

@FunctionalInterface
public interface GuiColumnCreator<BASE extends GuiElement<BASE, ?>> extends GuiDirectionalCreator<BASE> {
	
	@Override
	GuiColumn create();
	
	interface GuiColumn<BASE extends GuiElement<BASE, CREATOR>, CREATOR extends GuiColumnCreator<BASE>> extends GuiDirectional<BASE, CREATOR> {
	
	}
}
