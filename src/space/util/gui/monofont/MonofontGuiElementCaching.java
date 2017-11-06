package space.util.gui.monofont;

import space.util.baseobject.interfaces.ICache;
import space.util.string.CharSequence2D;

public abstract class MonofontGuiElementCaching extends MonofontGuiElement implements ICache {
	
	public boolean isBuild = false;
	protected CharSequence2D buffer;
	
	@Override
	public int sizeX() {
		rebuild();
		return buffer.maxLength();
	}
	
	@Override
	public int sizeY() {
		rebuild();
		return buffer.height();
	}
	
	public void rebuild() {
		if (isBuild)
			return;
		buffer = rebuild0();
		isBuild = true;
	}
	
	public abstract CharSequence2D rebuild0();
	
	public void modification() {
		isBuild = false;
	}
	
	@Override
	public CharSequence2D build() {
		rebuild();
		return buffer;
	}
	
	@Override
	public void clear() {
		buffer = null;
		modification();
	}
	
	@Override
	public void setParent(MonofontGuiElement parent) {
		super.setParent(parent);
		modification();
	}
}
