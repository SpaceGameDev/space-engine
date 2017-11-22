package space.util.baseobject;

import space.util.gui.monofont.MonofontGuiApi;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.ArrayList;

public class Test {
	
	public static void main(String[] args) throws Throwable {
		ToStringHelper.setDefault(MonofontGuiApi.INSTANCE);
		
		ArrayList<?> list = Createable.create(ArrayList.class);
		System.out.println(list.getClass().getName() + " -> " + list);
		System.out.println(Createable.MAP);
	}
}
