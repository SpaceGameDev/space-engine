package space.util.bufferAllocator.buffers;

import spaceOld.util.PrimitiveSize;

@SuppressWarnings("PointlessBitwiseExpression")
public interface IBuffer {
	
	long address();
	
	long capacity();
	
	default int capacityInt() {
		return (int) capacity();
	}
	
	void clear();
	
	void free();
	
	IBuffer addTracking();
	
	byte getByteOffset(long offset);
	
	default byte getByteIndex(long index) {
		return getByteOffset(index << PrimitiveSize.ByteShift);
	}
	
	void getByteOffset(long offset, byte[] b);
	
	default void getByteIndex(long index, byte[] b) {
		getByteOffset(index << PrimitiveSize.ByteShift, b);
	}
	
	void putByteOffset(long offset, byte b);
	
	default void putByteIndex(long index, byte b) {
		putByteOffset(index << PrimitiveSize.ByteShift, b);
	}
	
	void putByteOffset(long offset, byte[] b);
	
	default void putByteIndex(long index, byte[] b) {
		putByteOffset(index << PrimitiveSize.ByteShift, b);
	}
	
	default void getByte(byte[] b) {
		getByteOffset(0, b);
	}
	
	default void putByte(byte[] b) {
		putByteOffset(0, b);
	}
	
	short getShortOffset(long offset);
	
	default short getShortIndex(long index) {
		return getShortOffset(index << PrimitiveSize.int16Shift);
	}
	
	void getShortOffset(long offset, short[] b);
	
	default void getShortIndex(long index, short[] b) {
		getShortOffset(index << PrimitiveSize.int16Shift, b);
	}
	
	void putShortOffset(long offset, short b);
	
	default void putShortIndex(long index, short b) {
		putShortOffset(index << PrimitiveSize.int16Shift, b);
	}
	
	void putShortOffset(long offset, short[] b);
	
	default void putShortIndex(long index, short[] b) {
		putShortOffset(index << PrimitiveSize.int16Shift, b);
	}
	
	default void getShort(short[] b) {
		getShortOffset(0, b);
	}
	
	default void putShort(short[] b) {
		putShortOffset(0, b);
	}
	
	int getIntOffset(long offset);
	
	default int getIntIndex(long index) {
		return getIntOffset(index << PrimitiveSize.int32Shift);
	}
	
	void getIntOffset(long offset, int[] b);
	
	default void getIntIndex(long index, int[] b) {
		getIntOffset(index << PrimitiveSize.int32Shift, b);
	}
	
	void putIntOffset(long offset, int b);
	
	default void putIntIndex(long index, int b) {
		putIntOffset(index << PrimitiveSize.int32Shift, b);
	}
	
	void putIntOffset(long offset, int[] b);
	
	default void putIntIndex(long index, int[] b) {
		putIntOffset(index << PrimitiveSize.int32Shift, b);
	}
	
	default void getInt(int[] b) {
		getIntOffset(0, b);
	}
	
	default void putInt(int[] b) {
		putIntOffset(0, b);
	}
	
	long getLongOffset(long offset);
	
	default long getLongIndex(long index) {
		return getLongOffset(index << PrimitiveSize.int64Shift);
	}
	
	void getLongOffset(long offset, long[] b);
	
	default void getLongIndex(long index, long[] b) {
		getLongOffset(index << PrimitiveSize.int64Shift, b);
	}
	
	void putLongOffset(long offset, long b);
	
	default void putLongIndex(long index, long b) {
		putLongOffset(index << PrimitiveSize.int64Shift, b);
	}
	
	void putLongOffset(long offset, long[] b);
	
	default void putLongIndex(long index, long[] b) {
		putLongOffset(index << PrimitiveSize.int64Shift, b);
	}
	
	default void getLong(long[] b) {
		getLongOffset(0, b);
	}
	
	default void putLong(long[] b) {
		putLongOffset(0, b);
	}
	
	float getFloatOffset(long offset);
	
	default float getFloatIndex(long index) {
		return getFloatOffset(index << PrimitiveSize.fp32Shift);
	}
	
	void getFloatOffset(long offset, float[] b);
	
	default void getFloatIndex(long index, float[] b) {
		getFloatOffset(index << PrimitiveSize.fp32Shift, b);
	}
	
	void putFloatOffset(long offset, float b);
	
	default void putFloatIndex(long index, float b) {
		putFloatOffset(index << PrimitiveSize.fp32Shift, b);
	}
	
	void putFloatOffset(long offset, float[] b);
	
	default void putFloatIndex(long index, float[] b) {
		putFloatOffset(index << PrimitiveSize.fp32Shift, b);
	}
	
	default void getFloat(float[] b) {
		getFloatOffset(0, b);
	}
	
	default void putFloat(float[] b) {
		putFloatOffset(0, b);
	}
	
	double getDoubleOffset(long offset);
	
	default double getDoubleIndex(long index) {
		return getDoubleOffset(index << PrimitiveSize.fp64Shift);
	}
	
	void getDoubleOffset(long offset, double[] b);
	
	default void getDoubleIndex(long index, double[] b) {
		getDoubleOffset(index << PrimitiveSize.fp64Shift, b);
	}
	
	void putDoubleOffset(long offset, double b);
	
	default void putDoubleIndex(long index, double b) {
		putDoubleOffset(index << PrimitiveSize.fp64Shift, b);
	}
	
	void putDoubleOffset(long offset, double[] b);
	
	default void putDoubleIndex(long index, double[] b) {
		putDoubleOffset(index << PrimitiveSize.fp64Shift, b);
	}
	
	default void getDouble(double[] b) {
		getDoubleOffset(0, b);
	}
	
	default void putDouble(double[] b) {
		putDoubleOffset(0, b);
	}
	
	boolean getBooleanOffset(long offset);
	
	default boolean getBooleanIndex(long index) {
		return getBooleanOffset(index << PrimitiveSize.BooleanShift);
	}
	
	void getBooleanOffset(long offset, boolean[] b);
	
	default void getBooleanIndex(long index, boolean[] b) {
		getBooleanOffset(index << PrimitiveSize.BooleanShift, b);
	}
	
	void putBooleanOffset(long offset, boolean b);
	
	default void putBooleanIndex(long index, boolean b) {
		putBooleanOffset(index << PrimitiveSize.BooleanShift, b);
	}
	
	void putBooleanOffset(long offset, boolean[] b);
	
	default void putBooleanIndex(long index, boolean[] b) {
		putBooleanOffset(index << PrimitiveSize.BooleanShift, b);
	}
	
	default void getBoolean(boolean[] b) {
		getBooleanOffset(0, b);
	}
	
	default void putBoolean(boolean[] b) {
		putBooleanOffset(0, b);
	}
}
