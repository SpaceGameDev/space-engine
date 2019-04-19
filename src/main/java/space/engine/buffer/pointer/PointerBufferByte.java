package space.engine.buffer.pointer;

import org.jetbrains.annotations.NotNull;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack;
import space.engine.buffer.NioBufferWrapper;
import space.engine.primitive.JavaPrimitives;
import space.engine.primitive.Primitive;

import java.nio.ByteBuffer;

import static space.engine.Empties.EMPTY_OBJECT_ARRAY;
import static space.engine.buffer.Allocator.allocatorNoop;

//single
public class PointerBufferByte extends AbstractPointerBuffer<PointerBufferByte> {
	
	public static final Primitive<?> TYPE = JavaPrimitives.BYTE;
	
	//alloc
	
	/**
	 * Allocates a new {@link PointerBufferByte}. The Contents are undefined. If the {@link PointerBufferByte} is freed, it will free the memory.
	 */
	public static PointerBufferByte malloc(AllocatorStack.Frame allocator) {
		return malloc(allocator, EMPTY_OBJECT_ARRAY);
	}
	
	/**
	 * Allocates a new {@link PointerBufferByte}. The Contents are undefined. If the {@link PointerBufferByte} is freed, it will free the memory.
	 */
	public static PointerBufferByte malloc(Allocator allocator, @NotNull Object[] parents) {
		return new PointerBufferByte(allocator, allocator.malloc(TYPE.bytes), parents);
	}
	
	/**
	 * Allocates a new {@link PointerBufferByte}. The Contents are initialized to 0. If the {@link PointerBufferByte} is freed, it will free the memory.
	 */
	public static PointerBufferByte calloc(AllocatorStack.Frame allocator) {
		return calloc(allocator, EMPTY_OBJECT_ARRAY);
	}
	
	/**
	 * Allocates a new {@link PointerBufferByte}. The Contents are initialized to 0. If the {@link PointerBufferByte} is freed, it will free the memory.
	 */
	public static PointerBufferByte calloc(Allocator allocator, @NotNull Object[] parents) {
		return new PointerBufferByte(allocator, allocator.calloc(TYPE.bytes), parents);
	}
	
	//create
	
	/**
	 * Creates a new {@link PointerBufferByte} from the given address. If the {@link PointerBufferByte} is freed, it <b>WILL</b> free the memory.
	 */
	public static PointerBufferByte create(Allocator allocator, long address, @NotNull Object[] parents) {
		return new PointerBufferByte(allocator, address, parents);
	}
	
	/**
	 * Creates a new {@link PointerBufferByte} from the given address. It will <b>NEVER</b> free the memory but will still throw {@link space.engine.baseobject.exceptions.FreedException} if it is freed.
	 */
	public static PointerBufferByte wrap(long address) {
		return wrap(address, EMPTY_OBJECT_ARRAY);
	}
	
	/**
	 * Creates a new {@link PointerBufferByte} from the given address. It will <b>NEVER</b> free the memory but will still throw {@link space.engine.baseobject.exceptions.FreedException} if it is freed.
	 */
	public static PointerBufferByte wrap(long address, @NotNull Object[] parents) {
		return create(allocatorNoop(), address, parents);
	}
	
	//object
	protected PointerBufferByte(Allocator allocator, long address, @NotNull Object[] parents) {
		super(allocator, address, parents);
	}
	
	@Override
	public Primitive<?> type() {
		return TYPE;
	}
	
	@Override
	public ByteBuffer nioBuffer() {
		return NioBufferWrapper.wrapByte(this, 1);
	}
	
	//single
	public byte getByte() {
		return UNSAFE.getByte(address());
	}
	
	public void putByte(byte b) {
		UNSAFE.putByte(address(), b);
	}
	
	//copy
	@Override
	public void copyInto(PointerBufferByte dest) {
		dest.putByte(this.getByte());
	}
	
	@Override
	public void copyFrom(PointerBufferByte src) {
		this.putByte(src.getByte());
	}
}
