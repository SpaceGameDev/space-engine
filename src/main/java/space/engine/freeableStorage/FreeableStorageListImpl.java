package space.engine.freeableStorage;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.Freeable;
import space.engine.baseobject.ToString;
import space.engine.freeableStorage.FreeableStorageList.Entry;
import space.engine.string.toStringHelper.ToStringHelper;
import space.engine.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

/**
 * Use {@link FreeableStorageListImpl#createList(int)} to create a List.
 *
 * @implNote This is just a Base-Object for the inner classes:
 * {@link FreeableStorageListImpl.List List} and it's {@link Entry Entry}.
 */
public abstract class FreeableStorageListImpl implements Freeable {
	
	//static creator
	public static FreeableStorageList createList(int freePriority) {
		return new List(freePriority);
	}
	
	//object fields
	/**
	 * null: entry is freed
	 * notnull: entry is NOT freed
	 */
	public FreeableStorageListImpl prev;
	
	/**
	 * does not affect free status
	 */
	public FreeableStorageListImpl next;
	
	//object methods
	
	/**
	 * Inserts an Entry into the List before this Entry
	 *
	 * @param entry Entry to insert
	 */
	protected void insertBefore(FreeableStorageListImpl entry) {
		prev = entry.prev;
		next = entry;
		prev.next = this;
		next.prev = this;
	}
	
	/**
	 * removes an Entry from the List
	 *
	 * @param quickRemove true causes the list not to be reconnected, just the entry freed
	 */
	@SuppressWarnings("ConstantConditions")
	protected void remove(boolean quickRemove) {
		if (!quickRemove) {
			prev.next = next;
			next.prev = prev;
			next = null;
		}
		prev = null;
	}
	
	@Override
	public boolean isFreed() {
		return prev == null;
	}
	
	/**
	 * The List Implementation. <br>
	 * This is a looping linked list, so next and prev cannot be null, except it's not in the list (or freed).
	 */
	private static class List extends FreeableStorageListImpl implements FreeableStorageList, ToString {
		
		public int freePriority;
		
		private List(int freePriority) {
			this.freePriority = freePriority;
			prev = this;
			next = this;
		}
		
		@NotNull
		@Override
		public FreeableStorageListImpl.List.Entry insert(@NotNull FreeableStorage storage) {
			return new Entry(storage);
		}
		
		@Override
		public int freePriority() {
			return freePriority;
		}
		
		@Override
		public synchronized void free() {
			if (isFreed())
				return;
			remove(true);
			
			FreeableStorageListImpl next = this.next;
			while (next != this) {
				next.free();
				next = next.next;
			}
		}
		
		@NotNull
		@Override
		public <T> T toTSH(@NotNull ToStringHelper<T> api) {
			int size = 0;
			FreeableStorageListImpl next = this;
			while ((next = next.next) != this)
				size++;
			
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("size", size);
			tsh.add("freePriority", this.freePriority);
			return tsh.build();
		}
		
		/**
		 * This is the Entry class of the List
		 *
		 * @see FreeableStorageListImpl.List
		 */
		private class Entry extends FreeableStorageListImpl implements FreeableStorageList.Entry {
			
			final FreeableStorage freeableStorage;
			
			public Entry(FreeableStorage freeableStorage) {
				this.freeableStorage = freeableStorage;
				synchronized (List.this) {
					insertBefore(List.this);
				}
			}
			
			@Override
			public void remove() {
				synchronized (List.this) {
					remove(List.this.isFreed());
				}
			}
			
			//free
			@Override
			public void free() {
				synchronized (List.this) {
					if (isFreed())
						return;
					remove(List.this.isFreed());
				}
				
				freeableStorage.free();
			}
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
}
