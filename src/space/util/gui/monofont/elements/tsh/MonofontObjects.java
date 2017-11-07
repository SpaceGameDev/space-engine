package space.util.gui.monofont.elements.tsh;

import space.util.gui.monofont.MonofontGuiElementCaching;
import space.util.string.CharSequence2D;
import space.util.string.toStringHelper.AbstractToStringHelperObjectsInstance;

import java.util.function.Supplier;

public class MonofontObjects extends MonofontGuiElementCaching {
	
	public static Supplier<MonofontObjectsCreator> DEFAULT = () -> MonofontObjectsCreatorImpl.INSTANCE;
	
	public AbstractToStringHelperObjectsInstance tsh;
	
	public MonofontObjects(AbstractToStringHelperObjectsInstance tsh) {
		this.tsh = tsh;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		return DEFAULT.get().makeTable(tsh);
	}
}
