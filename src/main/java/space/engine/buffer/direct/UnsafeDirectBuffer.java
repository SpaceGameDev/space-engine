package space.engine.buffer.direct;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.Dumpable;
import space.engine.baseobject.ToString;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.freeableStorage.FreeableStorageImpl;
import space.engine.math.MathUtils;
import space.engine.string.String2D;
import space.engine.string.builder.CharBufferBuilder2D;
import space.engine.string.toStringHelper.ToStringHelper;
import space.engine.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;
import space.engine.unsafe.AllowBooleanArrayCopy;
import space.engine.unsafe.UnsafeInstance;
import sun.misc.Unsafe;

import static space.engine.math.MathUtils.min;
import static sun.misc.Unsafe.*;

/**
 * An <b>UNCHECKED</b> implementation of {@link DirectBuffer}.
 * <p>Use {@link CheckedDirectBuffer} if you need any access checked.</p>
 */
public class UnsafeDirectBuffer implements DirectBuffer, ToString {
	
	private static final Unsafe UNSAFE = UnsafeInstance.getUnsafe();
	
	public Storage storage;
	
	protected UnsafeDirectBuffer() {
	}
	
	public UnsafeDirectBuffer(long capacity, FreeableStorage... parents) {
		this(UNSAFE.allocateMemory(capacity), capacity, parents);
	}
	
	public UnsafeDirectBuffer(long address, long capacity, FreeableStorage... parents) {
		this.storage = new Storage(this, address, capacity, parents);
	}
	
	@NotNull
	@Override
	public FreeableStorage getStorage() {
		return storage;
	}
	
	//storage
	public static class Storage extends FreeableStorageImpl implements ToString {
		
		protected long address;
		protected long capacity;
		
		public Storage(Object referent, long address, long capacity, FreeableStorage... parents) {
			super(referent, parents);
			this.address = address;
			this.capacity = capacity;
		}
		
		@Override
		protected synchronized void handleFree() {
			UNSAFE.freeMemory(address);
			address = 0;
		}
		
		public long address() {
			throwIfFreed();
			return address;
		}
		
		public long capacity() {
			throwIfFreed();
			return capacity;
		}
		
		@NotNull
		@Override
		public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			boolean freed = this.isFreed();
			tsh.add("isFreed", freed);
			if (!freed)
				tsh.add("address", this.address);
			tsh.add("capacity", this.capacity);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	//getter
	@Override
	public long address() {
		return storage.address();
	}
	
	@Override
	public long capacity() {
		return storage.capacity();
	}
	
	//clear
	@Override
	public void clear() {
		UNSAFE.setMemory(storage.address(), storage.capacity(), (byte) 0);
	}
	
	//Dumpable
	@NotNull
	@Override
	public String2D dump() {
		if (storage.capacity() > Dumpable.getMaxDump())
			return Dumpable.DUMP_CAP_REACHED;
		
		CharBufferBuilder2D<?> b = new CharBufferBuilder2D<>(2, (int) storage.capacity() * 3);
		for (int i = 0; i < storage.capacity(); i++) {
			int pos = i * 3;
			byte d = UNSAFE.getByte(storage.address() + i);
			
			if (i % 8 == 0)
				b.setY(0).setX(pos).append(Integer.toHexString(i));
			b.setY(1).setX(pos).append(MathUtils.DIGITS[(d >>> 4) & 0xF]).append(MathUtils.DIGITS[d & 0xF]);
		}
		return b.toString2D();
	}
	
	//single
	//byte
	@Override
	public byte getByte(long offset) {
		return UNSAFE.getByte(storage.address() + offset);
	}
	
	@Override
	public void putByte(long offset, byte b) {
		UNSAFE.putByte(storage.address() + offset, b);
	}
	
	//short
	@Override
	public short getShort(long offset) {
		return UNSAFE.getShort(storage.address() + offset);
	}
	
	@Override
	public void putShort(long offset, short b) {
		UNSAFE.putShort(storage.address() + offset, b);
	}
	
	//int
	@Override
	public int getInt(long offset) {
		return UNSAFE.getInt(storage.address() + offset);
	}
	
	@Override
	public void putInt(long offset, int b) {
		UNSAFE.putInt(storage.address() + offset, b);
	}
	
	//long
	@Override
	public long getLong(long offset) {
		return UNSAFE.getLong(storage.address() + offset);
	}
	
	@Override
	public void putLong(long offset, long b) {
		UNSAFE.putLong(storage.address() + offset, b);
	}
	
	//float
	@Override
	public float getFloat(long offset) {
		return UNSAFE.getFloat(storage.address() + offset);
	}
	
	@Override
	public void putFloat(long offset, float b) {
		UNSAFE.putFloat(storage.address() + offset, b);
	}
	
	//double
	@Override
	public double getDouble(long offset) {
		return UNSAFE.getDouble(storage.address() + offset);
	}
	
	@Override
	public void putDouble(long offset, double b) {
		UNSAFE.putDouble(storage.address() + offset, b);
	}
	
