package space.util.gui.elements.direction;

@FunctionalInterface
public interface GuiRowCreator extends GuiDirectionalCreator {
	
	@Override
	GuiRow create();
	
	interface GuiRow extends GuiDirectional {
	
	}
}
