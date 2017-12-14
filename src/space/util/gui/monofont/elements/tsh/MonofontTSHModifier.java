package space.util.gui.monofont.elements.tsh;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.gui.elements.tsh.GuiToStringHelperModifierCreator;
import space.util.gui.elements.tsh.GuiToStringHelperModifierCreator.GuiToStringHelperModifier;
import space.util.gui.exception.IllegalGuiElementException;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.string.CharSequence2D;
import space.util.string.builder.CharBufferBuilder2D;

import static space.util.math.MathUtils.max;

public class MonofontTSHModifier extends MonofontGuiElement implements GuiToStringHelperModifier {
	
	public static final GuiToStringHelperModifierCreator CREATOR = MonofontTSHModifier::new;
	
	public String modifier;
	public MonofontGuiElement value;
	
	public MonofontTSHModifier(String modifier, String separator, GuiElement value) {
		this(modifier + separator, value);
	}
	
	public MonofontTSHModifier(String modifier, GuiElement value) {
		if (!(value instanceof MonofontGuiElement))
			throw new IllegalGuiElementException(value.getClass());
		
		this.modifier = modifier;
		this.value = (MonofontGuiElement) value;
	}
	
	@Override
	public int sizeX() {
		return modifier.length() + value.sizeX();
	}
	
	@Override
	public int sizeY() {
		return max(1, value.sizeY());
	}
	
	@Override
	public CharSequence2D buildSequence2D() {
		CharBufferBuilder2D<?> b = new CharBufferBuilder2D();
		b.append(modifier).append(value.buildSequence2D());
		return b;
	}
	
	@Override
	public GuiCreator getCreator() {
		return CREATOR;
	}
}
