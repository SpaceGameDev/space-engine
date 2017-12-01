package space.util.gui.monofont.elements.tsh;

import space.util.gui.GuiElement;
import space.util.gui.elements.tsh.GuiModifierCreator;
import space.util.gui.elements.tsh.GuiModifierCreator.GuiModifier;
import space.util.gui.exception.IllegalGuiElementException;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.string.CharSequence2D;
import space.util.string.builder.CharBufferBuilder2D;

public class MonofontModifier extends MonofontGuiElement implements GuiModifier {
	
	public static final GuiModifierCreator CREATOR = MonofontModifier::new;
	
	public String modifier;
	public String separator;
	public MonofontGuiElement value;
	
	public MonofontModifier(String modifier, String separator, GuiElement value) {
		if (!(value instanceof MonofontGuiElement))
			throw new IllegalGuiElementException(value);
		
		this.modifier = modifier;
		this.separator = separator;
		this.value = (MonofontGuiElement) value;
	}
	
	@Override
	public GuiModifierCreator getCreator() {
		return CREATOR;
	}
	
	@Override
	public int sizeX() {
		return modifier.length() + 1 + value.sizeX();
	}
	
	@Override
	public int sizeY() {
		return Math.max(1, value.sizeY());
	}
	
	@Override
	public CharSequence2D build() {
		return new CharBufferBuilder2D<>().append(modifier).append(separator).append(value.build());
	}
}
