package space.util.event.dependency;

import org.jetbrains.annotations.NotNull;
import space.util.event.TaskEvent;
import space.util.event.basic.BasicEvent;

public interface DependencyEvent<FUNCTION> extends TaskEvent<FUNCTION>, BasicEvent<DependencyEventEntry<FUNCTION>> {
	
	//addHook
	@Override
	void addHook(@NotNull DependencyEventEntry<FUNCTION> task);
	
	//removeHook
	@Override
	boolean removeHook(@NotNull DependencyEventEntry<@NotNull FUNCTION> task);
}
