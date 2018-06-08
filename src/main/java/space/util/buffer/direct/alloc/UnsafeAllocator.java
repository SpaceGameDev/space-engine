package space.util.buffer.direct.alloc;

import org.jetbrains.annotations.NotNull;
import space.util.buffer.Allocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.buffer.direct.UnsafeDirectBuffer;
import space.util.buffer.direct.UnsafeNonFreeDirectBuffer;
import space.util.freeableStorage.FreeableStorage;

public class UnsafeAllocator implements Allocator {
	
	@NotNull
	@Override
	public DirectBuffer create(long address, long capacity, @NotNull FreeableStorage... parents) {
		return new UnsafeDirectBuffer(address, capacity, parents);
	}
	
	@NotNull
	@Override
	public DirectBuffer createNoFree(long address, long capacity, @NotNull FreeableStorage... parents) {
		return new UnsafeNonFreeDirectBuffer(address, capacity, parents);
	}
	
	@NotNull
	@Override
	public DirectBuffer malloc(long capacity, @NotNull FreeableStorage... parents) {
		return new UnsafeDirectBuffer(capacity, parents);
	}
	
	@NotNull
	@Override
	public DirectBuffer calloc(long capacity, @NotNull FreeableStorage... parents) {
		DirectBuffer buffer = malloc(capacity, parents);
		buffer.clear();
		return buffer;
	}
}
