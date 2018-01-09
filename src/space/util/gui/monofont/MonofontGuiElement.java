package space.util.gui.monofont;

import space.util.baseobject.ToString;
import space.util.gui.GuiElement;
import space.util.string.CharSequence2D;
import space.util.string.ToString2D;
import space.util.string.toStringHelper.ToStringHelper;

public abstract class MonofontGuiElement implements ToString, GuiElement, ToString2D {
	
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
	
	//size
	public abstract int sizeX();
	
	public abstract int sizeY();
	
	//toString
	public abstract CharSequence2D buildSequence2D();
	
	@Override
	public CharSequence2D toString2D() {
		return buildSequence2D();
	}
	
	@Override
	public String toString() {
		return buildSequence2D().toString();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		if (api instanceof MonofontGuiApi)
			//noinspection unchecked
			return (T) this;
		return api.toString(buildSequence2D());
	}
}
