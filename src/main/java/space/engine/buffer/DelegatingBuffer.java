package space.engine.buffer;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.exceptions.FreedException;
import space.engine.freeableStorage.FreeableList;
import space.engine.string.String2D;
import space.engine.sync.barrier.Barrier;

public class DelegatingBuffer<BUFFER extends Buffer> implements Buffer {
	
	public final BUFFER buffer;
	
	public DelegatingBuffer(BUFFER buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public long address() {
		return buffer.address();
	}
	
	@Override
	public long capacity() {
		return buffer.capacity();
	}
	
	@Override
	public void throwIfFreed() throws FreedException {
		buffer.throwIfFreed();
	}
	
	@Override
	public @NotNull Barrier free() {
		return buffer.free();
	}
	
	@Override
	public boolean isFreed() {
		return buffer.isFreed();
	}
	
	@NotNull
	public FreeableList getSubList() {
		return buffer.getSubList();
	}
	
	@Override
	@NotNull
	public String2D dump() {
		return buffer.dump();
	}
}
