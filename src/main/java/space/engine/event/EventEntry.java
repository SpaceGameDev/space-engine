package space.engine.event;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EventEntry<FUNCTION> {
	
	//static
	public static final EventEntry<?>[] EMPTY_EVENT_ENTRY_ARRAY = new EventEntry[0];
	
	//object
	/**
	 * The function itself
	 */
	public final @NotNull FUNCTION function;
	
	/**
	 * anything in this array has be executed before this is executed
	 */
	public final @NotNull EventEntry<?>[] requires;
	
	/**
	 * anything in this array will be executed after this is executed
	 */
	public final @NotNull EventEntry<?>[] requiredBy;
	
	/**
	 * the default priority if no dependency is given
	 */
	public final int defaultPriority;
	
	//constructors
	public EventEntry(@NotNull FUNCTION function) {
		this(function, EMPTY_EVENT_ENTRY_ARRAY, EMPTY_EVENT_ENTRY_ARRAY, 0);
	}
	
	public EventEntry(@NotNull FUNCTION function, @NotNull EventEntry... requires) {
		this(function, requires, EMPTY_EVENT_ENTRY_ARRAY, 0);
	}
	
	public EventEntry(@NotNull FUNCTION function, @NotNull EventEntry<?>[] requires, int defaultPriority) {
		this(function, requires, EMPTY_EVENT_ENTRY_ARRAY, defaultPriority);
	}
	
	public EventEntry(@NotNull FUNCTION function, @NotNull EventEntry<?>[] requires, @NotNull EventEntry<?>[] requiredBy) {
		this(function, requires, requiredBy, 0);
	}
	
	public EventEntry(@NotNull FUNCTION function, @NotNull EventEntry<?>[] requires, @NotNull EventEntry<?>[] requiredBy, int defaultPriority) {
		this.function = function;
		this.requires = requires;
		this.requiredBy = requiredBy;
		this.defaultPriority = defaultPriority;
	}
	
	//Methods
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof EventEntry))
			return false;
		EventEntry<?> that = (EventEntry<?>) o;
		return function.equals(that.function);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(function);
	}
}
