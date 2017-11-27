package space.util.gui.tshImpl;

import space.util.baseobject.ToString;
import space.util.gui.GuiApi;
import space.util.gui.GuiElement;
import space.util.gui.elements.direction.GuiTable;
import space.util.gui.elements.text.GuiText1D;
import space.util.gui.elements.tsh.GuiArrayCreator.GuiArray;
import space.util.gui.elements.tsh.GuiModifier;
import space.util.gui.monofont.elements.text.MonofontText2D;
import space.util.gui.monofont.elements.tsh.MonofontObjects;
import space.util.string.CharSequence2D;
import space.util.string.String2D;
import space.util.string.toStringHelper.AbstractToStringHelperObjectsInstance;
import space.util.string.toStringHelper.ToStringHelper;

public class ToStringHelperGuiWrapper<T extends GuiElement<T>> implements ToStringHelper<T> {
	
	public final GuiApi<T> api;
	
	public ToStringHelperGuiWrapper(GuiApi<T> api) {
		this.api = api;
	}
	
	//native
	@Override
	@SuppressWarnings("unchecked")
	public T toString(byte obj) {
		return (T) api.get(GuiText1D.class).setCharSequence(Byte.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(short obj) {
		return (T) api.get(GuiText1D.class).setCharSequence(Short.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(int obj) {
		return (T) api.get(GuiText1D.class).setCharSequence(Integer.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(long obj) {
		return (T) api.get(GuiText1D.class).setCharSequence(Long.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(float obj) {
		return (T) api.get(GuiText1D.class).setCharSequence(Float.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(double obj) {
		return (T) api.get(GuiText1D.class).setCharSequence(Double.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(boolean obj) {
		return (T) api.get(GuiText1D.class).setCharSequence(Boolean.toString(obj));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T toString(char obj) {
		return (T) api.get(GuiText1D.class).setCharSequence(Character.toString(obj));
	}
	
	//array
	@Override
	public T toString(byte[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		GuiArray<T> list = api.get(GuiArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return (T) list;
	}
	
	@Override
	public T toString(short[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		GuiArray<T> list = api.get(GuiArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return (T) list;
	}
	
	@Override
	public T toString(int[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		GuiArray<T> list = api.get(GuiArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return (T) list;
	}
	
	@Override
	public T toString(long[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		GuiArray<T> list = api.get(GuiArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return (T) list;
	}
	
	@Override
	public T toString(float[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		GuiArray<T> list = api.get(GuiArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return (T) list;
	}
	
	@Override
	public T toString(double[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		GuiArray<T> list = api.get(GuiArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return (T) list;
	}
	
	@Override
	public T toString(boolean[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		GuiArray<T> list = api.get(GuiArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return (T) list;
	}
	
	@Override
	public T toString(char[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		GuiArray<T> list = api.get(GuiArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
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
		GuiArray<T> list = api.get(GuiArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return (T) list;
	}
	
	//String
	@Override
	public T toString(CharSequence str) {
		return str == null ? toStringNull() : (T) api.get(GuiText1D.class).setCharSequence(str);
	}
	
	@Override
	public T toString(String str) {
		return str == null ? toStringNull() : (T) api.get(GuiText1D.class).setCharSequence(str);
	}
	
	@Override
	public T toString(CharSequence2D str) {
		return str == null ? toStringNull() : (T) api.get(MonofontText2D.class).setCharSequence(str);
	}
	
	@Override
	public T toString(String2D str) {
		return str == null ? toStringNull() : (T) api.get(MonofontText2D.class).setCharSequence(str);
	}
	
	//null
	@Override
	public T toStringNull() {
		return (T) api.get(GuiText1D.class).setCharSequence("null");
	}
	
	//modifier
	@Override
	@SuppressWarnings("unchecked")
	public T createModifier(String modifier, Object value) {
		GuiModifier<T> mod = api.get(GuiModifier.class);
		mod.setModifier(modifier);
		mod.setVariableValue(toString(value));
		return (T) mod;
	}
	
	//objects
	@Override
	public ToStringHelperObjectsInstance<T> createObjectInstance(Object obj) {
		return new AbstractToStringHelperObjectsInstance<T>(obj, this) {
			@Override
			public T build() {
				return new MonofontObjects(this);
			}
		};
	}
	
	//table
	@Override
	public ToStringHelperTable<T> createTable(Object name, int dimensions) {
		return new ToStringHelperTable<T>() {
			GuiTable<T> table = api.get(GuiTable.class);
			
			@Override
			public void put(int[] pos, T object) {
				table.getTable().put(pos, object);
			}
			
			@Override
			@SuppressWarnings("unchecked")
			public T build() {
				return (T) table;
			}
		};
	}
	
	@Override
	public ToStringHelperTable<T> createMapper(Object name, String separator, boolean align) {
		return null;
	}
}
