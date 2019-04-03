package space.engine.buffer.pointer;

import org.jetbrains.annotations.NotNull;
import space.engine.annotation.Self;
import space.engine.buffer.Allocator;
import space.engine.buffer.Buffer;
import space.engine.buffer.DelegatingBuffer;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.primitive.Primitive;

public class AbstractPointerBuffer<@Self SELF extends AbstractPointerBuffer<SELF>> extends DelegatingBuffer<DirectBuffer> {
	
	public static long calculateLength(Buffer buffer, Primitive<?> type) {
		return type.isAligned ? buffer.capacity() >>> type.shift : buffer.capacity() / type.bytes;
	}
	
	//checkCapacity
	protected static <T extends Buffer> T checkCapacity(T buffer, Primitive<?> type) {
		checkCapacity(calculateLength(buffer, type));
		return buffer;
	}
	
	protected static void checkCapacity(long length) {
		if (length != 1)
			throw new IllegalArgumentException("length " + length + " != 1");
	}
	
	public static class PointerAllocator<T extends AbstractPointerBuffer<T>> implements Allocator<T> {
		
		public final Allocator<DirectBuffer> alloc;
		public final Primitive<?> primitive;
		public final PointerCreator<T> creator;
		
		public PointerAllocator(Allocator<DirectBuffer> alloc, Primitive<?> primitive, PointerCreator<T> creator) {
			this.alloc = alloc;
			this.primitive = primitive;
			this.creator = creator;
		}
		
		//methods with capacity
		@NotNull
		@Override
		public T create(long address, long capacity, @NotNull Object[] parents) {
			checkCapacity(capacity);
			return create(address, parents);
		}
		
		@NotNull
		@Override
		public T createNoFree(long address, long capacity, @NotNull Object[] parents) {
			checkCapacity(capacity);
			return createNoFree(address, parents);
		}
		
		@NotNull
		@Override
		public T malloc(long capacity, @NotNull Object[] parents) {
			checkCapacity(capacity);
			return malloc(parents);
		}
		
		@NotNull
		@Override
		public T calloc(long capacity, @NotNull Object[] parents) {
			checkCapacity(capacity);
			return calloc(parents);
		}
		
		//methods without capacity
		@NotNull
		public T create(long address, @NotNull Object[] parents) {
			return creator.create(alloc.create(address, primitive.bytes, parents));
		}
		
		@NotNull
		public T createNoFree(long address, @NotNull Object[] parents) {
			return creator.create(alloc.createNoFree(address, primitive.bytes, parents));
		}
		
		@NotNull
		public T malloc(@NotNull Object[] parents) {
			return creator.create(alloc.malloc(primitive.bytes, parents));
		}
		
		@NotNull
		public T calloc(@NotNull Object[] parents) {
			return creator.create(alloc.calloc(primitive.bytes, parents));
		}
	}
	
	@FunctionalInterface
	public interface PointerCreator<T extends AbstractPointerBuffer<T>> {
		
		T create(DirectBuffer buffer);
	}
	
	public final Primitive<?> type;
	
	public AbstractPointerBuffer(DirectBuffer buffer, Primitive<?> type) {
		super(checkCapacity(buffer, type));
		this.type = type;
	}
	
	//buffer copy
	public void copyInto(SELF dest) {
		buffer.copyInto(0, dest.buffer, 0, type.bytes);
	}
	
	public void copyFrom(SELF src) {
		buffer.copyFrom(src.buffer, 0, type.bytes, 0);
	}
	
	//other
	public void clear() {
		buffer.clear();
	}
}
