package space.util.gui.monofont.elements.tsh;

import space.util.gui.monofont.MonofontGuiElement;
import space.util.string.CharSequence2D;
import space.util.string.toStringHelper.AbstractToStringHelperObjectsInstance;

public interface MonofontObjectsCreator {
	
	CharSequence2D makeTable(AbstractToStringHelperObjectsInstance<MonofontGuiElement> tsh);
}
