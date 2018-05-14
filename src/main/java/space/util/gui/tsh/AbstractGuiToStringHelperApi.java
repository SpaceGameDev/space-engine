package space.util.gui.tsh;

import space.util.baseobject.ToString;
import space.util.gui.GuiApi;
import space.util.gui.GuiElement;
import space.util.gui.elements.text.GuiText1DCreator;
import space.util.gui.elements.text.GuiText2DCreator;
import space.util.gui.tsh.elements.GuiToStringHelperArrayCreator;
import space.util.gui.tsh.elements.GuiToStringHelperArrayCreator.ToStringHelperArray;
import space.util.gui.tsh.elements.GuiToStringHelperMapperCreator;
import space.util.gui.tsh.elements.GuiToStringHelperModifierCreator;
import space.util.gui.tsh.elements.GuiToStringHelperObjectsCreator;
import space.util.gui.tsh.elements.GuiToStringHelperTableCreator;
import space.util.string.CharSequence2D;
import space.util.string.String2D;
import space.util.string.toStringHelper.ToStringHelper;

public class AbstractGuiToStringHelperApi<T extends GuiElement> implements ToStringHelper<T> {
	
	public final GuiApi api;
	
	public AbstractGuiToStringHelperApi(GuiApi api) {
		this.api = api;
	}
	
	//native
	@Override
	@SuppressWarnings("unchecked")
	public T toString(byte obj) {
		return (T) api.get(GuiText1DCreator.class).create(Byte.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(short obj) {
		return (T) api.get(GuiText1DCreator.class).create(Short.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(int obj) {
		return (T) api.get(GuiText1DCreator.class).create(Integer.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(long obj) {
		return (T) api.get(GuiText1DCreator.class).create(Long.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(float obj) {
		return (T) api.get(GuiText1DCreator.class).create(Float.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(double obj) {
		return (T) api.get(GuiText1DCreator.class).create(Double.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(boolean obj) {
		return (T) api.get(GuiText1DCreator.class).create(Boolean.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(char obj) {
		return (T) api.get(GuiText1DCreator.class).create(Character.toString(obj));
	}
	
	//array
	@Override
	public T toString(byte[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray list = api.get(GuiToStringHelperArrayCreator.class).create(byte.class);
		for (int i = from; i < to; i++)
			list.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) list;
	}
	
	@Override
	public T toString(short[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray list = api.get(GuiToStringHelperArrayCreator.class).create(short.class);
		for (int i = from; i < to; i++)
			list.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) list;
	}
	
	@Override
	public T toString(int[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray list = api.get(GuiToStringHelperArrayCreator.class).create(int.class);
		for (int i = from; i < to; i++)
			list.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) list;
	}
	
	@Override
	public T toString(long[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray list = api.get(GuiToStringHelperArrayCreator.class).create(long.class);
		for (int i = from; i < to; i++)
			list.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) list;
	}
	
	@Override
	public T toString(float[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray list = api.get(GuiToStringHelperArrayCreator.class).create(float.class);
		for (int i = from; i < to; i++)
			list.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) list;
	}
	
	@Override
	public T toString(double[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray list = api.get(GuiToStringHelperArrayCreator.class).create(double.class);
		for (int i = from; i < to; i++)
			list.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) list;
	}
	
	@Override
	public T toString(boolean[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray list = api.get(GuiToStringHelperArrayCreator.class).create(boolean.class);
		for (int i = from; i < to; i++)
			list.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) list;
	}
	
	@Override
	public T toString(char[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray list = api.get(GuiToStringHelperArrayCreator.class).create(char.class);
		for (int i = from; i < to; i++)
			list.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) list;
	}
	
	//object
	@Override
	public T toString(Object obj) {
		return ToString.toTSH(this, obj);
	}
	
	@Override
	public T toString(Object[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		ToStringHelperArray list = api.get(GuiToStringHelperArrayCreator.class).create(obj.getClass().getComponentType());
		for (int i = from; i < to; i++)
			list.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) list;
	}
	
	//String
	@Override
	@SuppressWarnings("unchecked")
	public T toString(CharSequence str) {
		return str == null ? toStringNull() : (T) api.get(GuiText1DCreator.class).create(str.toString());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(String str) {
		return str == null ? toStringNull() : (T) api.get(GuiText1DCreator.class).create(str);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(CharSequence2D str) {
		return str == null ? toStringNull() : (T) api.get(GuiText2DCreator.class).create(str);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(String2D str) {
		return str == null ? toStringNull() : (T) api.get(GuiText2DCreator.class).create(str);
	}
	
	//null
	@Override
	@SuppressWarnings("unchecked")
	public T toStringNull() {
		return (T) api.get(GuiText1DCreator.class).create("null");
	}
	
	//modifier
	@Override
	@SuppressWarnings("unchecked")
	public T createModifier(String modifier, Object value) {
		return (T) api.get(GuiToStringHelperModifierCreator.class).create(modifier, toString(value));
	}
	
	//objects
	@Override
	@SuppressWarnings("unchecked")
	public ToStringHelperObjectsInstance<T> createObjectInstance(Object obj) {
		return (ToStringHelperObjectsInstance<T>) api.get(GuiToStringHelperObjectsCreator.class).create(obj, this);
	}
	
	//mapper
	@Override
	@SuppressWarnings("unchecked")
	public ToStringHelperTable<T> createMapper(String name, String separator, boolean align) {
		return (ToStringHelperTable<T>) api.get(GuiToStringHelperMapperCreator.class).create(name, new String2D(separator), align);
	}
	
	//table
	@Override
	@SuppressWarnings("unchecked")
	public ToStringHelperTable<T> createTable(String name, int dimensions) {
		return (ToStringHelperTable<T>) api.get(GuiToStringHelperTableCreator.class).create(name, dimensions);
	}
}
