package space.util.gui.monofont;

import space.util.baseobject.ToString;
import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.string.CharSequence2D;
import space.util.string.toStringHelper.ToStringHelper;

public abstract class MonofontGuiElement<CREATOR extends GuiCreator<MonofontGuiElement<CREATOR>>> implements ToString, GuiElement<MonofontGuiElement<CREATOR>, CREATOR> {
	
	public MonofontGuiElement parent;
	
	//parent
	public void setParent(MonofontGuiElement parent) {
		this.parent = parent;
	}
	
	//api
	@Override
	public MonofontGuiApi getApi() {
		return MonofontGuiApi.INSTANCE;
	}
	
	@Override
	public MonofontGuiElement<CREATOR> toBaseElement() {
		return this;
	}
	
	//size
	public abstract int sizeX();
	
	public abstract int sizeY();
	
	//toString
	public abstract CharSequence2D build();
	
	@Override
	public String toString() {
		return build().toString();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		if (api instanceof MonofontGuiApi)
			//noinspection unchecked
			return (T) this;
		return api.toString(build());
	}
}
