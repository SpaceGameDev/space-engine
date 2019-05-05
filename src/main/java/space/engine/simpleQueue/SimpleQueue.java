package space.engine.simpleQueue;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.stream.Stream;

public interface SimpleQueue<E> {
	
	void add(E e);
	
	default void addAll(Collection<E> collection) {
		collection.forEach(this::add);
	}
	
	default void addAll(E[] collection) {
		for (E e1 : collection)
			add(e1);
	}
	
	default void addAll(Stream<E> collection) {
		collection.forEach(this::add);
	}
	
	@Nullable E remove();
}
