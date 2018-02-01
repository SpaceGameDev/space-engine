package space.util.concurrent.task.chained;

import space.util.concurrent.event.IEvent;
import space.util.concurrent.task.creator.ITaskCreator;
import space.util.dependency.Dependency;
import space.util.dependency.IDependency;
import space.util.dependency.NoDepDependency;

public interface IChainedTaskBuilder<FUNCTION> extends ITaskCreator<FUNCTION>, IEvent<ChainedTaskEntry<FUNCTION>> {
	
	//addHook
	@Override
	void addHook(ChainedTaskEntry<FUNCTION> task);
	
	default ChainedTaskEntry<FUNCTION> addHook(IDependency dependency, FUNCTION function) {
		ChainedTaskEntry<FUNCTION> ret = new ChainedTaskEntry<>(dependency, function);
		addHook(ret);
		return ret;
	}
	
	default ChainedTaskEntry<FUNCTION> addHook(String uuid, FUNCTION function) {
		return addHook(new NoDepDependency(uuid), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addHook(String uuid, int defaultPriority, FUNCTION function) {
		return addHook(new Dependency(uuid, defaultPriority), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addHook(String uuid, String[] requires, FUNCTION function) {
		return addHook(new Dependency(uuid, requires), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addHook(String uuid, String[] requires, int defaultPriority, FUNCTION function) {
		return addHook(new Dependency(uuid, requires, defaultPriority), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addHook(String uuid, String[] requires, String[] requiredBy, FUNCTION function) {
		return addHook(new Dependency(uuid, requires, requiredBy), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addHook(String uuid, String[] requires, String[] requiredBy, int defaultPriority, FUNCTION function) {
		return addHook(new Dependency(uuid, requires, requiredBy, defaultPriority), function);
	}
	
	//other
	@Override
	boolean removeHook(ChainedTaskEntry<FUNCTION> task);
}
