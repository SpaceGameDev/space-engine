package space.util.gui.monofont;

import space.util.gui.elements.direction.GuiColumn;
import space.util.gui.elements.direction.GuiDirectional;
import space.util.gui.elements.direction.GuiRow;
import space.util.gui.elements.direction.GuiTable;
import space.util.gui.elements.text.GuiText1D;
import space.util.gui.elements.text.GuiText2D;
import space.util.gui.elements.tsh.GuiArray;
import space.util.gui.elements.tsh.GuiModifier;
import space.util.gui.elements.tsh.GuiVariable;
import space.util.gui.monofont.elements.direction.MonofontColumn;
import space.util.gui.monofont.elements.direction.MonofontDirectional;
import space.util.gui.monofont.elements.direction.MonofontRow;
import space.util.gui.monofont.elements.direction.MonofontTable;
import space.util.gui.monofont.elements.text.MonofontText1D;
import space.util.gui.monofont.elements.text.MonofontText2D;
import space.util.gui.monofont.elements.tsh.MonofontArray;
import space.util.gui.monofont.elements.tsh.MonofontModifier;
import space.util.gui.monofont.elements.tsh.MonofontObjects;
import space.util.gui.monofont.elements.tsh.MonofontVariable;
import space.util.gui.simple.SimpleGuiApi;
import space.util.string.CharSequence2D;
import space.util.string.String2D;
import space.util.string.toStringHelper.AbstractToStringHelperObjectsInstance;
import space.util.string.toStringHelper.ToStringHelper;

public class MonofontGuiApi extends SimpleGuiApi<MonofontGuiElement> implements ToStringHelper<MonofontGuiElement> {
	
	public static final MonofontGuiApi INSTANCE = new MonofontGuiApi();
	
	protected MonofontGuiApi() {
		//direction
		addElements(MonofontColumn::new, MonofontColumn.class, GuiColumn.class);
		addElements(MonofontRow::new, MonofontRow.class, GuiRow.class);
		addElements(MonofontDirectional::new, MonofontDirectional.class, GuiDirectional.class);
		addElements(MonofontTable::new, MonofontTable.class, GuiTable.class);
		//text
		addElements(MonofontText1D::new, MonofontText1D.class, GuiText1D.class);
		addElements(MonofontText2D::new, MonofontText2D.class, GuiText2D.class);
		//tsh
		addElements(MonofontArray::new, MonofontArray.class, GuiArray.class);
		addElements(MonofontArray::new, MonofontModifier.class, GuiModifier.class);
		addElements(MonofontVariable::new, MonofontVariable.class, GuiVariable.class);
//		new SimpleGuiElementSearcher<>(this, false).run();
	}
	
	@Override
	public Class<MonofontGuiElement> getBaseElementClass() {
		return MonofontGuiElement.class;
	}
	
	//native
	@Override
	public MonofontGuiElement toString(byte obj) {
		return create(MonofontText1D.class).setCharSequence(Byte.toString(obj));
	}
	
	@Override
	public MonofontGuiElement toString(short obj) {
		return create(MonofontText1D.class).setCharSequence(Short.toString(obj));
	}
	
	@Override
	public MonofontGuiElement toString(int obj) {
		return create(MonofontText1D.class).setCharSequence(Integer.toString(obj));
	}
	
	@Override
	public MonofontGuiElement toString(long obj) {
		return create(MonofontText1D.class).setCharSequence(Long.toString(obj));
	}
	
	@Override
	public MonofontGuiElement toString(float obj) {
		return create(MonofontText1D.class).setCharSequence(Float.toString(obj));
	}
	
	@Override
	public MonofontGuiElement toString(double obj) {
		return create(MonofontText1D.class).setCharSequence(Double.toString(obj));
	}
	
	@Override
	public MonofontGuiElement toString(boolean obj) {
		return create(MonofontText1D.class).setCharSequence(Boolean.toString(obj));
	}
	
	@Override
	public MonofontGuiElement toString(char obj) {
		return create(MonofontText1D.class).setCharSequence(Character.toString(obj));
	}
	
	//array
	@Override
	public MonofontGuiElement toString(byte[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement toString(short[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement toString(int[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement toString(long[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement toString(float[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement toString(double[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement toString(boolean[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement toString(char[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return list;
	}
	
	//object
	@Override
	public MonofontGuiElement toString(Object obj) {
		if (obj instanceof MonofontGuiElement)
			return (MonofontGuiElement) obj;
		if (obj instanceof String)
			return toString((String) obj);
		if (obj instanceof CharSequence2D)
			return toString((CharSequence2D) obj);
		if (obj instanceof CharSequence)
			return toString((CharSequence) obj);
		if (obj instanceof Object[])
			return toString((Object[]) obj);
		return create(MonofontText1D.class).setCharSequence(obj.toString());
	}
	
	@Override
	public MonofontGuiElement toString(Object[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(toString(obj[i]));
		return list;
	}
	
	//String
	@Override
	public MonofontGuiElement toString(CharSequence str) {
		return create(MonofontText1D.class).setCharSequence(str);
	}
	
	@Override
	public MonofontGuiElement toString(String str) {
		return create(MonofontText1D.class).setCharSequence(str);
	}
	
	@Override
	public MonofontGuiElement toString(CharSequence2D str) {
		return create(MonofontText2D.class).setCharSequence(str);
	}
	
	@Override
	public MonofontGuiElement toString(String2D str) {
		return create(MonofontText2D.class).setCharSequence(str);
	}
	
	//modifier
	@Override
	public MonofontGuiElement createModifier(String modifier, Object value) {
		return new MonofontModifier(modifier, value);
	}
	
	//objects
	@Override
	public ToStringHelperObjectsInstance<MonofontGuiElement> createObjectInstance(Object obj) {
		return new AbstractToStringHelperObjectsInstance<MonofontGuiElement>(obj, this) {
			@Override
			public MonofontGuiElement build() {
				return new MonofontObjects(this);
			}
		};
	}

//	//to extra
//	@Override
//	public MonofontGuiElement toString(CharSequence obj) {
//		return create(MonofontText1D.class).setCharSequence("\"" + obj + "\"");
//	}
//
//	@Override
//	public MonofontGuiElement toString(CharSequence2D obj) {
//		char[][] chars = obj.getChars();
//		int height = obj.height();
//
//		char[][] nchars = new char[height][];
//		int nmaxLength = obj.maxLength() + 2;
//
//		//first
//		{
//			char[] c = chars[0];
//			char[] nc = new char[nmaxLength];
//			System.arraycopy(c, 0, nc, 1, c.length);
//			Arrays.fill(nc, c.length + 1, nc.length - 1, ' ');
//			nc[0] = '"';
//			nc[nc.length - 1] = '"';
//			nchars[0] = nc;
//		}
//
//		//rest
//		for (int i = 1; i < height; i++) {
//			char[] c = chars[height];
//			char[] nc = new char[c.length + 1];
//			System.arraycopy(c, 0, nc, 1, c.length);
//			nc[0] = ' ';
//			nchars[i] = nc;
//		}
//
//		return create(MonofontText2D.class).setCharSequence(new String2D(nchars, nmaxLength));
//	}
//
//	public MonofontGuiElement toString(MonofontGuiElement obj) {
//		return obj;
//	}
}
