package space.util.gui.monofont.elements.text;

import space.util.gui.elements.text.GuiText2DCreator;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.string.CharSequence2D;

public class MonofontText2D extends MonofontGuiElement implements GuiText2DCreator<MonofontGuiElement> {
	
	public CharSequence2D squ;
	
	public MonofontText2D() {
	}
	
	public MonofontText2D(CharSequence2D squ) {
		this.squ = squ;
	}
	
	@Override
	public MonofontText2D setCharSequence(CharSequence2D squ) {
		this.squ = squ;
		return this;
	}
	
	@Override
	public int sizeX() {
		return squ.maxLength();
	}
	
	@Override
	public int sizeY() {
		return squ.height();
	}
	
	@Override
	public CharSequence2D build() {
		return squ;
	}
}
