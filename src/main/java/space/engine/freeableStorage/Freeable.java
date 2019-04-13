package space.engine.freeableStorage;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.exceptions.FreedException;
import space.engine.event.SequentialEventBuilder;
import space.engine.event.typehandler.TypeHandlerFirstFunction;
import space.engine.sync.Tasks.FunctionWithDelay;
import space.engine.sync.barrier.Barrier;

/**
 * {@link Freeable} is an advanced native or other resource free-ing system.
 * To use it you need a front-end Object and (mostly as a STATIC inner class) a Storage-Object (this class) for values required for eg. deallocation.
 * When creating a Storage you have to extend one of the three types of {@link Freeable}:
 * <ul>
 * <li>{@link FreeableStorage} - Phantom - {@link java.lang.ref.PhantomReference#get()} always returns null &emsp;&emsp;<b><- use this for native resource deallocation</b></li>
 * <li>{@link FreeableStorageWeak} - Weak - {@link java.lang.ref.WeakReference#get()} returns the front-end Object if not already deallocated &emsp;&emsp;<b><- use this for java cleanup stuff</b></li>
 * <li>{@link FreeableStorageSoft} - Soft - {@link java.lang.ref.SoftReference#get()} always returns the front-end object &emsp;&emsp;<b><- use this for out-of-memory / cache setups (in java)</b></li>
 * <li>See the different java.lang.ref Classes and the Package-Summary for more info.</li>
 * </ul>
 * <p>
 * They handle everything for you, from cleaning up hooks at your parents, freeing children first (explained later) and even the detection of already being freed.
 * You only have to implement the {@link FreeableStorage#handleFree()} Method and actually free your resources (eg. {@link sun.misc.Unsafe#freeMemory(long) Unsafe.freeMemory(long)}).
 * Creating the Storage-Object requires you to give it the front-end Object.
 * Note that <b>having any Reference from the Storage to the front-end Object will cause it not to get cleaned up!</b>
 * So make sure that if you are using Storage as an inner class it is a static class.
 * <p>
 * A {@link Freeable} also ALWAYS has "parents", which you can specify in the constructor.
 * When a parent is freed, their children are being freed first, then the parent. This allows for eg. Instance having multiple Buffers setups to free the Buffers first and then the instance.
 * Everything should be rooted sometime into the global {@link Freeable#ROOT_LIST}, which will be cleaned up automatically then the JVM starts the Shutdown threads.<br>
 * If your are using the SpaceEngine you should root it into your Side first. (which is then rooted into {@link Freeable#ROOT_LIST}).
 * You can also get the {@link FreeableList} containing all children of a Storage-Object with {@link Freeable#getFreeable(Object)}
 * <p>
 * If you want an {@link Freeable} Object in between without any free-ing capabilities, you can use {@link Freeable#createDummy(Object...)} to do so.
 * Have a look into {@link FreeableStorageCleaner} to setup a {@link space.engine.logger.Logger} for cleanup information or other things cleanup related.
 */
public interface Freeable {
	
	FreeableList ROOT_LIST = new FreeableList();
	SequentialEventBuilder<FunctionWithDelay<Object, Freeable>> GET_SUBLIST_EVENT = new SequentialEventBuilder<>();
	
	/**
	 * Frees the resource
	 *
	 * @return a Barrier when the recourse will be freed.
	 * Use {@link Barrier#ALWAYS_TRIGGERED_BARRIER} if it is already freed or freed immediately.
	 */
	@NotNull Barrier free();
	
	/**
	 * Checks if the resource was already freed. Mostly used in context with {@link #throwIfFreed()}.
	 * If your Object cannot be freed always return false.
	 *
	 * @return true if already freed
	 */
	boolean isFreed();
	
	/**
	 * Throws an {@link FreedException} if the resource was already freed
	 *
	 * @throws FreedException if already freed
	 */
	default void throwIfFreed() throws FreedException {
		if (isFreed())
			throw new FreedException(this);
	}
	
	/**
	 * Gets the subList of this {@link Freeable}
	 *
	 * @return a {@link FreeableList} to hook into
	 */
	@NotNull FreeableList getSubList();
	
	//static
	static Barrier freeObject(@NotNull Object object) {
		return getFreeable(object).free();
	}
	
	static @NotNull Freeable getFreeable(@NotNull Object object) {
		if (object instanceof Freeable)
			return (Freeable) object;
		
		TypeHandlerFirstFunction<Object, Freeable> handler = new TypeHandlerFirstFunction<>(object);
		GET_SUBLIST_EVENT.runImmediately(handler);
		Freeable result = handler.result();
		if (result != null)
			return result;
		throw new RuntimeException("Object " + object + " could not be resolved to a Freeable!");
	}
	
	/**
	 * Creates a new {@link Freeable} which won't free anything by itself, but still frees it's Children. <br>
	 * It can be used as a Layer in between multiple {@link Freeable FreeableStorages}.
	 *
	 * @param parents the parents it should have
	 * @return a new dummy {@link Freeable}
	 */
	static @NotNull FreeableStorage createDummy(@NotNull Object[] parents) {
		return new FreeableStorage(null, parents) {
			@Override
			protected @NotNull Barrier handleFree() {
				return Barrier.ALWAYS_TRIGGERED_BARRIER;
			}
		};
	}
	
	/**
	 * A Simple implementation if a call is using a backend {@link Freeable} for releasing resources.
	 */
	interface FreeableWrapper extends Freeable {
		
		@NotNull Freeable getStorage();
		
		@Override
		default @NotNull Barrier free() {
			return getStorage().free();
		}
		
		@Override
		default boolean isFreed() {
			return getStorage().isFreed();
		}
		
		@NotNull
		@Override
		default FreeableList getSubList() {
			return getStorage().getSubList();
		}
	}
}
