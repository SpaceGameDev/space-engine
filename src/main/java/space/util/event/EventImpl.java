package space.util.event;

import org.jetbrains.annotations.NotNull;
import space.util.event.typehandler.TypeHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple Event Implementation.
 * It will run the hooks every time {@link EventImpl#run(TypeHandler)} is called.
 */
public class EventImpl<FUNCTION> implements Event<FUNCTION> {
	
	public List<FUNCTION> after = new ArrayList<>();
	
	//hook
	@Override
	public synchronized void addHook(@NotNull FUNCTION func) {
		after.add(func);
	}
	
	@Override
	public synchronized boolean removeHook(@NotNull FUNCTION hook) {
		return after.remove(hook);
	}
	
	//run
	public synchronized void run(@NotNull TypeHandler<FUNCTION> type) {
		after.forEach(type);
	}
}
