package space.util.buffer.array;

import org.jetbrains.annotations.NotNull;
import space.util.annotation.Self;
import space.util.buffer.Buffer;
import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;
import space.util.primitive.Primitives;
import space.util.string.String2D;

public abstract class AbstractArrayBuffer<@Self SELF extends AbstractArrayBuffer<SELF>> implements Buffer {
	
	public final DirectBuffer buffer;
	public final Primitives type;
	public final long length;
	
	public AbstractArrayBuffer(DirectBuffer buffer, Primitives type) {
		this(buffer, type, type.divide(buffer.capacity()));
	}
	
	protected AbstractArrayBuffer(DirectBuffer buffer, Primitives type, long length) {
		this.buffer = buffer;
		this.type = type;
		this.length = length;
	}
	
	//offset calc
	public long length() {
		return length;
	}
	
	public long getOffset(long index) {
		return type.multiply(index);
	}
	
	public long getOffset(long index, long offset) {
		return type.multiply(index, offset);
	}
	
	//buffer copy
	public void copyInto(long offset, SELF dest, long destOffset, long length) {
		buffer.copyInto(offset, dest.buffer, destOffset, length);
	}
	
	public void copyFrom(SELF src, long srcOffset, long length, long offset) {
		buffer.copyFrom(src.buffer, srcOffset, length, offset);
	}
	
	//delegate
	@Override
	public long address() {
		return buffer.address();
	}
	
	@Override
	public long capacity() {
		return buffer.capacity();
	}
	
	@Override
	public void clear() {
		buffer.clear();
	}
	
	@NotNull
	@Override
	public String2D dump() {
		return buffer.dump();
	}
	
	@NotNull
	@Override
	public FreeableStorage getStorage() {
		return buffer.getStorage();
	}
}
