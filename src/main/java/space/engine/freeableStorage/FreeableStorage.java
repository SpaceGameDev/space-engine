package space.engine.freeableStorage;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.Freeable;

/**
 * {@link FreeableStorage} is an advanced native or other resource free-ing system.
 * To use it you need a front-end Object and (mostly as a STATIC inner class) a Storage-Object (this class) for values required for eg. deallocation.
 * When creating a Storage you have to extend one of the three types of {@link FreeableStorage}:
 * <ul>
 * <li>{@link FreeableStorageImpl} - Phantom - {@link java.lang.ref.PhantomReference#get()} always returns null &emsp;&emsp;<b><- use this for native resource deallocation</b></li>
 * <li>{@link FreeableStorageWeak} - Weak - {@link java.lang.ref.WeakReference#get()} returns the front-end Object if not already deallocated &emsp;&emsp;<b><- use this for java cleanup stuff</b></li>
 * <li>{@link FreeableStorageSoft} - Soft - {@link java.lang.ref.SoftReference#get()} always returns the front-end object &emsp;&emsp;<b><- use this for out-of-memory / cache setups (in java)</b></li>
 * <li>See the different java.lang.ref Classes and the Package-Summary for more info.</li>
 * </ul>
 * <p>
 * They handle everything for you, from cleaning up hooks at your parents, freeing children first (explained later) and even the detection of already being freed.
 * You only have to implement the {@link FreeableStorageImpl#handleFree()} Method and actually free your resources (eg. {@link sun.misc.Unsafe#freeMemory(long) Unsafe.freeMemory(long)}).
 * Creating the Storage-Object requires you to give it the front-end Object.
 * Note that <b>having any Reference from the Storage to the front-end Object will cause it not to get cleaned up!</b>
 * So make sure that if you are using Storage as an inner class it is a static class.
 * <p>
 * A {@link FreeableStorage} also ALWAYS has "parents", which you can specify in the constructor.
 * When a parent is freed, their children are being freed first, then the parent. This allows for eg. Instance having multiple Buffers setups to free the Buffers first and then the instance.
 * Everything should be rooted sometime into the global {@link FreeableStorage#ROOT_LIST}, which will be cleaned up automatically then the JVM starts the Shutdown threads.<br>
 * If your are using the SpaceEngine you should root it into your Side first. (which is then rooted into {@link FreeableStorage#ROOT_LIST}).
 * You can also get the {@link FreeableStorageList} containing all children of a Storage-Object with {@link FreeableStorage#getSubList()}
 * <p>
 * If you want an {@link FreeableStorage} Object in between without any free-ing capabilities, you can use {@link FreeableStorage#createDummy(FreeableStorage...)} to do so.
 * Have a look into {@link FreeableStorageCleaner} to setup a {@link space.engine.logger.Logger} for cleanup information or other things cleanup related.
 */
public interface FreeableStorage extends Freeable {
	
	FreeableStorageList ROOT_LIST = FreeableStorageListImpl.createList();
	
	/**
	 * Gets the subList of this {@link FreeableStorage}
	 *
	 * @return a {@link FreeableStorageList} to hook into
	 */
	@NotNull FreeableStorageList getSubList();
	
	/**
	 * Creates a new {@link FreeableStorage} which won't free anything by itself, but still frees it's Children. <br>
	 * It can be used as a Layer in between multiple {@link FreeableStorage FreeableStorages}.
	 *
	 * @param parents the parents it should have
	 * @return a new dummy {@link FreeableStorage}
	 */
	static @NotNull FreeableStorageImpl createDummy(@NotNull FreeableStorage... parents) {
		return new FreeableStorageImpl(null, parents) {
			@Override
			protected void handleFree() {
			
			}
		};
	}
}
