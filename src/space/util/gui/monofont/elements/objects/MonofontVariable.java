package space.util.gui.monofont.elements.objects;

import space.util.gui.elements.objects.GuiVariable;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.string.CharSequence2D;
import space.util.string.builder.CharBufferBuilder2D;

public class MonofontVariable extends MonofontGuiElement implements GuiVariable<MonofontGuiElement> {
	
	public CharSequence name;
	public MonofontGuiElement value;
	
	@Override
	public GuiVariable<MonofontGuiElement> setVariableName(CharSequence name) {
		this.name = name;
		return this;
	}
	
	@Override
	public GuiVariable<MonofontGuiElement> setVariableValue(MonofontGuiElement value) {
		this.value = value;
		return this;
	}
	
	@Override
	public int sizeX() {
		return name.length() + 2 + value.sizeX();
	}
	
	@Override
	public int sizeY() {
		return Math.max(1, value.sizeY());
	}
	
	@Override
	public CharSequence2D build() {
		return new CharBufferBuilder2D<>().append(name).append(": ").append(value.build());
	}
}
