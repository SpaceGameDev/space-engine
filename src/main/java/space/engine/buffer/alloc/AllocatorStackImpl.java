package space.engine.buffer.alloc;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.buffer.AbstractBuffer.Storage;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack;
import space.engine.freeableStorage.FreeableList;
import space.engine.sync.barrier.Barrier;

public class AllocatorStackImpl extends AllocatorStack {
	
	protected final @NotNull Allocator allocator;
	protected final @NotNull Storage storage;
	protected final long capacity;
	
	protected @Nullable Frame current;
	
	public AllocatorStackImpl(@NotNull Allocator allocator, long capacity, @NotNull Object[] parents) {
		this.allocator = allocator;
		this.capacity = capacity;
		storage = new Storage(this, allocator, allocator.malloc(capacity), parents);
	}
	
	@Override
	public Frame frame() {
		return current = new Frame(current);
	}
	
	public class Frame extends AllocatorStack.Frame {
		
		protected @Nullable Frame prev;
		protected long pointerStack;
		private @Nullable FreeableList subList;
		
		public Frame(@Nullable Frame prev) {
			this.prev = prev;
			this.pointerStack = prev != null ? prev.pointerStack : 0;
		}
		
		public void checkThisIsTopFrame() {
			if (current != this)
				throw new RuntimeException("this frame is not the current frame");
		}
		
		@Override
		public long malloc(long sizeOf) {
			checkThisIsTopFrame();
			long oldPointerStack = pointerStack;
			//align to 8
			pointerStack += (sizeOf + 0x7) & ~0x7;
			if (pointerStack > capacity)
				throw new OutOfMemoryError("Stack overflow!");
			return storage.getAddress() + oldPointerStack;
		}
		
		@Override
		public long calloc(long sizeOf) {
			long address = malloc(sizeOf);
			UNSAFE.setMemory(address, sizeOf, (byte) 0);
			return address;
		}
		
		@Override
		public void free(long address) {
		
		}
		
		@Override
		public @NotNull Barrier free() {
			if (current != this)
				throw new RuntimeException("popped frame is not current frame!");
			current = prev;
			prev = null;
			return subList != null ? subList.free() : Barrier.ALWAYS_TRIGGERED_BARRIER;
		}
		
		@Override
		public boolean isFreed() {
			return current == this;
		}
		
		@Override
		public @NotNull FreeableList getSubList() {
			FreeableList subList = this.subList;
			if (subList != null)
				return subList;
			return this.subList = new FreeableList();
		}
	}
}
