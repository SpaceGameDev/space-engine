package space.util.gui.elements.tsh;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.gui.exception.IllegalGuiElementException;

@FunctionalInterface
public interface GuiModifierCreator extends GuiCreator {
	
	/**
	 * creates a modifier to a value in this or a similar way:
	 * <code>modifier + " " + value</code>
	 *
	 * @param modifier the modifier applied to the value (eg. synchronized, delegate etc.)
	 * @param value    the value as a {@link GuiElement}
	 * @return the new {@link ToStringHelperModifier}
	 * @throws IllegalGuiElementException if a supplied {@link GuiElement} is illegal (eg. wrong type)
	 */
	default ToStringHelperModifier create(String modifier, GuiElement value) throws IllegalGuiElementException {
		return create(modifier, " ", value);
	}
	
	/**
	 * creates a modifier to a value in this or a similar way:
	 * <code>modifier + separator + value</code>
	 *
	 * @param modifier  the modifier applied to the value (eg. synchronized, delegate etc.)
	 * @param separator the separator between the modifier and the value
	 * @param value     the value as a {@link GuiElement}
	 * @return the new {@link ToStringHelperModifier}
	 * @throws IllegalGuiElementException if a supplied {@link GuiElement} is illegal (eg. wrong type)
	 */
	ToStringHelperModifier create(String modifier, String separator, GuiElement value) throws IllegalGuiElementException;
	
	interface ToStringHelperModifier extends GuiElement {
	
	}
}
