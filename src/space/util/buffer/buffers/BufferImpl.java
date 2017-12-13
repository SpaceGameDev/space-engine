package space.util.buffer.buffers;

import static space.util.math.MathUtils.min;
import static space.util.unsafe.UnsafeInstance.UNSAFE;
import static sun.misc.Unsafe.*;

public class BufferImpl extends SimpleBuffer implements Buffer {
	
	public BufferImpl(long capacity) {
		super(capacity);
	}
	
	public BufferImpl(long address, long capacity) {
		super(address, capacity);
	}
	
	@Override
	public long address() {
		return address;
	}
	
	@Override
	public long capacity() {
		return capacity;
	}
	
	@Override
	public void clear() {
		UNSAFE.setMemory(address, capacity, (byte) 0);
	}
	
	//single
	//byte
	@Override
	public byte getByte(long offset) {
		return UNSAFE.getByte(address + offset);
	}
	
	@Override
	public void putByte(long offset, byte b) {
		UNSAFE.putByte(address + offset, b);
	}
	
	//short
	@Override
	public short getShort(long offset) {
		return UNSAFE.getShort(address + offset);
	}
	
	@Override
	public void putShort(long offset, short b) {
		UNSAFE.putShort(address + offset, b);
	}
	
	//int
	@Override
	public int getInt(long offset) {
		return UNSAFE.getInt(address + offset);
	}
	
	@Override
	public void putInt(long offset, int b) {
		UNSAFE.putInt(address + offset, b);
	}
	
	//long
	@Override
	public long getLong(long offset) {
		return UNSAFE.getLong(address + offset);
	}
	
	@Override
	public void putLong(long offset, long b) {
		UNSAFE.putLong(address + offset, b);
	}
	
	//float
	@Override
	public float getFloat(long offset) {
		return UNSAFE.getFloat(address + offset);
	}
	
	@Override
	public void putFloat(long offset, float b) {
		UNSAFE.putFloat(address + offset, b);
	}
	
	//double
	@Override
	public double getDouble(long offset) {
		return UNSAFE.getDouble(address + offset);
	}
	
	@Override
	public void putDouble(long offset, double b) {
		UNSAFE.putDouble(address + offset, b);
	}
	
	//boolean
	@Override
	public boolean getBoolean(long offset) {
		return UNSAFE.getByte(address + offset) != 0;
	}
	
	@Override
	public void putBoolean(long offset, boolean b) {
		UNSAFE.putByte(address + offset, (byte) (b ? 1 : 0));
	}
	
	//arrays
	//byte
	@Override
	public void copyInto(long offset, byte[] dest, int destPos, int length) {
		UNSAFE.copyMemory(null, address + offset, dest, ARRAY_BYTE_BASE_OFFSET + destPos * ARRAY_BYTE_INDEX_SCALE, length * ARRAY_BYTE_INDEX_SCALE);
	}
	
	@Override
	public void copyFrom(byte[] src, int srcPos, int length, long offset) {
		UNSAFE.copyMemory(src, ARRAY_BYTE_BASE_OFFSET + srcPos * ARRAY_BYTE_INDEX_SCALE, null, address + offset, length * ARRAY_BYTE_INDEX_SCALE);
	}
	
	//short
	@Override
	public void copyInto(long offset, short[] dest, int destPos, int length) {
		UNSAFE.copyMemory(null, address + offset, dest, ARRAY_SHORT_BASE_OFFSET + destPos * ARRAY_SHORT_INDEX_SCALE, length * ARRAY_SHORT_INDEX_SCALE);
	}
	
	@Override
	public void copyFrom(short[] src, int srcPos, int length, long offset) {
		UNSAFE.copyMemory(src, ARRAY_SHORT_BASE_OFFSET + srcPos * ARRAY_SHORT_INDEX_SCALE, null, address + offset, length * ARRAY_SHORT_INDEX_SCALE);
	}
	
	//int
	@Override
	public void copyInto(long offset, int[] dest, int destPos, int length) {
		UNSAFE.copyMemory(null, address + offset, dest, ARRAY_INT_BASE_OFFSET + destPos * ARRAY_INT_INDEX_SCALE, length * ARRAY_INT_INDEX_SCALE);
	}
	
	@Override
	public void copyFrom(int[] src, int srcPos, int length, long offset) {
		UNSAFE.copyMemory(src, ARRAY_INT_BASE_OFFSET + srcPos * ARRAY_INT_INDEX_SCALE, null, address + offset, length * ARRAY_INT_INDEX_SCALE);
	}
	
	//long
	@Override
	public void copyInto(long offset, long[] dest, int destPos, int length) {
		UNSAFE.copyMemory(null, address + offset, dest, ARRAY_LONG_BASE_OFFSET + destPos * ARRAY_LONG_INDEX_SCALE, length * ARRAY_LONG_INDEX_SCALE);
	}
	
	@Override
	public void copyFrom(long[] src, int srcPos, int length, long offset) {
		UNSAFE.copyMemory(src, ARRAY_LONG_BASE_OFFSET + srcPos * ARRAY_LONG_INDEX_SCALE, null, address + offset, length * ARRAY_LONG_INDEX_SCALE);
	}
	
	//float
	@Override
	public void copyInto(long offset, float[] dest, int destPos, int length) {
		UNSAFE.copyMemory(null, address + offset, dest, ARRAY_FLOAT_BASE_OFFSET + destPos * ARRAY_FLOAT_INDEX_SCALE, length * ARRAY_FLOAT_INDEX_SCALE);
	}
	
	@Override
	public void copyFrom(float[] src, int srcPos, int length, long offset) {
		UNSAFE.copyMemory(src, ARRAY_FLOAT_BASE_OFFSET + srcPos * ARRAY_FLOAT_INDEX_SCALE, null, address + offset, length * ARRAY_FLOAT_INDEX_SCALE);
	}
	
