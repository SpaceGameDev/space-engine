package space.util.concurrent.task.chained;

import space.util.concurrent.event.Event;
import space.util.concurrent.task.creator.TaskCreator;
import space.util.dependency.Dependency;
import space.util.dependency.NoDepDependency;
import space.util.dependency.SimpleDependency;

public interface ChainedTaskBuilder<FUNCTION> extends TaskCreator<FUNCTION>, Event<ChainedTaskEntry<FUNCTION>> {
	
	//addHook
	@Override
	void addHook(ChainedTaskEntry<FUNCTION> task);
	
	default ChainedTaskEntry<FUNCTION> addHook(Dependency dependency, FUNCTION function) {
		ChainedTaskEntry<FUNCTION> ret = new ChainedTaskEntry<>(dependency, function);
		addHook(ret);
		return ret;
	}
	
	default ChainedTaskEntry<FUNCTION> addHook(String uuid, FUNCTION function) {
		return addHook(new NoDepDependency(uuid), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addHook(String uuid, int defaultPriority, FUNCTION function) {
		return addHook(new SimpleDependency(uuid, defaultPriority), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addHook(String uuid, String[] requires, FUNCTION function) {
		return addHook(new SimpleDependency(uuid, requires), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addHook(String uuid, String[] requires, int defaultPriority, FUNCTION function) {
		return addHook(new SimpleDependency(uuid, requires, defaultPriority), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addHook(String uuid, String[] requires, String[] requiredBy, FUNCTION function) {
		return addHook(new SimpleDependency(uuid, requires, requiredBy), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addHook(String uuid, String[] requires, String[] requiredBy, int defaultPriority, FUNCTION function) {
		return addHook(new SimpleDependency(uuid, requires, requiredBy, defaultPriority), function);
	}
	
	//other
	@Override
	boolean removeHook(ChainedTaskEntry<FUNCTION> task);
}
