package space.util.gui.monofont.elements.tsh;

import space.util.gui.elements.tsh.GuiObjectsCreator;
import space.util.gui.elements.tsh.GuiObjectsCreator.GuiObjects;
import space.util.gui.monofont.MonofontGuiElementCaching;
import space.util.gui.monofont.objectsCreator.MonofontObjectsCreator;
import space.util.gui.monofont.objectsCreator.MonofontObjectsCreatorImpl;
import space.util.string.CharSequence2D;
import space.util.string.toStringHelper.AbstractToStringHelperObjectsInstance;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.function.Supplier;

public class MonofontObjects extends MonofontGuiElementCaching implements GuiObjects {
	
	public static final GuiObjectsCreator CREATOR = MonofontObjects::new;
	public static Supplier<MonofontObjectsCreator> DEFAULT = () -> MonofontObjectsCreatorImpl.INSTANCE;
	
	public AbstractToStringHelperObjectsInstance<GuiObjects> tsh;
	
	public MonofontObjects(Object obj, ToStringHelper<Object> helper) {
		this(new MonofontObjectsToStringHelperObjectsInstance(obj, helper));
	}
	
	public MonofontObjects(AbstractToStringHelperObjectsInstance<GuiObjects> tsh) {
		this.tsh = tsh;
	}
	
	@Override
	public GuiObjectsCreator getCreator() {
		return MonofontObjects::new;
	}
	
	@Override
	public ToStringHelperObjectsInstance<GuiObjects> getTSH() {
		return tsh;
	}
	
	//rebuild
	@Override
	public CharSequence2D rebuild0() {
		return DEFAULT.get().makeTable(this);
	}
	
	public static class MonofontObjectsToStringHelperObjectsInstance extends AbstractToStringHelperObjectsInstance<GuiObjects> {
		
		public MonofontObjectsToStringHelperObjectsInstance(Object object, ToStringHelper<Object> helper) {
			super(object, helper);
		}
		
		@Override
		@Deprecated
		public GuiObjects build() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
	}
}
