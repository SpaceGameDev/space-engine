package space.util.gui.elements.tsh;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperTable;

@FunctionalInterface
public interface GuiToStringHelperMapperCreator<T> extends GuiCreator {
	
	GuiToStringHelperMapper create(String name, String separator, boolean align);
	
	interface GuiToStringHelperMapper extends GuiElement, ToStringHelperTable<GuiElement> {
		
		@Override
		GuiElement put(int[] pos, GuiElement object);
		
		@Override
		default GuiElement build() {
			return this;
		}
	}
}
