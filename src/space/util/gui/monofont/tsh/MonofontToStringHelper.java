package space.util.gui.monofont.tsh;

import space.util.gui.GuiApi;
import space.util.gui.elements.text.GuiText1DCreator;
import space.util.gui.elements.text.GuiText2DCreator;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.tsh.elements.GuiToStringHelperArrayCreator;
import space.util.gui.tsh.elements.GuiToStringHelperArrayCreator.ToStringHelperArray;
import space.util.gui.tsh.elements.GuiToStringHelperMapperCreator;
import space.util.gui.tsh.elements.GuiToStringHelperModifierCreator;
import space.util.gui.tsh.elements.GuiToStringHelperObjectsCreator;
import space.util.gui.tsh.elements.GuiToStringHelperTableCreator;
import space.util.string.CharSequence2D;
import space.util.string.String2D;
import space.util.string.toStringHelper.ToStringHelper;

public class MonofontToStringHelper implements ToStringHelper<MonofontGuiElement> {
	
	public GuiApi api;
	
	public MonofontToStringHelper(GuiApi api) {
		this.api = api;
	}
	
	//native
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement toString(byte b) {
		return (MonofontGuiElement) api.get(GuiText1DCreator.class).create(Byte.toString(b));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement toString(short s) {
		return (MonofontGuiElement) api.get(GuiText1DCreator.class).create(Short.toString(s));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement toString(int i) {
		return (MonofontGuiElement) api.get(GuiText1DCreator.class).create(Integer.toString(i));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement toString(long l) {
		return (MonofontGuiElement) api.get(GuiText1DCreator.class).create(Long.toString(l));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement toString(float f) {
		return (MonofontGuiElement) api.get(GuiText1DCreator.class).create(Float.toString(f));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement toString(double d) {
		return (MonofontGuiElement) api.get(GuiText1DCreator.class).create(Double.toString(d));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement toString(boolean b) {
		return (MonofontGuiElement) api.get(GuiText1DCreator.class).create(Boolean.toString(b));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement toString(char c) {
		return (MonofontGuiElement) api.get(GuiText1DCreator.class).create(Character.toString(c));
	}
	
	//native array
	@Override
	public MonofontGuiElement toString(byte[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray array = api.get(GuiToStringHelperArrayCreator.class).create(byte.class);
		for (int i = from; i < to; i++)
			array.put(i, toString(obj[i]));
		//noinspection unchecked
		return (MonofontGuiElement) array;
	}
	
	@Override
	public MonofontGuiElement toString(short[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray array = api.get(GuiToStringHelperArrayCreator.class).create(short.class);
		for (int i = from; i < to; i++)
			array.put(i, toString(obj[i]));
		//noinspection unchecked
		return (MonofontGuiElement) array;
	}
	
	@Override
	public MonofontGuiElement toString(int[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray array = api.get(GuiToStringHelperArrayCreator.class).create(int.class);
		for (int i = from; i < to; i++)
			array.put(i, toString(obj[i]));
		//noinspection unchecked
		return (MonofontGuiElement) array;
	}
	
	@Override
	public MonofontGuiElement toString(long[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray array = api.get(GuiToStringHelperArrayCreator.class).create(long.class);
		for (int i = from; i < to; i++)
			array.put(i, toString(obj[i]));
		//noinspection unchecked
		return (MonofontGuiElement) array;
	}
	
	@Override
	public MonofontGuiElement toString(float[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray array = api.get(GuiToStringHelperArrayCreator.class).create(float.class);
		for (int i = from; i < to; i++)
			array.put(i, toString(obj[i]));
		//noinspection unchecked
		return (MonofontGuiElement) array;
	}
	
	@Override
	public MonofontGuiElement toString(double[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray array = api.get(GuiToStringHelperArrayCreator.class).create(double.class);
		for (int i = from; i < to; i++)
			array.put(i, toString(obj[i]));
		//noinspection unchecked
		return (MonofontGuiElement) array;
	}
	
	@Override
	public MonofontGuiElement toString(boolean[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray array = api.get(GuiToStringHelperArrayCreator.class).create(boolean.class);
		for (int i = from; i < to; i++)
			array.put(i, toString(obj[i]));
		//noinspection unchecked
		return (MonofontGuiElement) array;
	}
	
	@Override
	public MonofontGuiElement toString(char[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray array = api.get(GuiToStringHelperArrayCreator.class).create(char.class);
		for (int i = from; i < to; i++)
			array.put(i, toString(obj[i]));
		//noinspection unchecked
		return (MonofontGuiElement) array;
	}
	
	//array object
	@Override
	public MonofontGuiElement toString(Object[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray array = api.get(GuiToStringHelperArrayCreator.class).create(obj.getClass().getComponentType());
		for (int i = from; i < to; i++)
			array.put(i, toString(obj[i]));
		//noinspection unchecked
		return (MonofontGuiElement) array;
	}
	
	//string
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement toString(CharSequence str) {
		return (MonofontGuiElement) api.get(GuiText1DCreator.class).create(str.toString());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement toString(String str) {
		return (MonofontGuiElement) api.get(GuiText1DCreator.class).create(str);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement toString(CharSequence2D str) {
		return (MonofontGuiElement) api.get(GuiText2DCreator.class).create(str);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement toString(String2D str) {
		return (MonofontGuiElement) api.get(GuiText2DCreator.class).create(str);
	}
	
	//null
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement toStringNull() {
		return (MonofontGuiElement) api.get(GuiText1DCreator.class).create("null");
	}
	
	//modifier
	@Override
	@SuppressWarnings("unchecked")
	public MonofontGuiElement createModifier(String modifier, Object value) {
		return (MonofontGuiElement) api.get(GuiToStringHelperModifierCreator.class).create(modifier, toString(value));
	}
	
	//object
	@Override
	@SuppressWarnings("unchecked")
	public ToStringHelperObjectsInstance<MonofontGuiElement> createObjectInstance(Object obj) {
		return (ToStringHelperObjectsInstance<MonofontGuiElement>) (Object) api.get(GuiToStringHelperObjectsCreator.class).create(obj, this);
	}
	
	//mapper
	@Override
	@SuppressWarnings("unchecked")
	public ToStringHelperTable<MonofontGuiElement> createMapper(String name, String separator, boolean align) {
		return (ToStringHelperTable<MonofontGuiElement>) (Object) api.get(GuiToStringHelperMapperCreator.class).create(name, new String2D(separator), align);
	}
	
	//table
	@Override
	@SuppressWarnings("unchecked")
	public ToStringHelperTable<MonofontGuiElement> createTable(String name, int dimensions) {
		return (ToStringHelperTable<MonofontGuiElement>) (Object) api.get(GuiToStringHelperTableCreator.class).create(name, dimensions);
	}
}
