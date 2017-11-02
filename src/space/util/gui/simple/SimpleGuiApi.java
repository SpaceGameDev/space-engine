package space.util.gui.simple;

import space.util.delegate.map.specific.ClassMap.GetClassOrSuperMap;
import space.util.gui.GuiApi;
import space.util.gui.GuiElement;
import space.util.gui.GuiElementUnsupportedException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class SimpleGuiApi<BASEELEMENT extends GuiElement<BASEELEMENT>> implements GuiApi<BASEELEMENT> {
	
	public Map<Class<? extends GuiElement<?>>, Supplier<? extends GuiElement<?>>> map = new GetClassOrSuperMap<>(new HashMap<>());
	
	public void addElement(Supplier<? extends GuiElement<?>> con, Class<? extends GuiElement<?>> clazz) {
		map.put(clazz, con);
	}
	
	@SuppressWarnings("unchecked")
	public final void addElements(Supplier<? extends GuiElement<?>> con, Class<?>... clazz) {
		for (Class<?> e : clazz)
			map.put((Class<? extends GuiElement<?>>) e, con);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <ELEMENT extends GuiElement<?>, RETURN extends ELEMENT> RETURN create(Class<ELEMENT> type) {
		Supplier<? extends GuiElement<?>> sup = map.get(type);
		if (sup == null)
			throw new GuiElementUnsupportedException("Gui Element unsupported: " + type.getName());
		return (RETURN) sup.get();
	}
	
	@Override
	public <ELEMENT extends GuiElement<?>> boolean isSupported(Class<ELEMENT> type) {
		return map.containsKey(type);
	}
}
