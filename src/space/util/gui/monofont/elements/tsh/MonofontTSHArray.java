package space.util.gui.monofont.elements.tsh;

import space.util.gui.GuiCreator;
import space.util.gui.elements.tsh.GuiToStringHelperArrayCreator;
import space.util.gui.elements.tsh.GuiToStringHelperArrayCreator.ToStringHelperArray;
import space.util.gui.monofont.elements.direction.MonofontElementList;
import space.util.gui.monofont.tsh.arrayCreator.MonofontArrayCreator;
import space.util.gui.monofont.tsh.arrayCreator.MonofontArrayCreatorImpl;
import space.util.string.CharSequence2D;

import java.util.function.Supplier;

public class MonofontTSHArray extends MonofontElementList implements ToStringHelperArray {
	
	public static final GuiToStringHelperArrayCreator CREATOR = MonofontTSHArray::new;
	public static Supplier<MonofontArrayCreator> DEFAULT = () -> MonofontArrayCreatorImpl.INSTANCE;
	
	public Class<?> type;
	public MonofontArrayCreator style = DEFAULT.get();
	
	public MonofontTSHArray(Class<?> type) {
		this.type = type;
	}
	
	@Override
	public GuiCreator getCreator() {
		return CREATOR;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		return style.makeTable(type, list);
	}
}
