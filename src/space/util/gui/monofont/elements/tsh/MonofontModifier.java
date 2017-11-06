package space.util.gui.monofont.elements.tsh;

import space.util.gui.elements.tsh.GuiModifier;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.string.CharSequence2D;
import space.util.string.builder.CharBufferBuilder2D;

public class MonofontModifier extends MonofontGuiElement implements GuiModifier<MonofontGuiElement> {
	
	public CharSequence modifier;
	public MonofontGuiElement value;
	
	public MonofontModifier(CharSequence modifier, Object value) {
		super();
	}
	
	@Override
	public GuiModifier<MonofontGuiElement> setModifier(CharSequence modifier) {
		this.modifier = modifier;
		return this;
	}
	
	@Override
	public GuiModifier<MonofontGuiElement> setVariableValue(MonofontGuiElement value) {
		this.value = value;
		return this;
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
		return new CharBufferBuilder2D<>().append(modifier).append(" ").append(value.build());
	}
}
