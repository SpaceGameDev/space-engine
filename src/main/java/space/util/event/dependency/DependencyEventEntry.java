package space.util.event.dependency;

import org.jetbrains.annotations.NotNull;
import space.util.baseobject.ToString;
import space.util.dependency.Dependency;
import space.util.dependency.NoDepDependency;
import space.util.dependency.SimpleDependency;
import space.util.event.TaskEvent;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;
import space.util.task.impl.RunnableTask;

import java.util.Comparator;

public class DependencyEventEntry<FUNCTION> implements ToString, Comparable<DependencyEventEntry<FUNCTION>> {
	
	public static final Comparator<@NotNull DependencyEventEntry<?>> COMPARATOR = (o1, o2) -> o1 == o2 ? 0 : Dependency.COMPARATOR.compare(o1.dependency, o2.dependency);
	
	public @NotNull TaskEvent<FUNCTION> function;
	public @NotNull Dependency dependency;
	
	public DependencyEventEntry(@NotNull FUNCTION function, @NotNull Dependency dependency) {
		this(handler -> new RunnableTask(() -> handler.accept(function)), dependency);
	}
	
	public DependencyEventEntry(@NotNull TaskEvent<FUNCTION> function, @NotNull Dependency dependency) {
		this.function = function;
		this.dependency = dependency;
	}
	
	//constructor delegate
	@SuppressWarnings("unused")
	public static <FUNCTION> DependencyEventEntry<FUNCTION> fromFunction(@NotNull FUNCTION function, @NotNull String uuid) {
		return new DependencyEventEntry<>(function, new NoDepDependency(uuid));
	}
	
	@SuppressWarnings("unused")
	public static <FUNCTION> DependencyEventEntry<FUNCTION> fromFunction(@NotNull FUNCTION function, @NotNull String uuid, int defaultPriority) {
		return new DependencyEventEntry<>(function, new SimpleDependency(uuid, defaultPriority));
	}
	
	@SuppressWarnings("unused")
	public static <FUNCTION> DependencyEventEntry<FUNCTION> fromFunction(@NotNull FUNCTION function, @NotNull String uuid, String[] requires) {
		return new DependencyEventEntry<>(function, new SimpleDependency(uuid, requires));
	}
	
	@SuppressWarnings("unused")
	public static <FUNCTION> DependencyEventEntry<FUNCTION> fromFunction(@NotNull FUNCTION function, @NotNull String uuid, String[] requires, int defaultPriority) {
		return new DependencyEventEntry<>(function, new SimpleDependency(uuid, requires, defaultPriority));
	}
	
	@SuppressWarnings("unused")
	public static <FUNCTION> DependencyEventEntry<FUNCTION> fromFunction(@NotNull FUNCTION function, @NotNull String uuid, String[] requires, String[] requiredBy) {
		return new DependencyEventEntry<>(function, new SimpleDependency(uuid, requires, requiredBy));
	}
	
	@SuppressWarnings("unused")
	public static <FUNCTION> DependencyEventEntry<FUNCTION> fromFunction(@NotNull FUNCTION function, @NotNull String uuid, String[] requires, String[] requiredBy, int defaultPriority) {
		return new DependencyEventEntry<>(function, new SimpleDependency(uuid, requires, requiredBy, defaultPriority));
	}
	
	@SuppressWarnings("unused")
	public static <FUNCTION> DependencyEventEntry<FUNCTION> fromEventCreator(@NotNull TaskEvent<FUNCTION> function, @NotNull String uuid) {
		return new DependencyEventEntry<>(function, new NoDepDependency(uuid));
	}
	
	@SuppressWarnings("unused")
	public static <FUNCTION> DependencyEventEntry<FUNCTION> fromEventCreator(@NotNull TaskEvent<FUNCTION> function, @NotNull String uuid, int defaultPriority) {
		return new DependencyEventEntry<>(function, new SimpleDependency(uuid, defaultPriority));
	}
	
	@SuppressWarnings("unused")
	public static <FUNCTION> DependencyEventEntry<FUNCTION> fromEventCreator(@NotNull TaskEvent<FUNCTION> function, @NotNull String uuid, String[] requires) {
		return new DependencyEventEntry<>(function, new SimpleDependency(uuid, requires));
	}
	
	@SuppressWarnings("unused")
	public static <FUNCTION> DependencyEventEntry<FUNCTION> fromEventCreator(@NotNull TaskEvent<FUNCTION> function, @NotNull String uuid, String[] requires, int defaultPriority) {
		return new DependencyEventEntry<>(function, new SimpleDependency(uuid, requires, defaultPriority));
	}
	
	@SuppressWarnings("unused")
	public static <FUNCTION> DependencyEventEntry<FUNCTION> fromEventCreator(@NotNull TaskEvent<FUNCTION> function, @NotNull String uuid, String[] requires, String[] requiredBy) {
		return new DependencyEventEntry<>(function, new SimpleDependency(uuid, requires, requiredBy));
	}
	
	@SuppressWarnings("unused")
	public static <FUNCTION> DependencyEventEntry<FUNCTION> fromEventCreator(@NotNull TaskEvent<FUNCTION> function, @NotNull String uuid, String[] requires, String[] requiredBy, int defaultPriority) {
		return new DependencyEventEntry<>(function, new SimpleDependency(uuid, requires, requiredBy, defaultPriority));
	}
	
	//methods
	@Override
	public int compareTo(@NotNull DependencyEventEntry<FUNCTION> o) {
		return COMPARATOR.compare(this, o);
	}
	
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("dependency", this.dependency);
		tsh.add("function", this.function);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
