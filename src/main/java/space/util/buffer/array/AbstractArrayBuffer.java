package space.util.buffer.array;

import org.jetbrains.annotations.NotNull;
import space.util.annotation.Self;
import space.util.buffer.Allocator;
import space.util.buffer.DelegatingBuffer;
import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;
import space.util.primitive.Primitive;

public abstract class AbstractArrayBuffer<@Self SELF extends AbstractArrayBuffer<SELF>> extends DelegatingBuffer<DirectBuffer> {
	
	public static long calculateLength(DirectBuffer buffer, Primitive<?> type) {
		return type.isAligned ? buffer.capacity() >>> type.shift : buffer.capacity() / type.bytes;
	}
	
	public static class ArrayAllocator<T extends AbstractArrayBuffer<T>> implements Allocator<T> {
		
		public final Allocator<DirectBuffer> alloc;
		public final Primitive<?> primitive;
		public final ArrayCreator<T> creator;
		
		public ArrayAllocator(Allocator<DirectBuffer> alloc, Primitive<?> primitive, ArrayCreator<T> creator) {
			this.alloc = alloc;
			this.primitive = primitive;
			this.creator = creator;
		}
		
		@NotNull
		@Override
		public T create(long address, long length, @NotNull FreeableStorage... parents) {
			return creator.create(alloc.create(address, length, parents), length * primitive.bytes);
		}
		
		@NotNull
		@Override
		public T createNoFree(long address, long length, @NotNull FreeableStorage... parents) {
			return creator.create(alloc.createNoFree(address, length, parents), length * primitive.bytes);
		}
		
		@NotNull
		@Override
		public T malloc(long capacity, @NotNull FreeableStorage... parents) {
			return creator.create(alloc.malloc(capacity, parents), capacity * primitive.bytes);
		}
		
		@NotNull
		@Override
		public T calloc(long capacity, @NotNull FreeableStorage... parents) {
			return creator.create(alloc.calloc(capacity, parents), capacity * primitive.bytes);
		}
	}
	
	@FunctionalInterface
	public interface ArrayCreator<T extends AbstractArrayBuffer<T>> {
		
		T create(DirectBuffer buffer, long length);
	}
	
	public final Primitive<?> type;
	public final long length;
	
	protected AbstractArrayBuffer(DirectBuffer buffer, Primitive<?> type) {
		this(buffer, type, calculateLength(buffer, type));
	}
	
	protected AbstractArrayBuffer(DirectBuffer buffer, Primitive<?> type, long length) {
		super(buffer);
		this.type = type;
		this.length = length;
	}
	
	//offset calc
	public long length() {
		return length;
	}
	
	public long getOffset(long index) {
		return index * type.bytes;
	}
	
	public long getOffset(long index, long offset) {
		return index * type.bytes + offset;
	}
	
	//buffer copy
	public void copyInto(long offset, SELF dest, long destOffset, long length) {
		buffer.copyInto(offset, dest.buffer, destOffset, length);
	}
	
	public void copyFrom(SELF src, long srcOffset, long length, long offset) {
		buffer.copyFrom(src.buffer, srcOffset, length, offset);
	}
	
	//other
	public void clear() {
		buffer.clear();
	}
}
