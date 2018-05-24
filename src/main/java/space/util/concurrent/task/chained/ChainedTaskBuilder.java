package space.util.concurrent.task.chained;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.concurrent.event.Event;
import space.util.concurrent.task.creator.TaskCreator;
import space.util.dependency.Dependency;
import space.util.dependency.NoDepDependency;
import space.util.dependency.SimpleDependency;

public interface ChainedTaskBuilder<FUNCTION> extends TaskCreator<FUNCTION>, Event<ChainedTaskEntry<FUNCTION>> {
	
	//addHook
	@Override
	void addHook(@NotNull ChainedTaskEntry<FUNCTION> task);
	
	default ChainedTaskEntry<@NotNull FUNCTION> addHook(Dependency dependency, @NotNull FUNCTION function) {
		ChainedTaskEntry<@NotNull FUNCTION> ret = new ChainedTaskEntry<>(dependency, function);
		addHook(ret);
		return ret;
	}
	
	default ChainedTaskEntry<@NotNull FUNCTION> addHook(@Nullable String uuid, @NotNull FUNCTION function) {
		return addHook(new NoDepDependency(uuid), function);
	}
	
	default ChainedTaskEntry<@NotNull FUNCTION> addHook(@Nullable String uuid, int defaultPriority, @NotNull FUNCTION function) {
		return addHook(new SimpleDependency(uuid, defaultPriority), function);
	}
	
	default ChainedTaskEntry<@NotNull FUNCTION> addHook(@Nullable String uuid, @Nullable String[] requires, @NotNull FUNCTION function) {
		return addHook(new SimpleDependency(uuid, requires), function);
	}
	
	default ChainedTaskEntry<@NotNull FUNCTION> addHook(@Nullable String uuid, @Nullable String[] requires, int defaultPriority, @NotNull FUNCTION function) {
		return addHook(new SimpleDependency(uuid, requires, defaultPriority), function);
	}
	
	default ChainedTaskEntry<@NotNull FUNCTION> addHook(@Nullable String uuid, @Nullable String[] requires, @Nullable String[] requiredBy, @NotNull FUNCTION function) {
		return addHook(new SimpleDependency(uuid, requires, requiredBy), function);
	}
	
	default ChainedTaskEntry<@NotNull FUNCTION> addHook(@Nullable String uuid, @Nullable String[] requires, @Nullable String[] requiredBy, int defaultPriority, @NotNull FUNCTION function) {
		return addHook(new SimpleDependency(uuid, requires, requiredBy, defaultPriority), function);
	}
	
	//other
	@Override
	boolean removeHook(@NotNull ChainedTaskEntry<@NotNull FUNCTION> task);
}
