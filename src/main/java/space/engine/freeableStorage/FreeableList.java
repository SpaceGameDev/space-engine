package space.engine.freeableStorage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.baseobject.exceptions.FreedException;

public class FreeableList implements Freeable {
	
	private boolean isFreed = false;
	private @Nullable Entry first;
	
	public synchronized FreeableList.Entry insert(@NotNull Freeable storage) {
		if (isFreed)
			throw new FreedException(this);
		
		Entry entry = new Entry(storage);
		if (first != null) {
			entry.next = first;
			first.prev = entry;
		}
		first = entry;
		return entry;
	}
	
	@Override
	public synchronized void free() {
		if (isFreed)
			return;
		isFreed = true;
		
		if (first == null)
			return;
		
		//free entries
		Entry next = first;
		while (next != null) {
			next.freeable.free();
			next = next.next;
		}
		
		//make entries unreachable
		first = null;
	}
	
	@Override
	public synchronized boolean isFreed() {
		return isFreed;
	}
	
	@Override
	public @NotNull FreeableList getSubList() {
		return this;
	}
	
	public class Entry {
		
		public final Freeable freeable;
		private @Nullable Entry prev;
		private @Nullable Entry next;
		
		public Entry(Freeable freeable) {
			this.freeable = freeable;
		}
		
		/**
		 * removes an Entry from the List
		 */
		public void remove() {
			synchronized (FreeableList.this) {
				if (isFreed || (prev == null && next == null))
					return;
				
				if (prev != null)
					prev.next = next;
				if (next != null)
					next.prev = prev;
				if (first == this)
					first = next;
				
				next = null;
				prev = null;
			}
		}
	}
}
