package space.engine.event;

import org.jetbrains.annotations.NotNull;
import space.engine.event.typehandler.TypeHandler;
import space.engine.sync.TaskCreator;
import space.engine.sync.Tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple Event Implementation.
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
	@Override
	public TaskCreator execute(@NotNull TypeHandler<FUNCTION> type) {
		return Tasks.runnable(() -> {
			synchronized (this) {
				after.forEach(type);
			}
		});
	}
}
