package space.util.gui.elements.tsh;

import space.util.gui.GuiApi;
import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.gui.elements.text.GuiText1DCreator;
import space.util.gui.exception.IllegalGuiElementException;

import java.util.function.Function;

@FunctionalInterface
public interface GuiArrayCreator<BASE extends GuiElement<BASE, ?>> extends GuiCreator<BASE> {
	
	/**
	 * creates a {@link GuiElement}-Array. It can be used to visualize an array[] or {@link java.util.List}.
	 *
	 * @param type  the type of array (eg. int, double, Object, String etc.).
	 *              you can get the value this way: <code>array.getClass().getComponentType()</code>
	 * @param array an array of {@link GuiElement}s with all the entries
	 * @return the new {@link GuiArray}
	 * @throws IllegalGuiElementException if a supplied {@link GuiElement} is illegal (eg. wrong type)
	 */
	default <TYPE> GuiArray create(GuiApi<?> api, Class<TYPE> type, TYPE[] array, Function<TYPE, String> mapper) throws IllegalGuiElementException {
		GuiElement<?, ?>[] elem = new GuiElement[array.length];
		for (int i = 0; i < array.length; i++)
			elem[i] = api.get(GuiText1DCreator.class).create(mapper.apply(array[i])).toBaseElement();
		
		//noinspection unchecked
		return create(type, (BASE[]) elem);
	}
	
	/**
	 * creates a {@link GuiElement}-Array. It can be used to visualize an array[] or {@link java.util.List}.
	 *
	 * @param type  the type of array (eg. int, double, Object, String etc.).
	 *              you can get the value this way: <code>array.getClass().getComponentType()</code>
	 * @param array an array of {@link GuiElement}s with all the entries
	 * @return the new {@link GuiArray}
	 * @throws IllegalGuiElementException if a supplied {@link GuiElement} is illegal (eg. wrong type)
	 */
	GuiArray create(Class<?> type, BASE[] array) throws IllegalGuiElementException;
	
	interface GuiArray<ELEMENT extends GuiElement<ELEMENT, CREATOR>, CREATOR extends GuiArrayCreator<ELEMENT>> extends GuiElement<ELEMENT, CREATOR> {
	
	}
}
