package space.util.gui.tsh.elements;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.string.CharSequence2D;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperTable;

@FunctionalInterface
public interface GuiToStringHelperMapperCreator extends GuiCreator {
	
	GuiToStringHelperMapper create(String name, CharSequence2D separator, boolean align);
	
	interface GuiToStringHelperMapper extends GuiElement, ToStringHelperTable<GuiElement> {
		
		@Override
		GuiElement put(int[] pos, GuiElement object);
		
		@Override
		default GuiElement build() {
			return this;
		}
	}
}
