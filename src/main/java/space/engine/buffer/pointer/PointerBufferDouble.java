package space.engine.buffer.pointer;

import org.jetbrains.annotations.NotNull;
import space.engine.buffer.Allocator;
import space.engine.buffer.NioBufferWrapper;
import space.engine.primitive.JavaPrimitives;
import space.engine.primitive.Primitive;

import java.nio.DoubleBuffer;

import static space.engine.buffer.Allocator.allocatorNoop;

//single
public class PointerBufferDouble extends AbstractPointerBuffer<PointerBufferDouble> {
	
	public static final Primitive<?> TYPE = JavaPrimitives.DOUBLE;
	
	//alloc
	
	/**
	 * Allocates a new {@link PointerBufferDouble}. The Contents are undefined. If the {@link PointerBufferDouble} is freed, it will free the memory.
	 */
	public static PointerBufferDouble malloc(Allocator allocator, @NotNull Object[] parents) {
		return new PointerBufferDouble(allocator, allocator.malloc(TYPE.bytes), parents);
	}
	
	/**
	 * Allocates a new {@link PointerBufferDouble}. The Contents are initialized to 0. If the {@link PointerBufferDouble} is freed, it will free the memory.
	 */
	public static PointerBufferDouble calloc(Allocator allocator, @NotNull Object[] parents) {
		return new PointerBufferDouble(allocator, allocator.calloc(TYPE.bytes), parents);
	}
	
	//create
	
	/**
	 * Creates a new {@link PointerBufferDouble} from the given address. If the {@link PointerBufferDouble} is freed, it <b>WILL</b> free the memory.
	 */
	public static PointerBufferDouble create(Allocator allocator, long address, @NotNull Object[] parents) {
		return new PointerBufferDouble(allocator, address, parents);
	}
	
	/**
	 * Creates a new {@link PointerBufferDouble} from the given address. It will <b>NEVER</b> free the memory.
	 */
	public static PointerBufferDouble wrap(long address, @NotNull Object[] parents) {
		return create(allocatorNoop(), address, parents);
	}
	
	//object
	protected PointerBufferDouble(Allocator allocator, long address, @NotNull Object[] parents) {
		super(allocator, address, parents);
	}
	
	@Override
	public Primitive<?> type() {
		return TYPE;
	}
	
	@Override
	public DoubleBuffer nioBuffer() {
		return NioBufferWrapper.wrapDouble(this, 1);
	}
	
	//single
	public double getDouble() {
		return UNSAFE.getDouble(address());
	}
	
	public void putDouble(double b) {
		UNSAFE.putDouble(address(), b);
	}
	
	//copy
	@Override
	public void copyInto(PointerBufferDouble dest) {
		dest.putDouble(this.getDouble());
	}
	
	@Override
	public void copyFrom(PointerBufferDouble src) {
		this.putDouble(src.getDouble());
	}
}
