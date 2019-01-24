package space.engine.buffer;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.exceptions.FreedException;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.freeableStorage.FreeableStorageList;
import space.engine.string.String2D;

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
	@NotNull
	public FreeableStorage getStorage() {
		return buffer.getStorage();
	}
	
	@Override
	public void free() {
		buffer.free();
	}
	
	@Override
	public boolean isFreed() {
		return buffer.isFreed();
	}
	
	@Override
	public int freePriority() {
		return buffer.freePriority();
	}
	
	@Override
	@NotNull
	public FreeableStorageList getSubList() {
		return buffer.getSubList();
	}
	
	@Override
	@NotNull
	public String2D dump() {
		return buffer.dump();
	}
}
