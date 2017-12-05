package space.util.baseobject;

import space.util.gui.monofont.MonofontGuiApi;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.ArrayList;

public class Test {
	
	public static void main(String[] args) {
		ToStringHelper.setDefault(MonofontGuiApi.TSH);
		
		ArrayList<?> list = Creatable.create(ArrayList.class);
		System.out.println(list.getClass().getName() + " -> " + list);
		System.out.println(Creatable.MAP);
	}
}
