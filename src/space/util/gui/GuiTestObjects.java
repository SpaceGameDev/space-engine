package space.util.gui;

import space.util.baseobject.ToString;
import space.util.gui.monofont.MonofontGuiApi;
import space.util.gui.tshImpl.ToStringHelperGuiWrapper;
import space.util.string.String2D;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class GuiTestObjects {
	
	public static void main(String[] args) {
		ToStringHelper.setDefault(new ToStringHelperGuiWrapper<>(MonofontGuiApi.INSTANCE));
//		System.out.println(new Dependency("test", new String[] {"before"}, new String[] {"after", "last"}));
		TestObj1 th = new TestObj1("Name thing", new String2D(new String[] {"Hello there!", "I'm a Name Thing"}));
		System.out.println(th);
	}
	
	public static class TestObj1 implements ToString {
		
		String name;
		String2D name2D;
		
		public TestObj1(String name, String2D name2D) {
			this.name = name;
			this.name2D = name2D;
		}
		
		@Override
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("name", this.name);
			tsh.add("name2D", this.name2D);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
}
