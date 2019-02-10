package space.engine.event;

import org.jetbrains.annotations.NotNull;
import space.engine.event.typehandler.TypeHandler;
import space.engine.sync.TaskCreator;
import space.engine.sync.barrier.Barrier;
import space.engine.sync.lock.SyncLock;

import static space.engine.sync.barrier.Barrier.EMPTY_BARRIER_ARRAY;
import static space.engine.sync.lock.SyncLock.EMPTY_SYNCLOCK_ARRAY;

/**
 * The {@link Event} Object is used to {@link Event#addHook(EventEntry)} and {@link Event#removeHook(EventEntry)} Hooks,
 * which will be called when the {@link Event} is triggered. The order in which they will be triggered is determined by
 * the Dependencies of the  supplied {@link EventEntry}.
 */
public interface Event<FUNCTION> {
	
	/**
	 * adds a Hook.
	 *
	 * @param hook the Hook to add
	 */
	void addHook(@NotNull EventEntry<FUNCTION> hook);
	
	/**
	 * Removes a Hook.<br>
	 * It is expected that the Method will be called very rarely and mustn't be optimized via hashing or other techniques.
	 *
	 * @param hook the Hook to be removed
	 */
	boolean removeHook(@NotNull EventEntry<FUNCTION> hook);
	
	/**
	 * Creates a new Task based upon this template.
	 * The Task may start execution after declared {@link Barrier Barriers} are triggered and while holding declared {@link SyncLock SyncLocks}.
	 *
	 * @return the created Task of generic type TASK
	 */
	default @NotNull Barrier submit(@NotNull TypeHandler<FUNCTION> typeHandler) {
		return submit(typeHandler, EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY);
	}
	
	/**
	 * Creates a new Task based upon this template.
	 * The Task may start execution after declared {@link Barrier Barriers} are triggered and while holding declared {@link SyncLock SyncLocks}.
	 *
	 * @return the created Task of generic type TASK
	 */
	default @NotNull Barrier submit(@NotNull TypeHandler<FUNCTION> typeHandler, @NotNull Barrier... barriers) {
		return submit(typeHandler, EMPTY_SYNCLOCK_ARRAY, barriers);
	}
	
	/**
	 * Creates a new Task based upon this template.
	 * The Task may start execution after declared {@link Barrier Barriers} are triggered and while holding declared {@link SyncLock SyncLocks}.
	 *
	 * @return the created Task of generic type TASK
	 */
	@NotNull Barrier submit(@NotNull TypeHandler<FUNCTION> typeHandler, @NotNull SyncLock[] locks, @NotNull Barrier... barriers);
	
	default @NotNull TaskCreator<? extends Barrier> taskCreator(@NotNull TypeHandler<FUNCTION> typeHandler) {
		return (locks, barriers) -> submit(typeHandler, locks, barriers);
	}
}
