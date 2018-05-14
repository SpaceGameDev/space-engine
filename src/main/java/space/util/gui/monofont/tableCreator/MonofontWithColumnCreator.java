package space.util.gui.monofont.tableCreator;

import space.util.gui.monofont.tableCreator.multi.MonofontTableCreator;
import space.util.gui.monofont.tableCreator.multi.MonofontWithTableCreator;

public interface MonofontWithColumnCreator extends MonofontWithTableCreator {
	
	void setMonofontColumnCreator(MonofontColumnCreator style);
	
	@Override
	default void setMonofontTableCreator(MonofontTableCreator style) {
		setMonofontColumnCreator(new MonofontColumnFromTable(style));
	}
}