	//boolean
	@Override
	public boolean getBoolean(long offset) {
		return UNSAFE.getByte(storage.address() + offset) != 0;
	}
	
	@Override
	public void putBoolean(long offset, boolean b) {
		UNSAFE.putByte(storage.address() + offset, (byte) (b ? 1 : 0));
	}
	
	//pointer
	@Override
	public long getPointer(long offset) {
		return UNSAFE.getAddress(storage.address() + offset);
	}
	
	@Override
	public void putPointer(long offset, long b) {
		UNSAFE.putAddress(storage.address() + offset, b);
	}
	
	//arrays
	//byte
	@Override
	public void copyInto(long offset, byte[] dest, int destPos, int length) {
		UNSAFE.copyMemory(null, storage.address() + offset, dest, ARRAY_BYTE_BASE_OFFSET + destPos * ARRAY_BYTE_INDEX_SCALE, length * ARRAY_BYTE_INDEX_SCALE);
	}
	
	@Override
	public void copyFrom(byte[] src, int srcPos, int length, long offset) {
		UNSAFE.copyMemory(src, ARRAY_BYTE_BASE_OFFSET + srcPos * ARRAY_BYTE_INDEX_SCALE, null, storage.address() + offset, length * ARRAY_BYTE_INDEX_SCALE);
	}
	
	//short
	@Override
	public void copyInto(long offset, short[] dest, int destPos, int length) {
		UNSAFE.copyMemory(null, storage.address() + offset, dest, ARRAY_SHORT_BASE_OFFSET + destPos * ARRAY_SHORT_INDEX_SCALE, length * ARRAY_SHORT_INDEX_SCALE);
	}
	
	@Override
	public void copyFrom(short[] src, int srcPos, int length, long offset) {
		UNSAFE.copyMemory(src, ARRAY_SHORT_BASE_OFFSET + srcPos * ARRAY_SHORT_INDEX_SCALE, null, storage.address() + offset, length * ARRAY_SHORT_INDEX_SCALE);
	}
	
	//int
	@Override
	public void copyInto(long offset, int[] dest, int destPos, int length) {
		UNSAFE.copyMemory(null, storage.address() + offset, dest, ARRAY_INT_BASE_OFFSET + destPos * ARRAY_INT_INDEX_SCALE, length * ARRAY_INT_INDEX_SCALE);
	}
	
	@Override
	public void copyFrom(int[] src, int srcPos, int length, long offset) {
		UNSAFE.copyMemory(src, ARRAY_INT_BASE_OFFSET + srcPos * ARRAY_INT_INDEX_SCALE, null, storage.address() + offset, length * ARRAY_INT_INDEX_SCALE);
	}
	
	//long
	@Override
	public void copyInto(long offset, long[] dest, int destPos, int length) {
		UNSAFE.copyMemory(null, storage.address() + offset, dest, ARRAY_LONG_BASE_OFFSET + destPos * ARRAY_LONG_INDEX_SCALE, length * ARRAY_LONG_INDEX_SCALE);
	}
	
	@Override
	public void copyFrom(long[] src, int srcPos, int length, long offset) {
		UNSAFE.copyMemory(src, ARRAY_LONG_BASE_OFFSET + srcPos * ARRAY_LONG_INDEX_SCALE, null, storage.address() + offset, length * ARRAY_LONG_INDEX_SCALE);
	}
	
	//float
	@Override
	public void copyInto(long offset, float[] dest, int destPos, int length) {
		UNSAFE.copyMemory(null, storage.address() + offset, dest, ARRAY_FLOAT_BASE_OFFSET + destPos * ARRAY_FLOAT_INDEX_SCALE, length * ARRAY_FLOAT_INDEX_SCALE);
	}
	
	@Override
	public void copyFrom(float[] src, int srcPos, int length, long offset) {
		UNSAFE.copyMemory(src, ARRAY_FLOAT_BASE_OFFSET + srcPos * ARRAY_FLOAT_INDEX_SCALE, null, storage.address() + offset, length * ARRAY_FLOAT_INDEX_SCALE);
	}
	
	//double
	@Override
	public void copyInto(long offset, double[] dest, int destPos, int length) {
		UNSAFE.copyMemory(null, storage.address() + offset, dest, ARRAY_DOUBLE_BASE_OFFSET + destPos * ARRAY_DOUBLE_INDEX_SCALE, length * ARRAY_DOUBLE_INDEX_SCALE);
	}
	
	@Override
	public void copyFrom(double[] src, int srcPos, int length, long offset) {
		UNSAFE.copyMemory(src, ARRAY_DOUBLE_BASE_OFFSET + srcPos * ARRAY_DOUBLE_INDEX_SCALE, null, storage.address() + offset, length * ARRAY_DOUBLE_INDEX_SCALE);
	}
	
