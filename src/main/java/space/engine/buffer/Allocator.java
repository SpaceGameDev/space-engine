package space.engine.buffer;

import org.jetbrains.annotations.NotNull;
import space.engine.buffer.array.AbstractArrayBuffer;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.buffer.pointer.AbstractPointerBuffer;

/**
 * An Allocator for {@link DirectBuffer Buffers}. It can:
 * <ul>
 * <li>{@link Allocator#create(long, long, Object...)} create a Buffer-Object with address and capacity</li>
 * <li>{@link Allocator#createNoFree(long, long, Object...)} create a Buffer-Object that will <b>NOT</b> free itself</li>
 * <li>{@link Allocator#malloc(long, Object...)} allocate a Buffer containing uninitialized memory</li>
 * <li>{@link Allocator#calloc(long, Object...)} allocate a Buffer containing cleared out data / all zeros</li>
 * </ul>
 * <p><b>NOTE: the definition of capacity will vary depending on the implementation!</b></p>
 * Some Examples:
 * <ul>
 * <li>{@link DirectBuffer}: number of bytes the {@link DirectBuffer} should have</li>
 * <li>{@link AbstractArrayBuffer}: number of primitive type the {@link AbstractArrayBuffer} should have</li>
 * <li>{@link AbstractPointerBuffer}: only checks if the buffers capacity is enough to hold one primitive type</li>
 * </ul>
 */
public interface Allocator<T> {
	
	@NotNull T create(long address, long capacity, @NotNull Object[] parents);
	
	@NotNull T createNoFree(long address, long capacity, @NotNull Object[] parents);
	
	@NotNull T malloc(long capacity, @NotNull Object[] parents);
	
	@NotNull T calloc(long capacity, @NotNull Object[] parents);
}
