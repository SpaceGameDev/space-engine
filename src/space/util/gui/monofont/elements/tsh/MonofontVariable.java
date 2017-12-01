package space.util.gui.monofont.elements.tsh;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.gui.elements.tsh.GuiVariableCreator;
import space.util.gui.elements.tsh.GuiVariableCreator.GuiVariable;
import space.util.gui.exception.IllegalGuiElementException;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.string.CharSequence2D;
import space.util.string.builder.CharBufferBuilder2D;

public class MonofontVariable extends MonofontGuiElement implements GuiVariable {
	
	public static final GuiVariableCreator CREATOR = MonofontVariable::new;
	
	public String name;
	public MonofontGuiElement value;
	
	public MonofontVariable(String name, GuiElement value) {
		if (!(value instanceof MonofontGuiElement))
			throw new IllegalGuiElementException(value);
		
		this.name = name;
		this.value = (MonofontGuiElement) value;
	}
	
	@Override
	public GuiCreator getCreator() {
		return CREATOR;
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
