package space.engine.observable;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.CanceledCheck;
import space.engine.event.EventEntry;
import space.engine.event.SequentialEventBuilder;
import space.engine.orderingGuarantee.GeneratingOrderingGuarantee;
import space.engine.sync.DelayTask;
import space.engine.sync.barrier.Barrier;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static space.engine.sync.Tasks.*;

/**
 * An {@link ObservableReference} allows you to update a reference with {@link #set(Object)} and running an {@link space.engine.event.Event} when doing so.
 * It also guarantees ordering of Event calls independent which Thread updates the reference.
 */
public class ObservableReference<T> {
	
	public static <T> ObservableReference<T> generatingReference(Supplier<T> generate, ObservableReference<?>... parents) {
		ObservableReference<T> reference = new ObservableReference<>();
		Consumer<Object> onChange = o -> reference.set(generate);
		for (ObservableReference<?> parent : parents)
			parent.addHook(onChange);
		return reference;
	}
	
	public static <T> ObservableReference<T> generatingReference(Supplier<T> generate, Collection<ObservableReference<?>> parents) {
		ObservableReference<T> reference = new ObservableReference<>();
		Consumer<Object> onChange = o -> reference.set(generate);
		parents.forEach(parent -> parent.addHook(onChange));
		return reference;
	}
	
	//object
	private final GeneratingOrderingGuarantee ordering = new GeneratingOrderingGuarantee();
	protected final SequentialEventBuilder<Consumer<? super T>> changeEvent = new SequentialEventBuilder<>();
	
	private volatile T t;
	
	public ObservableReference() {
	}
	
	public ObservableReference(T initial) {
		this.t = initial;
	}
	
	//get
	
	/**
	 * Only call this function inside a callback of this {@link ObservableReference}.
	 * Otherwise the results will be inconsistent.
	 */
	public T get() {
		return t;
	}
	
	//set
	public Barrier set(T t) {
		return ordering.next(prev -> runnableCancelable(canceledCheck -> {
			if (canceledCheck.isCanceled())
				return;
			this.t = t;
			Barrier barrier = changeEvent.runImmediatelyIfPossible(tConsumer -> tConsumer.accept(t));
			if (barrier != Barrier.ALWAYS_TRIGGERED_BARRIER)
				throw new DelayTask(barrier);
		}).submit(prev));
	}
	
	public Barrier set(Supplier<T> supplier) {
		return ordering.next(prev -> runnableCancelable(canceledCheck -> {
			if (canceledCheck.isCanceled())
				return;
			T t = supplier.get();
			
			if (canceledCheck.isCanceled())
				return;
			this.t = t;
			Barrier barrier = changeEvent.runImmediatelyIfPossible(tConsumer -> tConsumer.accept(t));
			if (barrier != Barrier.ALWAYS_TRIGGERED_BARRIER)
				throw new DelayTask(barrier);
		}).submit(prev));
	}
	
	public Barrier set(Function<CanceledCheck, T> supplier) {
		return ordering.next(prev -> runnableCancelable(canceledCheck -> {
			if (canceledCheck.isCanceled())
				return;
			T t = supplier.apply(canceledCheck);
			
			if (canceledCheck.isCanceled())
				return;
			this.t = t;
			Barrier barrier = changeEvent.runImmediatelyIfPossible(tConsumer -> tConsumer.accept(t));
			if (barrier != Barrier.ALWAYS_TRIGGERED_BARRIER)
				throw new DelayTask(barrier);
		}).submit(prev));
	}
	
	/**
	 * For debugging and highly controlled testing purposes only!
	 */
	@Deprecated
	public Barrier getLatestBarrier() {
		return ordering.getLatestBarrier();
	}
	
	//addHook
	public Barrier addHook(@NotNull EventEntry<Consumer<? super T>> hook) {
		return ordering.nextInbetween(prev -> runnable(() -> {
			hook.function.accept(t);
			changeEvent.addHook(hook);
		}).submit(prev));
	}
	
	public EventEntry<Consumer<? super T>> addHook(Consumer<? super T> changeConsumer) {
		EventEntry<Consumer<? super T>> entry = new EventEntry<>(changeConsumer);
		addHook(entry);
		return entry;
	}
	
	public EventEntry<Consumer<? super T>> addHook(Consumer<? super T> changeConsumer, @NotNull EventEntry<?>... requires) {
		EventEntry<Consumer<? super T>> entry = new EventEntry<>(changeConsumer, requires);
		addHook(entry);
		return entry;
	}
	
	public EventEntry<Consumer<? super T>> addHook(Consumer<? super T> changeConsumer, @NotNull EventEntry<?>[] requiredBy, @NotNull EventEntry<?>... requires) {
		EventEntry<Consumer<? super T>> entry = new EventEntry<>(changeConsumer, requiredBy, requires);
		addHook(entry);
		return entry;
	}
}
