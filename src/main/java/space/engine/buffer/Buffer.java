package space.engine.buffer;

import space.engine.baseobject.Dumpable;
import space.engine.freeableStorage.Freeable;
import space.engine.unsafe.UnsafeInstance;
import sun.misc.Unsafe;

public abstract class Buffer implements Freeable, Dumpable {
	
	protected static final Unsafe UNSAFE = UnsafeInstance.getUnsafe();
	
	//index checks (copied from Object.checkIndex())
	public static long checkIndex(long index, long bufferLength) {
		if (index < 0 || index >= bufferLength)
			throw new IndexOutOfBoundsException(String.format("Index %d out-of-bounds for length %d", index, bufferLength));
		return index;
	}
	
	public static long checkFromToIndex(long fromIndex, long toIndex, long bufferLength) {
		if (fromIndex < 0 || fromIndex > toIndex || toIndex > bufferLength)
			throw new IndexOutOfBoundsException(String.format("Range [%d, %d) out-of-bounds for length %d", fromIndex, toIndex, bufferLength));
		return fromIndex;
	}
	
	public static long checkFromIndexSize(long fromIndex, long size, long bufferLength) {
		if ((bufferLength | fromIndex | size) < 0 || size > bufferLength - fromIndex)
			throw new IndexOutOfBoundsException(String.format("Range [%d, %<d + %d) out-of-bounds for length %d", fromIndex, size, bufferLength));
		return fromIndex;
	}
	
	//object
	public abstract long address();
	
	public abstract long sizeOf();
}