	//double
	@Override
	public void copyInto(long offset, double[] dest, int destPos, int length) {
		UNSAFE.copyMemory(null, address + offset, dest, ARRAY_DOUBLE_BASE_OFFSET + destPos * ARRAY_DOUBLE_INDEX_SCALE, length * ARRAY_DOUBLE_INDEX_SCALE);
	}
	
	@Override
	public void copyFrom(double[] src, int srcPos, int length, long offset) {
		UNSAFE.copyMemory(src, ARRAY_DOUBLE_BASE_OFFSET + srcPos * ARRAY_DOUBLE_INDEX_SCALE, null, address + offset, length * ARRAY_DOUBLE_INDEX_SCALE);
	}
	
	//boolean
	@Override
	public void copyInto(long offset, boolean[] dest, int destPos, int length) {
		if (AllowBooleanArrayCopy.ALLOWBOOLEANARRAYCOPY)
			UNSAFE.copyMemory(null, address + offset, dest, ARRAY_BOOLEAN_BASE_OFFSET + destPos * ARRAY_BOOLEAN_INDEX_SCALE, length * ARRAY_BOOLEAN_INDEX_SCALE);
		else
			for (int i = 0; i < length; i++)
				dest[i] = UNSAFE.getByte(address + i * ARRAY_BOOLEAN_INDEX_SCALE) != 0;
	}
	
	@Override
	public void copyFrom(boolean[] src, int srcPos, int length, long offset) {
		if (AllowBooleanArrayCopy.ALLOWBOOLEANARRAYCOPY)
			UNSAFE.copyMemory(src, ARRAY_BOOLEAN_BASE_OFFSET + srcPos * ARRAY_BOOLEAN_INDEX_SCALE, null, address + offset, length * ARRAY_BOOLEAN_INDEX_SCALE);
		else
			for (int i = 0; i < length; i++)
				UNSAFE.putByte(address + i * ARRAY_BOOLEAN_INDEX_SCALE, (byte) (src[i] ? 1 : 0));
	}
	
	@Override
	public void fillByte(long offset, byte b, long length) {
		UNSAFE.setMemory(address + offset, length, b);
	}
	
	@Override
	public void fillShort(long offset, short b, long length) {
		short[] tarray = new short[] {b};
		UNSAFE.copyMemory(tarray, ARRAY_SHORT_BASE_OFFSET, null, address + offset, min(ARRAY_SHORT_INDEX_SCALE, length));
		int i = ARRAY_SHORT_INDEX_SCALE;
		for (; (i << 1) < length; i <<= 1)
			UNSAFE.copyMemory(address + offset, address + offset + i, i);
		if (i < length)
			UNSAFE.copyMemory(address + offset, address + offset + i, length - i);
	}
	
	@Override
	public void fillInt(long offset, int b, long length) {
		int[] tarray = new int[] {b};
		UNSAFE.copyMemory(tarray, ARRAY_INT_BASE_OFFSET, null, address + offset, min(ARRAY_INT_INDEX_SCALE, length));
		int i = ARRAY_INT_INDEX_SCALE;
		for (; (i << 1) < length; i <<= 1)
			UNSAFE.copyMemory(address + offset, address + offset + i, i);
		if (i < length)
			UNSAFE.copyMemory(address + offset, address + offset + i, length - i);
	}
	
	@Override
	public void fillLong(long offset, long b, long length) {
		long[] tarray = new long[] {b};
		UNSAFE.copyMemory(tarray, ARRAY_LONG_BASE_OFFSET, null, address + offset, min(ARRAY_LONG_INDEX_SCALE, length));
		int i = ARRAY_LONG_INDEX_SCALE;
		for (; (i << 1) < length; i <<= 1)
			UNSAFE.copyMemory(address + offset, address + offset + i, i);
		if (i < length)
			UNSAFE.copyMemory(address + offset, address + offset + i, length - i);
	}
	
	@Override
	public void fillFloat(long offset, float b, long length) {
		float[] tarray = new float[] {b};
		UNSAFE.copyMemory(tarray, ARRAY_FLOAT_BASE_OFFSET, null, address + offset, min(ARRAY_FLOAT_INDEX_SCALE, length));
		int i = ARRAY_FLOAT_INDEX_SCALE;
		for (; (i << 1) < length; i <<= 1)
			UNSAFE.copyMemory(address + offset, address + offset + i, i);
		if (i < length)
			UNSAFE.copyMemory(address + offset, address + offset + i, length - i);
	}
	
	@Override
	public void fillDouble(long offset, double b, long length) {
		double[] tarray = new double[] {b};
		UNSAFE.copyMemory(tarray, ARRAY_DOUBLE_BASE_OFFSET, null, address + offset, min(ARRAY_DOUBLE_INDEX_SCALE, length));
		int i = ARRAY_DOUBLE_INDEX_SCALE;
		for (; (i << 1) < length; i <<= 1)
			UNSAFE.copyMemory(address + offset, address + offset + i, i);
		if (i < length)
			UNSAFE.copyMemory(address + offset, address + offset + i, length - i);
	}
	
	@Override
	public void fillBoolean(long offset, boolean b, long length) {
		fillByte(offset, (byte) (b ? 1 : 0), length);
	}
	
	@Override
	public void copyInto(long offset, Buffer dest, long destPos, long length) {
		UNSAFE.copyMemory(address + offset, dest.address() + destPos, length);
	}
	
	@Override
	public void copyFrom(Buffer src, long srcPos, long length, long offset) {
		UNSAFE.copyMemory(src.address() + srcPos, address + offset, length);
	}
}
