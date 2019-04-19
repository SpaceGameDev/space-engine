package space.engine.buffer.array;

import org.jetbrains.annotations.NotNull;
import space.engine.buffer.Allocator;
import space.engine.buffer.Buffer;
import space.engine.buffer.NioBufferWrapper;
import space.engine.primitive.JavaPrimitives;
import space.engine.primitive.Primitive;

import java.nio.ByteBuffer;

import static space.engine.buffer.Allocator.allocatorNoop;
import static sun.misc.Unsafe.*;

public class ArrayBufferByte extends AbstractArrayBuffer<ArrayBufferByte> {
	
	public static final Primitive<?> TYPE = JavaPrimitives.BYTE;
	
	//alloc
	
	/**
	 * Allocates a new {@link ArrayBufferByte} of length. The Contents are undefined. If the {@link ArrayBufferByte} is freed, it will free the memory.
	 */
	public static ArrayBufferByte malloc(Allocator allocator, long length, @NotNull Object[] parents) {
		return new ArrayBufferByte(allocator, allocator.malloc(length * TYPE.bytes), length, parents);
	}
	
	/**
	 * Allocates a new {@link ArrayBufferByte} of length. The Contents are initialized to 0. If the {@link ArrayBufferByte} is freed, it will free the memory.
	 */
	public static ArrayBufferByte calloc(Allocator allocator, long length, @NotNull Object[] parents) {
		return new ArrayBufferByte(allocator, allocator.calloc(length * TYPE.bytes), length, parents);
	}
	
	//create
	
	/**
	 * Creates a new {@link ArrayBufferByte} from the given address and length. If the {@link ArrayBufferByte} is freed, it <b>WILL</b> free the memory.
	 */
	public static ArrayBufferByte create(Allocator allocator, long address, long length, @NotNull Object[] parents) {
		return new ArrayBufferByte(allocator, address, length, parents);
	}
	
	/**
	 * Creates a new {@link ArrayBufferByte} from the given address and length. It will <b>NEVER</b> free the memory.
	 */
	public static ArrayBufferByte wrap(long address, long length, @NotNull Object[] parents) {
		return create(allocatorNoop(), address, length, parents);
	}
	
	//object
	protected ArrayBufferByte(Allocator allocator, long address, long length, @NotNull Object[] parents) {
		super(allocator, address, length, parents);
	}
	
	@Override
	public Primitive<?> type() {
		return TYPE;
	}
	
	@Override
	public ByteBuffer nioBuffer() {
		return NioBufferWrapper.wrapByte(this, length);
	}
	
	//single
	public byte getByte(long index) {
		Buffer.checkIndex(index, this.length);
		return UNSAFE.getByte(address() + type().multiply(index));
	}
	
	public void putByte(long index, byte b) {
		Buffer.checkIndex(index, this.length);
		UNSAFE.putByte(address() + type().multiply(index), b);
	}
	
	//array
	public void copyInto(byte[] dest) {
		copyInto(0, dest, 0, dest.length);
	}
	
	public void copyInto(long srcIndex, byte[] dest, int destIndex, int length) {
		Buffer.checkFromIndexSize(srcIndex, length, this.length);
		Buffer.checkFromIndexSize(destIndex, length, dest.length);
		UNSAFE.copyMemory(null, address() + type().multiply(srcIndex), dest, ARRAY_BYTE_BASE_OFFSET + destIndex * ARRAY_BYTE_INDEX_SCALE, type().multiply(length));
	}
	
	public void copyFrom(byte[] src) {
		copyFrom(src, 0, 0, src.length);
	}
	
	public void copyFrom(byte[] src, int srcIndex, long destIndex, int length) {
		Buffer.checkFromIndexSize(srcIndex, length, src.length);
		Buffer.checkFromIndexSize(destIndex, length, this.length);
		UNSAFE.copyMemory(src, ARRAY_BYTE_BASE_OFFSET + srcIndex * ARRAY_BYTE_INDEX_SCALE, null, address() + type().multiply(destIndex), type().multiply(length));
	}
}