	//boolean
	@Override
	public void copyInto(long offset, boolean[] dest, int destPos, int length) {
		if (AllowBooleanArrayCopy.ALLOW_BOOLEAN_ARRAY_COPY)
			UNSAFE.copyMemory(null, storage.address() + offset, dest, ARRAY_BOOLEAN_BASE_OFFSET + destPos * ARRAY_BOOLEAN_INDEX_SCALE, length * ARRAY_BOOLEAN_INDEX_SCALE);
		else
			for (int i = 0; i < length; i++)
				dest[i] = UNSAFE.getByte(storage.address() + i * ARRAY_BOOLEAN_INDEX_SCALE) != 0;
	}
	
	@Override
	public void copyFrom(boolean[] src, int srcPos, int length, long offset) {
		if (AllowBooleanArrayCopy.ALLOW_BOOLEAN_ARRAY_COPY)
			UNSAFE.copyMemory(src, ARRAY_BOOLEAN_BASE_OFFSET + srcPos * ARRAY_BOOLEAN_INDEX_SCALE, null, storage.address() + offset, length * ARRAY_BOOLEAN_INDEX_SCALE);
		else
			for (int i = 0; i < length; i++)
				UNSAFE.putByte(storage.address() + i * ARRAY_BOOLEAN_INDEX_SCALE, (byte) (src[i] ? 1 : 0));
	}
	
	@Override
	public void fillByte(long offset, byte b, long length) {
		UNSAFE.setMemory(storage.address() + offset, length, b);
	}
	
	@Override
	public void fillShort(long offset, short b, long length) {
		short[] tarray = new short[] {b};
		UNSAFE.copyMemory(tarray, ARRAY_SHORT_BASE_OFFSET, null, storage.address() + offset, min(ARRAY_SHORT_INDEX_SCALE, length));
		int i = ARRAY_SHORT_INDEX_SCALE;
		for (; (i << 1) < length; i <<= 1)
			UNSAFE.copyMemory(storage.address() + offset, storage.address() + offset + i, i);
		if (i < length)
			UNSAFE.copyMemory(storage.address() + offset, storage.address() + offset + i, length - i);
	}
	
	@Override
	public void fillInt(long offset, int b, long length) {
		int[] tarray = new int[] {b};
		UNSAFE.copyMemory(tarray, ARRAY_INT_BASE_OFFSET, null, storage.address() + offset, min(ARRAY_INT_INDEX_SCALE, length));
		int i = ARRAY_INT_INDEX_SCALE;
		for (; (i << 1) < length; i <<= 1)
			UNSAFE.copyMemory(storage.address() + offset, storage.address() + offset + i, i);
		if (i < length)
			UNSAFE.copyMemory(storage.address() + offset, storage.address() + offset + i, length - i);
	}
	
	@Override
	public void fillLong(long offset, long b, long length) {
		long[] tarray = new long[] {b};
		UNSAFE.copyMemory(tarray, ARRAY_LONG_BASE_OFFSET, null, storage.address() + offset, min(ARRAY_LONG_INDEX_SCALE, length));
		int i = ARRAY_LONG_INDEX_SCALE;
		for (; (i << 1) < length; i <<= 1)
			UNSAFE.copyMemory(storage.address() + offset, storage.address() + offset + i, i);
		if (i < length)
			UNSAFE.copyMemory(storage.address() + offset, storage.address() + offset + i, length - i);
	}
	
	@Override
	public void fillFloat(long offset, float b, long length) {
		float[] tarray = new float[] {b};
		UNSAFE.copyMemory(tarray, ARRAY_FLOAT_BASE_OFFSET, null, storage.address() + offset, min(ARRAY_FLOAT_INDEX_SCALE, length));
		int i = ARRAY_FLOAT_INDEX_SCALE;
		for (; (i << 1) < length; i <<= 1)
			UNSAFE.copyMemory(storage.address() + offset, storage.address() + offset + i, i);
		if (i < length)
			UNSAFE.copyMemory(storage.address() + offset, storage.address() + offset + i, length - i);
	}
	
	@Override
	public void fillDouble(long offset, double b, long length) {
		double[] tarray = new double[] {b};
		UNSAFE.copyMemory(tarray, ARRAY_DOUBLE_BASE_OFFSET, null, storage.address() + offset, min(ARRAY_DOUBLE_INDEX_SCALE, length));
		int i = ARRAY_DOUBLE_INDEX_SCALE;
		for (; (i << 1) < length; i <<= 1)
			UNSAFE.copyMemory(storage.address() + offset, storage.address() + offset + i, i);
		if (i < length)
			UNSAFE.copyMemory(storage.address() + offset, storage.address() + offset + i, length - i);
	}
	
	@Override
	public void fillBoolean(long offset, boolean b, long length) {
		fillByte(offset, (byte) (b ? 1 : 0), length);
	}
	
	@Override
	public void copyInto(long offset, DirectBuffer dest, long destPos, long length) {
		UNSAFE.copyMemory(storage.address() + offset, dest.address() + destPos, length);
	}
	
	@Override
	public void copyFrom(DirectBuffer src, long srcPos, long length, long offset) {
		UNSAFE.copyMemory(src.address() + srcPos, storage.address() + offset, length);
	}
	
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("isFreed", this.storage.isFreed());
		tsh.add("address", this.storage.address);
		tsh.add("capacity", this.storage.capacity);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
