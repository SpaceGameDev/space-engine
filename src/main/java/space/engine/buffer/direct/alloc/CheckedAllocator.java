package space.engine.buffer.direct.alloc;

import org.jetbrains.annotations.NotNull;
import space.engine.buffer.Allocator;
import space.engine.buffer.direct.CheckedDirectBuffer;
import space.engine.buffer.direct.DirectBuffer;

public class CheckedAllocator implements Allocator<DirectBuffer> {
	
	public Allocator<DirectBuffer> alloc;
	
	public CheckedAllocator(Allocator<DirectBuffer> alloc) {
		this.alloc = alloc;
	}
	
	@NotNull
	@Override
	public DirectBuffer create(long address, long capacity, @NotNull Object[] parents) {
		return new CheckedDirectBuffer(alloc.create(address, capacity, parents));
	}
	
	@NotNull
	@Override
	public DirectBuffer createNoFree(long address, long capacity, @NotNull Object[] parents) {
		return new CheckedDirectBuffer(alloc.createNoFree(address, capacity, parents));
	}
	
	@NotNull
	@Override
	public DirectBuffer malloc(long capacity, @NotNull Object[] parents) {
		return new CheckedDirectBuffer(alloc.malloc(capacity, parents));
	}
	
	@NotNull
	@Override
	public DirectBuffer calloc(long capacity, @NotNull Object[] parents) {
		return new CheckedDirectBuffer(alloc.calloc(capacity, parents));
	}
}
