package space.util.gui.elements.tsh;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperTable;

@FunctionalInterface
public interface GuiToStringHelperTableCreator<T> extends GuiCreator {
	
	GuiToStringHelperTable create(String name, int dimensions);
	
	interface GuiToStringHelperTable extends GuiElement, ToStringHelperTable<GuiElement> {
		
		@Override
		GuiElement put(int[] pos, GuiElement object);
		
		@Override
		default GuiElement build() {
			return this;
		}
	}
}
