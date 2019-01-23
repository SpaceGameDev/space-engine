package space.util.gui.monofont.tsh.objectsCreator;

import space.util.gui.monofont.MonofontGuiElement;
import space.util.string.CharSequence2D;
import space.util.string.toStringHelper.AbstractToStringHelperObjectsInstance;

public interface MonofontTSHObjectsCreator {
	
	CharSequence2D makeTable(AbstractToStringHelperObjectsInstance<MonofontGuiElement> tsh);
}
