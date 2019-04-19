package space.engine.buffer.pointer;

import org.jetbrains.annotations.NotNull;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack;
import space.engine.buffer.NioBufferWrapper;
import space.engine.primitive.JavaPrimitives;
import space.engine.primitive.Primitive;

import java.nio.IntBuffer;

import static space.engine.Empties.EMPTY_OBJECT_ARRAY;
import static space.engine.buffer.Allocator.allocatorNoop;

//single
public class PointerBufferInt extends AbstractPointerBuffer<PointerBufferInt> {
	
	public static final Primitive<?> TYPE = JavaPrimitives.INT;
	
	//alloc
	
	/**
	 * Allocates a new {@link PointerBufferInt}. The Contents are undefined. If the {@link PointerBufferInt} is freed, it will free the memory.
	 */
	public static PointerBufferInt malloc(AllocatorStack.Frame allocator) {
		return malloc(allocator, EMPTY_OBJECT_ARRAY);
	}
	
	/**
	 * Allocates a new {@link PointerBufferInt}. The Contents are undefined. If the {@link PointerBufferInt} is freed, it will free the memory.
	 */
	public static PointerBufferInt malloc(Allocator allocator, @NotNull Object[] parents) {
		return new PointerBufferInt(allocator, allocator.malloc(TYPE.bytes), parents);
	}
	
	/**
	 * Allocates a new {@link PointerBufferInt}. The Contents are initialized to 0. If the {@link PointerBufferInt} is freed, it will free the memory.
	 */
	public static PointerBufferInt calloc(AllocatorStack.Frame allocator) {
		return calloc(allocator, EMPTY_OBJECT_ARRAY);
	}
	
	/**
	 * Allocates a new {@link PointerBufferInt}. The Contents are initialized to 0. If the {@link PointerBufferInt} is freed, it will free the memory.
	 */
	public static PointerBufferInt calloc(Allocator allocator, @NotNull Object[] parents) {
		return new PointerBufferInt(allocator, allocator.calloc(TYPE.bytes), parents);
	}
	
	//create
	
	/**
	 * Creates a new {@link PointerBufferInt} from the given address. If the {@link PointerBufferInt} is freed, it <b>WILL</b> free the memory.
	 */
	public static PointerBufferInt create(Allocator allocator, long address, @NotNull Object[] parents) {
		return new PointerBufferInt(allocator, address, parents);
	}
	
	/**
	 * Creates a new {@link PointerBufferInt} from the given address. It will <b>NEVER</b> free the memory but will still throw {@link space.engine.baseobject.exceptions.FreedException} if it is freed.
	 */
	public static PointerBufferInt wrap(long address) {
		return wrap(address, EMPTY_OBJECT_ARRAY);
	}
	
	/**
	 * Creates a new {@link PointerBufferInt} from the given address. It will <b>NEVER</b> free the memory but will still throw {@link space.engine.baseobject.exceptions.FreedException} if it is freed.
	 */
	public static PointerBufferInt wrap(long address, @NotNull Object[] parents) {
		return create(allocatorNoop(), address, parents);
	}
	
	//object
	protected PointerBufferInt(Allocator allocator, long address, @NotNull Object[] parents) {
		super(allocator, address, parents);
	}
	
	@Override
	public Primitive<?> type() {
		return TYPE;
	}
	
	@Override
	public IntBuffer nioBuffer() {
		return NioBufferWrapper.wrapInt(this, 1);
	}
	
	//single
	public int getInt() {
		return UNSAFE.getInt(address());
	}
	
	public void putInt(int b) {
		UNSAFE.putInt(address(), b);
	}
	
	//copy
	@Override
	public void copyInto(PointerBufferInt dest) {
		dest.putInt(this.getInt());
	}
	
	@Override
	public void copyFrom(PointerBufferInt src) {
		this.putInt(src.getInt());
	}
}
