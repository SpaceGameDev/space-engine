package space.util.event.dependency;

import org.jetbrains.annotations.NotNull;
import space.util.event.Event;
import space.util.task.EventCreator;

public interface DependencyEventCreator<FUNCTION> extends EventCreator<FUNCTION>, Event<DependencyEventEntry<FUNCTION>> {
	
	//addHook
	@Override
	void addHook(@NotNull DependencyEventEntry<FUNCTION> task);
	
	//removeHook
	@Override
	boolean removeHook(@NotNull DependencyEventEntry<@NotNull FUNCTION> task);
}
