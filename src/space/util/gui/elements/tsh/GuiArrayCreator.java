package space.util.gui.elements.tsh;

import space.util.gui.GuiApi;
import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.gui.elements.direction.GuiDirectionalCreator.GuiDirectional;
import space.util.gui.elements.text.GuiText1DCreator;

import java.util.function.Function;

@FunctionalInterface
public interface GuiArrayCreator extends GuiCreator {
	
	/**
	 * creates a {@link GuiElement}-Array. It can be used to visualize an array[] or {@link java.util.List}.
	 *
	 * @param type  the type of array (eg. int, double, Object, String etc.).
	 *              you can get the value this way: <code>array.getClass().getComponentType()</code>
	 * @param array an array of {@link GuiElement}s with all the entries
	 * @return the new {@link GuiArray}
	 */
	default <TYPE> GuiArray create(GuiApi api, Class<TYPE> type, TYPE[] array, Function<TYPE, String> mapper) {
		GuiArray gui = create(type);
		for (TYPE elem : array)
			//noinspection unchecked
			gui.add(api.get(GuiText1DCreator.class).create(mapper.apply(elem)));
		return gui;
	}
	
	/**
	 * creates a {@link GuiElement}-Array. It can be used to visualize an array[] or {@link java.util.List}.
	 *
	 * @param type  the type of array (eg. int, double, Object, String etc.).
	 *              you can get the value this way: <code>array.getClass().getComponentType()</code>
	 * @param array an array of {@link GuiElement}s with all the entries
	 * @return the new {@link GuiArray}
	 */
	default GuiArray create(Class<?> type, GuiElement[] array) {
		GuiArray gui = create(type);
		for (GuiElement b : array)
			gui.add(b);
		return gui;
	}
	
	/**
	 * creates a new {@link GuiArray}
	 *
	 * @return the new {@link GuiArray}
	 */
	GuiArray create(Class<?> type);
	
	interface GuiArray extends GuiDirectional {
		
		@Override
		void add(GuiElement v);
		
		@Override
		GuiElement put(int index, GuiElement v);
		
		@Override
		GuiElement remove(int index);
		
		@Override
		void remove(GuiElement v);
	}
}
