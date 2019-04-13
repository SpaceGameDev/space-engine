package space.engine.buffer.array;

import org.jetbrains.annotations.NotNull;
import space.engine.buffer.Allocator;
import space.engine.buffer.Buffer;
import space.engine.buffer.NioBufferWrapper;
import space.engine.primitive.Primitive;

import static space.engine.Device.IS_64_BIT;
import static space.engine.buffer.Allocator.allocatorNoop;
import static space.engine.primitive.Primitives.POINTER;
import static sun.misc.Unsafe.*;

public class ArrayBufferPointer extends AbstractArrayBuffer<ArrayBufferPointer> {
	
	public static final Primitive<?> TYPE = POINTER;
	
	//alloc
	
	/**
	 * Allocates a new {@link ArrayBufferPointer} of length. The Contents are undefined. If the {@link ArrayBufferPointer} is freed, it will free the memory.
	 */
	public static ArrayBufferPointer malloc(Allocator allocator, long length, @NotNull Object[] parents) {
		return new ArrayBufferPointer(allocator, allocator.malloc(length * TYPE.bytes), length, parents);
	}
	
	/**
	 * Allocates a new {@link ArrayBufferPointer} of length. The Contents are initialized to 0. If the {@link ArrayBufferPointer} is freed, it will free the memory.
	 */
	public static ArrayBufferPointer calloc(Allocator allocator, long length, @NotNull Object[] parents) {
		return new ArrayBufferPointer(allocator, allocator.calloc(length * TYPE.bytes), length, parents);
	}
	
	//create
	
	/**
	 * Creates a new {@link ArrayBufferPointer} from the given address and length. If the {@link ArrayBufferPointer} is freed, it <b>WILL</b> free the memory.
	 */
	public static ArrayBufferPointer create(Allocator allocator, long address, long length, @NotNull Object[] parents) {
		return new ArrayBufferPointer(allocator, address, length, parents);
	}
	
	/**
	 * Creates a new {@link ArrayBufferPointer} from the given address and length. It will <b>NEVER</b> free the memory.
	 */
	public static ArrayBufferPointer wrap(long address, long length, @NotNull Object[] parents) {
		return create(allocatorNoop(), address, length, parents);
	}
	
	//object
	protected ArrayBufferPointer(Allocator allocator, long address, long length, @NotNull Object[] parents) {
		super(allocator, address, length, parents);
	}
	
	@Override
	public Primitive<?> type() {
		return TYPE;
	}
	
	@Override
	public java.nio.Buffer nioBuffer() {
		return NioBufferWrapper.wrapByte(this, sizeOf());
	}
	
	//single
	public long getPointer(long index) {
		Buffer.checkIndex(index, this.length);
		return UNSAFE.getAddress(address() + type().multiply(index));
	}
	
	public void putPointer(long index, Buffer b) {
		putPointer(index, b.address());
	}
	
	public void putPointer(long index, java.nio.Buffer b) {
		putPointer(index, NioBufferWrapper.getAddress(b));
	}
	
	public void putPointer(long index, long b) {
		Buffer.checkIndex(index, this.length);
		UNSAFE.putAddress(address() + type().multiply(index), b);
	}
	
	//array
	public void copyInto(long[] dest) {
		copyInto(0, dest, 0, dest.length);
	}
	
	public void copyInto(long srcIndex, long[] dest, int destIndex, int length) {
		Buffer.checkFromIndexSize(srcIndex, length, this.length);
		Buffer.checkFromIndexSize(destIndex, length, dest.length);
		
		if (IS_64_BIT) {
			UNSAFE.copyMemory(null, address() + type().multiply(srcIndex), dest, ARRAY_LONG_BASE_OFFSET + destIndex * ARRAY_LONG_INDEX_SCALE, type().multiply(length));
		} else {
			long srcAddress = address() + type().multiply(srcIndex);
			for (int i = 0; i < length; i++)
				dest[destIndex + i] = (long) UNSAFE.getInt(srcAddress + i) & 0xFFFF_FFFFL;
		}
	}
	
	public void copyFrom(long[] src) {
		copyFrom(src, 0, 0, src.length);
	}
	
	public void copyFrom(long[] src, int srcIndex, long destIndex, int length) {
		Buffer.checkFromIndexSize(srcIndex, length, src.length);
		Buffer.checkFromIndexSize(destIndex, length, this.length);
		
		if (IS_64_BIT) {
			UNSAFE.copyMemory(src, ARRAY_LONG_BASE_OFFSET + srcIndex * ARRAY_LONG_INDEX_SCALE, null, address() + type().multiply(destIndex), type().multiply(length));
		} else {
			long destAddress = address() + type().multiply(destIndex);
			for (int i = 0; i < length; i++)
				UNSAFE.putInt(destAddress + i, (int) src[srcIndex + i]);
		}
	}
}