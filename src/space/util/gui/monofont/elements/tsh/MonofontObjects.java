package space.util.gui.monofont.elements.tsh;

import space.util.gui.monofont.MonofontGuiElementCaching;
import space.util.string.CharSequence2D;
import space.util.string.toStringHelper.AbstractToStringHelperObjectsInstance;

public class MonofontObjects extends MonofontGuiElementCaching {
	
	public AbstractToStringHelperObjectsInstance tsh;
	
	public MonofontObjects(AbstractToStringHelperObjectsInstance tsh) {
		this.tsh = tsh;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		return null;
	}
}
