package space.util.buffer.direct.alloc;

import org.jetbrains.annotations.NotNull;
import space.util.buffer.Allocator;
import space.util.buffer.direct.CheckedDirectBuffer;
import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;

public class CheckedAllocator implements Allocator<DirectBuffer> {
	
	public Allocator<DirectBuffer> alloc;
	
	public CheckedAllocator(Allocator<DirectBuffer> alloc) {
		this.alloc = alloc;
	}
	
	@NotNull
	@Override
	public DirectBuffer create(long address, long capacity, @NotNull FreeableStorage... parents) {
		return new CheckedDirectBuffer(alloc.create(address, capacity, parents));
	}
	
	@NotNull
	@Override
	public DirectBuffer createNoFree(long address, long capacity, @NotNull FreeableStorage... parents) {
		return new CheckedDirectBuffer(alloc.createNoFree(address, capacity, parents));
	}
	
	@NotNull
	@Override
	public DirectBuffer malloc(long capacity, @NotNull FreeableStorage... parents) {
		return new CheckedDirectBuffer(alloc.malloc(capacity, parents));
	}
	
	@NotNull
	@Override
	public DirectBuffer calloc(long capacity, @NotNull FreeableStorage... parents) {
		return new CheckedDirectBuffer(alloc.calloc(capacity, parents));
	}
}
