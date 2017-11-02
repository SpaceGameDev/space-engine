package space.util.gui.monofont.elements.text;

import space.util.gui.elements.text.GuiText1D;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.string.CharSequence2D;
import space.util.string.String2D;

public class MonofontText1D extends MonofontGuiElement implements GuiText1D<MonofontGuiElement> {
	
	public CharSequence squ;
	
	public MonofontText1D() {
	}
	
	public MonofontText1D(CharSequence squ) {
		this.squ = squ;
	}
	
	@Override
	public MonofontText1D setCharSequence(CharSequence squ) {
		this.squ = squ;
		return this;
	}
	
	@Override
	public int sizeX() {
		return squ.length();
	}
	
	@Override
	public int sizeY() {
		return 1;
	}
	
	@Override
	public CharSequence2D build() {
		return new String2D(squ);
	}
}
