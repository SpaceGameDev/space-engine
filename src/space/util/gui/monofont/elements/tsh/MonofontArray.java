package space.util.gui.monofont.elements.tsh;

import space.util.gui.elements.tsh.GuiArrayCreator;
import space.util.gui.elements.tsh.GuiArrayCreator.GuiArray;
import space.util.gui.monofont.arrayCreator.MonofontArrayCreator;
import space.util.gui.monofont.arrayCreator.MonofontArrayCreatorImpl;
import space.util.gui.monofont.elements.direction.MonofontElementList;
import space.util.string.CharSequence2D;

import java.util.function.Supplier;

public class MonofontArray extends MonofontElementList implements GuiArray {
	
	public static final GuiArrayCreator CREATOR = MonofontArray::new;
	public static Supplier<MonofontArrayCreator> DEFAULT = () -> MonofontArrayCreatorImpl.INSTANCE;
	
	public Class<?> type;
	public MonofontArrayCreator style = DEFAULT.get();
	
	public MonofontArray(Class<?> type) {
		this.type = type;
	}
	
	@Override
	public GuiArrayCreator getCreator() {
		return CREATOR;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		return style.makeTable(this);
	}
}
