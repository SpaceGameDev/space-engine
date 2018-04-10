package space.util.concurrent.event;

import space.util.concurrent.task.typehandler.TypeHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple Event running hooks every time {@link SimpleEvent#run(TypeHandler)} is called.
 */
public class SimpleEvent<FUNCTION> implements RunnableEvent<FUNCTION> {
	
	public List<FUNCTION> after = new ArrayList<>();
	
	//hook
	@Override
	public synchronized void addHook(FUNCTION func) {
		after.add(func);
	}
	
	@Override
	public synchronized boolean removeHook(FUNCTION hook) {
		return after.remove(hook);
	}
	
	//run
	@Override
	public synchronized void run(TypeHandler<FUNCTION> type) {
		notifyAll();
		after.forEach(type);
	}
}
