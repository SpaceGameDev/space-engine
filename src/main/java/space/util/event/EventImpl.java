package space.util.event;

import org.jetbrains.annotations.NotNull;
import space.util.event.typehandler.TypeHandler;
import space.util.task.TaskCreator;
import space.util.task.Tasks;

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
		return Tasks.create(() -> {
			synchronized (this) {
				after.forEach(type);
			}
		});
	}
}
