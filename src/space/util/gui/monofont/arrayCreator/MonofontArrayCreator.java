package space.util.gui.monofont.arrayCreator;

import space.util.gui.monofont.elements.tsh.MonofontArray;
import space.util.string.CharSequence2D;

public interface MonofontArrayCreator {
	
	CharSequence2D makeTable(MonofontArray tsh);
}
