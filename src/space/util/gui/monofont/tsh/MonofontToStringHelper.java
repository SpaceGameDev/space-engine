package space.util.gui.monofont.tsh;

import space.util.gui.GuiApi;
import space.util.gui.GuiElement;
import space.util.gui.elements.text.GuiText1DCreator;
import space.util.gui.elements.text.GuiText2DCreator;
import space.util.gui.elements.tsh.GuiToStringHelperArrayCreator;
import space.util.gui.elements.tsh.GuiToStringHelperMapperCreator;
import space.util.gui.elements.tsh.GuiToStringHelperModifierCreator;
import space.util.gui.elements.tsh.GuiToStringHelperObjectsCreator;
import space.util.gui.elements.tsh.GuiToStringHelperTableCreator;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMapArray;
import space.util.string.CharSequence2D;
import space.util.string.String2D;
import space.util.string.toStringHelper.ToStringHelper;

public class MonofontToStringHelper<T extends GuiElement> implements ToStringHelper<T> {
	
	public GuiApi api;
	
	public MonofontToStringHelper(GuiApi api) {
		this.api = api;
	}
	
	//native
	@Override
	public T toString(byte b) {
		//noinspection unchecked
		return (T) api.get(GuiText1DCreator.class).create(Byte.toString(b));
	}
	
	@Override
	public T toString(short s) {
		//noinspection unchecked
		return (T) api.get(GuiText1DCreator.class).create(Short.toString(s));
	}
	
	@Override
	public T toString(int i) {
		//noinspection unchecked
		return (T) api.get(GuiText1DCreator.class).create(Integer.toString(i));
	}
	
	@Override
	public T toString(long l) {
		//noinspection unchecked
		return (T) api.get(GuiText1DCreator.class).create(Long.toString(l));
	}
	
	@Override
	public T toString(float f) {
		//noinspection unchecked
		return (T) api.get(GuiText1DCreator.class).create(Float.toString(f));
	}
	
	@Override
	public T toString(double d) {
		//noinspection unchecked
		return (T) api.get(GuiText1DCreator.class).create(Double.toString(d));
	}
	
	@Override
	public T toString(boolean b) {
		//noinspection unchecked
		return (T) api.get(GuiText1DCreator.class).create(Boolean.toString(b));
	}
	
	@Override
	public T toString(char c) {
		//noinspection unchecked
		return (T) api.get(GuiText1DCreator.class).create(Character.toString(c));
	}
	
	//native array
	@Override
	public T toString(byte[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		IndexMap<T> indexMap = new IndexMapArray<>();
		for (int i = from; i < to; i++)
			indexMap.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) api.get(GuiToStringHelperArrayCreator.class).create(byte.class, indexMap);
	}
	
	@Override
	public T toString(short[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		IndexMap<T> indexMap = new IndexMapArray<>();
		for (int i = from; i < to; i++)
			indexMap.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) api.get(GuiToStringHelperArrayCreator.class).create(short.class, indexMap);
	}
	
	@Override
	public T toString(int[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		IndexMap<T> indexMap = new IndexMapArray<>();
		for (int i = from; i < to; i++)
			indexMap.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) api.get(GuiToStringHelperArrayCreator.class).create(int.class, indexMap);
	}
	
	@Override
	public T toString(long[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		IndexMap<T> indexMap = new IndexMapArray<>();
		for (int i = from; i < to; i++)
			indexMap.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) api.get(GuiToStringHelperArrayCreator.class).create(long.class, indexMap);
	}
	
	@Override
	public T toString(float[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		IndexMap<T> indexMap = new IndexMapArray<>();
		for (int i = from; i < to; i++)
			indexMap.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) api.get(GuiToStringHelperArrayCreator.class).create(float.class, indexMap);
	}
	
	@Override
	public T toString(double[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		IndexMap<T> indexMap = new IndexMapArray<>();
		for (int i = from; i < to; i++)
			indexMap.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) api.get(GuiToStringHelperArrayCreator.class).create(double.class, indexMap);
	}
	
	@Override
	public T toString(boolean[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		IndexMap<T> indexMap = new IndexMapArray<>();
		for (int i = from; i < to; i++)
			indexMap.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) api.get(GuiToStringHelperArrayCreator.class).create(boolean.class, indexMap);
	}
	
	@Override
	public T toString(char[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		IndexMap<T> indexMap = new IndexMapArray<>();
		for (int i = from; i < to; i++)
			indexMap.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) api.get(GuiToStringHelperArrayCreator.class).create(char.class, indexMap);
	}
	
	//string
	@Override
	public T toString(CharSequence str) {
		//noinspection unchecked
		return (T) api.get(GuiText1DCreator.class).create(str.toString());
	}
	
	@Override
	public T toString(String str) {
		//noinspection unchecked
		return (T) api.get(GuiText1DCreator.class).create(str);
	}
	
	@Override
	public T toString(CharSequence2D str) {
		//noinspection unchecked
		return (T) api.get(GuiText2DCreator.class).create(str);
	}
	
	@Override
	public T toString(String2D str) {
		//noinspection unchecked
		return (T) api.get(GuiText2DCreator.class).create(str);
	}
	
	//null
	@Override
	public T toStringNull() {
		//noinspection unchecked
		return (T) api.get(GuiText1DCreator.class).create("null");
	}
	
	@Override
	public T toString(Object[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		IndexMap<T> indexMap = new IndexMapArray<>();
		for (int i = from; i < to; i++)
			indexMap.put(i, toString(obj[i]));
		//noinspection unchecked
		return (T) api.get(GuiToStringHelperArrayCreator.class).create(obj.getClass().getComponentType(), indexMap);
	}
	
	//modifier
	@Override
	public T createModifier(String modifier, Object value) {
		//noinspection unchecked
		return (T) api.get(GuiToStringHelperModifierCreator.class).create(modifier, toString(value));
	}
	
	//object
	@Override
	public ToStringHelperObjectsInstance<T> createObjectInstance(Object obj) {
		//noinspection unchecked
		return (ToStringHelperObjectsInstance<T>) api.get(GuiToStringHelperObjectsCreator.class).create(obj, this);
	}
	
	//mapper
	@Override
	public ToStringHelperTable<T> createMapper(String name, String separator, boolean align) {
		//noinspection unchecked
		return (ToStringHelperTable<T>) api.get(GuiToStringHelperMapperCreator.class).create(name, separator, align);
	}
	
	//table
	@Override
	public ToStringHelperTable<T> createTable(String name, int dimensions) {
		//noinspection unchecked
		return (ToStringHelperTable<T>) api.get(GuiToStringHelperTableCreator.class).create(name, dimensions);
	}
}
