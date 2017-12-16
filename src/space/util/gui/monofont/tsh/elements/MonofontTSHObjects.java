package space.util.gui.monofont.tsh.elements;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.MonofontGuiElementCaching;
import space.util.gui.monofont.tsh.objectsCreator.MonofontTSHObjectsCreator;
import space.util.gui.monofont.tsh.objectsCreator.MonofontTSHObjectsCreatorImpl;
import space.util.gui.tsh.elements.GuiToStringHelperObjectsCreator;
import space.util.gui.tsh.elements.GuiToStringHelperObjectsCreator.ToStringHelperObjects;
import space.util.string.CharSequence2D;
import space.util.string.String2D;
import space.util.string.toStringHelper.AbstractToStringHelperObjectsInstance;
import space.util.string.toStringHelper.ToStringHelper;

public class MonofontTSHObjects extends MonofontGuiElementCaching implements ToStringHelperObjects {
	
	public static final GuiToStringHelperObjectsCreator CREATOR = MonofontTSHObjects::new;
	
	public Object obj;
	public ToStringHelper<? extends GuiElement> helper;
	public AbstractToStringHelperObjectsInstance<MonofontGuiElement> tshObjectsInstance;
	public MonofontTSHObjectsCreator style = MonofontTSHObjectsCreatorImpl.DEFAULT;
	
	public MonofontTSHObjects(Object obj, ToStringHelper<? extends GuiElement> helper) {
		this.obj = obj;
		this.helper = helper;
		
		//noinspection unchecked
		tshObjectsInstance = new AbstractToStringHelperObjectsInstance<MonofontGuiElement>(obj, (ToStringHelper<MonofontGuiElement>) helper) {
			@Override
			public MonofontGuiElement build() {
				return MonofontTSHObjects.this;
			}
		};
	}
	
	@Override
	public GuiCreator getCreator() {
		return CREATOR;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		return style.makeTable(tshObjectsInstance);
	}
	
	//delegate
	@Override
	public MonofontGuiElement build() {
		return this;
	}
	
	@Override
	public String toString() {
		return tshObjectsInstance.toString();
	}
	
	@Override
	public void add(String name, byte obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, short obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, int obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, long obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, float obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, double obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, boolean obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, char obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, byte[] obj, int from, int to) {
		tshObjectsInstance.add(name, obj, from, to);
	}
	
	@Override
	public void add(String name, short[] obj, int from, int to) {
		tshObjectsInstance.add(name, obj, from, to);
	}
	
	@Override
	public void add(String name, int[] obj, int from, int to) {
		tshObjectsInstance.add(name, obj, from, to);
	}
	
	@Override
	public void add(String name, long[] obj, int from, int to) {
		tshObjectsInstance.add(name, obj, from, to);
	}
	
	@Override
	public void add(String name, float[] obj, int from, int to) {
		tshObjectsInstance.add(name, obj, from, to);
	}
	
	@Override
	public void add(String name, double[] obj, int from, int to) {
		tshObjectsInstance.add(name, obj, from, to);
	}
	
	@Override
	public void add(String name, boolean[] obj, int from, int to) {
		tshObjectsInstance.add(name, obj, from, to);
	}
	
	@Override
	public void add(String name, char[] obj, int from, int to) {
		tshObjectsInstance.add(name, obj, from, to);
	}
	
	@Override
	public void add(String name, Object obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, Object[] obj, int from, int to) {
		tshObjectsInstance.add(name, obj, from, to);
	}
	
	@Override
	public void addNull(String name) {
		tshObjectsInstance.addNull(name);
	}
	
	@Override
	public void add(String name, byte[] obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, short[] obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, int[] obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, long[] obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, float[] obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, double[] obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, boolean[] obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, char[] obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, Object[] obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, String obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, CharSequence obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, String2D obj) {
		tshObjectsInstance.add(name, obj);
	}
	
	@Override
	public void add(String name, CharSequence2D obj) {
		tshObjectsInstance.add(name, obj);
	}
}
