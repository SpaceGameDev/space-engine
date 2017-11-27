package space.util.gui.elements.direction;

import space.util.gui.GuiElement;

@FunctionalInterface
public interface GuiRowCreator<BASE extends GuiElement<BASE, ?>> extends GuiDirectionalCreator<BASE> {
	
	@Override
	GuiRow create();
	
	interface GuiRow<BASE extends GuiElement<BASE, CREATOR>, CREATOR extends GuiRowCreator<BASE>> extends GuiDirectional<BASE, CREATOR> {
	
	}
}
