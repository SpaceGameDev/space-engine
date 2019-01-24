package space.engine.buffer.direct.alloc;

import org.jetbrains.annotations.NotNull;
import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.buffer.direct.UnsafeDirectBuffer;
import space.engine.buffer.direct.UnsafeNonFreeDirectBuffer;
import space.engine.freeableStorage.FreeableStorage;

public class UnsafeAllocator implements Allocator<DirectBuffer> {
	
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
