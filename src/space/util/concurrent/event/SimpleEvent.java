package space.util.concurrent.event;

import space.util.concurrent.task.typehandler.ITypeHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple Event running hooks every time {@link SimpleEvent#run(ITypeHandler)} is called.
 */
public class SimpleEvent<FUNCTION> implements IRunnableEvent<FUNCTION> {
	
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
	public synchronized void run(ITypeHandler<FUNCTION> type) {
		notifyAll();
		after.forEach(type);
	}
}
