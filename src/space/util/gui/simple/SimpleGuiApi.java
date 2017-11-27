package space.util.gui.simple;

import space.util.delegate.map.specific.ClassMap.GetClassOrSuperMap;
import space.util.gui.GuiApi;
import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.gui.exception.GuiElementUnsupportedException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class SimpleGuiApi<BASE extends GuiElement<BASE, ?>> implements GuiApi<BASE> {
	
	public Map<Class<? extends GuiCreator<? extends BASE>>, Supplier<GuiCreator<? extends BASE>>> map = new GetClassOrSuperMap<>(new HashMap<>());
	
	public void addElement(Supplier<GuiCreator<? extends BASE>> con, Class<? extends GuiCreator<? extends BASE>> clazz) {
		map.put(clazz, con);
	}
	
	@SuppressWarnings("unchecked")
	public final void addElements(Supplier<GuiCreator<? extends BASE>> con, Class<?>... clazz) {
		for (Class<?> e : clazz)
			map.put((Class<? extends GuiCreator<? extends BASE>>) e, con);
	}
	
	@Override
	public <CREATOR extends GuiCreator<? extends BASE>> CREATOR get(Class<CREATOR> type) {
		Supplier<GuiCreator<? extends BASE>> creator = map.get(type);
		if (creator == null)
			throw new GuiElementUnsupportedException("Gui Element unsupported: " + type.getName());
		//noinspection unchecked
		return (CREATOR) creator;
	}
	
	@Override
	public <CREATOR extends GuiCreator<? extends BASE>> boolean isSupported(Class<CREATOR> type) {
		return map.containsKey(type);
	}
}
