package space.util.task.function.chained;

import space.util.dependency.Dependency;
import space.util.dependency.IDependency;
import space.util.dependency.NoDepDependency;
import space.util.task.function.creator.IFunctionTaskCreator;

public interface IChainedTaskBuilder<FUNCTION> extends IFunctionTaskCreator<FUNCTION> {
	
	//addTask
	void addTask(ChainedTaskEntry<FUNCTION> task);
	
	default ChainedTaskEntry<FUNCTION> addTask(IDependency dependency, FUNCTION function) {
		ChainedTaskEntry<FUNCTION> ret = new ChainedTaskEntry<>(dependency, function);
		addTask(ret);
		return ret;
	}
	
	default ChainedTaskEntry<FUNCTION> addTask(String uuid, FUNCTION function) {
		return addTask(new NoDepDependency(uuid), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addTask(String uuid, int defaultPriority, FUNCTION function) {
		return addTask(new Dependency(uuid, defaultPriority), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addTask(String uuid, String[] requires, FUNCTION function) {
		return addTask(new Dependency(uuid, requires), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addTask(String uuid, String[] requires, int defaultPriority, FUNCTION function) {
		return addTask(new Dependency(uuid, requires, defaultPriority), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addTask(String uuid, String[] requires, String[] requiredBy, FUNCTION function) {
		return addTask(new Dependency(uuid, requires, requiredBy), function);
	}
	
	default ChainedTaskEntry<FUNCTION> addTask(String uuid, String[] requires, String[] requiredBy, int defaultPriority, FUNCTION function) {
		return addTask(new Dependency(uuid, requires, requiredBy, defaultPriority), function);
	}
	
	//other
	boolean removeTask(ChainedTaskEntry<FUNCTION> task);
}
