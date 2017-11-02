package space.util.gui;

import space.util.dependency.Dependency;

public class GuiTestObjects {
	
	public static void main(String[] args) {
		System.out.println(new Dependency("test", new String[] {"before"}, new String[] {"after", "last"}));
	}
}
