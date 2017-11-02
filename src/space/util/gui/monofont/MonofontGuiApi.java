package space.util.gui.monofont;

import space.util.conversion.ToObject;
import space.util.gui.elements.direction.GuiColumn;
import space.util.gui.elements.direction.GuiDirectional;
import space.util.gui.elements.direction.GuiRow;
import space.util.gui.elements.direction.GuiTable;
import space.util.gui.elements.objects.GuiArray;
import space.util.gui.elements.objects.GuiVariable;
import space.util.gui.elements.text.GuiText1D;
import space.util.gui.elements.text.GuiText2D;
import space.util.gui.monofont.elements.direction.MonofontColumn;
import space.util.gui.monofont.elements.direction.MonofontDirectional;
import space.util.gui.monofont.elements.direction.MonofontRow;
import space.util.gui.monofont.elements.direction.MonofontTable;
import space.util.gui.monofont.elements.objects.MonofontArray;
import space.util.gui.monofont.elements.objects.MonofontVariable;
import space.util.gui.monofont.elements.text.MonofontText1D;
import space.util.gui.monofont.elements.text.MonofontText2D;
import space.util.gui.simple.SimpleGuiApi;
import space.util.string.CharSequence2D;
import space.util.string.String2D;

import java.util.Arrays;

public class MonofontGuiApi extends SimpleGuiApi<MonofontGuiElement> implements ToObject<MonofontGuiElement> {
	
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
		//object
		addElements(MonofontVariable::new, MonofontVariable.class, GuiVariable.class);
		addElements(MonofontArray::new, MonofontArray.class, GuiArray.class);
//		new SimpleGuiElementSearcher<>(this, false).run();
	}
	
	@Override
	public Class<MonofontGuiElement> getBaseElementClass() {
		return MonofontGuiElement.class;
	}
	
	//to primitive
	@Override
	public MonofontGuiElement to(byte obj) {
		return create(MonofontText1D.class).setCharSequence(Byte.toString(obj));
	}
	
	@Override
	public MonofontGuiElement to(short obj) {
		return create(MonofontText1D.class).setCharSequence(Short.toString(obj));
	}
	
	@Override
	public MonofontGuiElement to(int obj) {
		return create(MonofontText1D.class).setCharSequence(Integer.toString(obj));
	}
	
	@Override
	public MonofontGuiElement to(long obj) {
		return create(MonofontText1D.class).setCharSequence(Long.toString(obj));
	}
	
	@Override
	public MonofontGuiElement to(float obj) {
		return create(MonofontText1D.class).setCharSequence(Float.toString(obj));
	}
	
	@Override
	public MonofontGuiElement to(double obj) {
		return create(MonofontText1D.class).setCharSequence(Double.toString(obj));
	}
	
	@Override
	public MonofontGuiElement to(boolean obj) {
		return create(MonofontText1D.class).setCharSequence(Boolean.toString(obj));
	}
	
	@Override
	public MonofontGuiElement to(char obj) {
		return create(MonofontText1D.class).setCharSequence(Character.toString(obj));
	}
	
	//to array
	@Override
	public MonofontGuiElement to(byte[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(to(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement to(short[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(to(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement to(int[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(to(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement to(long[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(to(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement to(float[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(to(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement to(double[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(to(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement to(boolean[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(to(obj[i]));
		return list;
	}
	
	@Override
	public MonofontGuiElement to(char[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(to(obj[i]));
		return list;
	}
	
	//to object
	@Override
	public MonofontGuiElement to(Object obj) {
		if (obj instanceof MonofontGuiElement)
			return (MonofontGuiElement) obj;
		if (obj instanceof CharSequence)
			return to((CharSequence) obj);
		if (obj instanceof CharSequence2D)
			return to((CharSequence2D) obj);
		if (obj instanceof Object[])
			return to((Object[]) obj);
		return create(MonofontText1D.class).setCharSequence(obj.toString());
	}
	
	@Override
	public MonofontGuiElement to(Object[] obj, int from, int to) {
		MonofontArray list = create(MonofontArray.class);
		for (int i = from; i < to; i++)
			list.add(to(obj[i]));
		return list;
	}
	
	//to extra
	@Override
	public MonofontGuiElement to(CharSequence obj) {
		return create(MonofontText1D.class).setCharSequence("\"" + obj + "\"");
	}
	
	@Override
	public MonofontGuiElement to(CharSequence2D obj) {
		char[][] chars = obj.getChars();
		int height = obj.height();
		
		char[][] nchars = new char[height][];
		int nmaxLength = obj.maxLength() + 2;
		
		//first
		{
			char[] c = chars[0];
			char[] nc = new char[nmaxLength];
			System.arraycopy(c, 0, nc, 1, c.length);
			Arrays.fill(nc, c.length + 1, nc.length - 1, ' ');
			nc[0] = '"';
			nc[nc.length - 1] = '"';
			nchars[0] = nc;
		}
		
		//rest
		for (int i = 1; i < height; i++) {
			char[] c = chars[height];
			char[] nc = new char[c.length + 1];
			System.arraycopy(c, 0, nc, 1, c.length);
			nc[0] = ' ';
			nchars[i] = nc;
		}
		
		return create(MonofontText2D.class).setCharSequence(new String2D(nchars, nmaxLength));
	}
	
	public MonofontGuiElement to(MonofontGuiElement obj) {
		return obj;
	}
}
