package space.util.gui.monofont;

import space.util.gui.GuiApi;
import space.util.gui.GuiElement;
import space.util.string.CharSequence2D;

public abstract class MonofontGuiElement implements GuiElement<MonofontGuiElement> {
	
	public MonofontGuiElement parent;
	
	//parent
	public void setParent(MonofontGuiElement parent) {
		this.parent = parent;
	}
	
	//stackSize
	public int getStackSize() {
		if (parent == null)
			return 0;
		return parent.getStackSize() + 1;
	}
	
	//api
	@Override
	public GuiApi<MonofontGuiElement> getApi() {
		return MonofontGuiApi.INSTANCE;
	}
	
	//size
	public abstract int sizeX();
	
	public abstract int sizeY();
	
	//build
	public abstract CharSequence2D build();
	
	@Override
	public String toString() {
		return build().toString();
	}
}
