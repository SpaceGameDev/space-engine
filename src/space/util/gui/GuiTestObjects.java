package space.util.gui;

import space.util.dependency.Dependency;
import space.util.gui.monofont.MonofontGuiApi;
import space.util.string.toStringHelper.ToStringHelper;

public class GuiTestObjects {
	
	public static void main(String[] args) {
		ToStringHelper.setDefault(MonofontGuiApi.INSTANCE);
		System.out.println(new Dependency("test", new String[] {"before"}, new String[] {"after", "last"}));
	}
}
