package space.util.bufferAllocator.buffers;

import space.util.baseobject.additional.IReleasable;
import space.util.primitive.PrimitiveType;

//FIXME: redo some of the access functions: add fill() and copy(), change array[] set / get to have a length
public interface IBuffer extends IReleasable {
	
	//buffer positioning
	long address();
	
	long capacity();
	
	//modifier operations
	void clear();
	
	//release
	@Override
	void release();
	
	//getter and setter
	//byte get
	byte getByteOffset(long offset);
	
	default byte getByteIndex(long index) {
		return getByteOffset(index << PrimitiveType.BYTE.SHIFT);
	}
	
	void getByteOffset(long offset, byte[] b);
	
	default void getByteIndex(long index, byte[] b) {
		getByteOffset(index << PrimitiveType.BYTE.SHIFT, b);
	}
	
	default void getByte(byte[] b) {
		getByteOffset(0, b);
	}
	
	//byte put
	void putByteOffset(long offset, byte b);
	
	default void putByteIndex(long index, byte b) {
		putByteOffset(index << PrimitiveType.BYTE.SHIFT, b);
	}
	
	void putByteOffset(long offset, byte[] b);
	
	default void putByteIndex(long index, byte[] b) {
		putByteOffset(index << PrimitiveType.BYTE.SHIFT, b);
	}
	
	default void putByte(byte[] b) {
		putByteOffset(0, b);
	}
	
	//byte fill
	void fillByteOffset(long offset, long length, byte b);
	
	//short
	short getShortOffset(long offset);
	
	default short getShortIndex(long index) {
		return getShortOffset(index << PrimitiveType.INT16.SHIFT);
	}
	
	void getShortOffset(long offset, short[] b);
	
	default void getShortIndex(long index, short[] b) {
		getShortOffset(index << PrimitiveType.INT16.SHIFT, b);
	}
	
	void putShortOffset(long offset, short b);
	
	default void putShortIndex(long index, short b) {
		putShortOffset(index << PrimitiveType.INT16.SHIFT, b);
	}
	
	void putShortOffset(long offset, short[] b);
	
	default void putShortIndex(long index, short[] b) {
		putShortOffset(index << PrimitiveType.INT16.SHIFT, b);
	}
	
	default void getShort(short[] b) {
		getShortOffset(0, b);
	}
	
	default void putShort(short[] b) {
		putShortOffset(0, b);
	}
	
	//int
	int getIntOffset(long offset);
	
	default int getIntIndex(long index) {
		return getIntOffset(index << PrimitiveType.INT32.SHIFT);
	}
	
	void getIntOffset(long offset, int[] b);
	
	default void getIntIndex(long index, int[] b) {
		getIntOffset(index << PrimitiveType.INT32.SHIFT, b);
	}
	
	void putIntOffset(long offset, int b);
	
	default void putIntIndex(long index, int b) {
		putIntOffset(index << PrimitiveType.INT32.SHIFT, b);
	}
	
	void putIntOffset(long offset, int[] b);
	
	default void putIntIndex(long index, int[] b) {
		putIntOffset(index << PrimitiveType.INT32.SHIFT, b);
	}
	
	default void getInt(int[] b) {
		getIntOffset(0, b);
	}
	
	default void putInt(int[] b) {
		putIntOffset(0, b);
	}
	
	//long
	long getLongOffset(long offset);
	
	default long getLongIndex(long index) {
		return getLongOffset(index << PrimitiveType.INT64.SHIFT);
	}
	
	void getLongOffset(long offset, long[] b);
	
	default void getLongIndex(long index, long[] b) {
		getLongOffset(index << PrimitiveType.INT64.SHIFT, b);
	}
	
	void putLongOffset(long offset, long b);
	
	default void putLongIndex(long index, long b) {
		putLongOffset(index << PrimitiveType.INT64.SHIFT, b);
	}
	
	void putLongOffset(long offset, long[] b);
	
	default void putLongIndex(long index, long[] b) {
		putLongOffset(index << PrimitiveType.INT64.SHIFT, b);
	}
	
	default void getLong(long[] b) {
		getLongOffset(0, b);
	}
	
	default void putLong(long[] b) {
		putLongOffset(0, b);
	}
	
	//float
	float getFloatOffset(long offset);
	
	default float getFloatIndex(long index) {
		return getFloatOffset(index << PrimitiveType.FP32.SHIFT);
	}
	
	void getFloatOffset(long offset, float[] b);
	
	default void getFloatIndex(long index, float[] b) {
		getFloatOffset(index << PrimitiveType.FP32.SHIFT, b);
	}
	
	void putFloatOffset(long offset, float b);
	
	default void putFloatIndex(long index, float b) {
		putFloatOffset(index << PrimitiveType.FP32.SHIFT, b);
	}
	
	void putFloatOffset(long offset, float[] b);
	
	default void putFloatIndex(long index, float[] b) {
		putFloatOffset(index << PrimitiveType.FP32.SHIFT, b);
	}
	
	default void getFloat(float[] b) {
		getFloatOffset(0, b);
	}
	
	default void putFloat(float[] b) {
		putFloatOffset(0, b);
	}
	
	//double
	double getDoubleOffset(long offset);
	
	default double getDoubleIndex(long index) {
		return getDoubleOffset(index << PrimitiveType.FP64.SHIFT);
	}
	
	void getDoubleOffset(long offset, double[] b);
	
	default void getDoubleIndex(long index, double[] b) {
		getDoubleOffset(index << PrimitiveType.FP64.SHIFT, b);
	}
	
	void putDoubleOffset(long offset, double b);
	
	default void putDoubleIndex(long index, double b) {
		putDoubleOffset(index << PrimitiveType.FP64.SHIFT, b);
	}
	
	void putDoubleOffset(long offset, double[] b);
	
	default void putDoubleIndex(long index, double[] b) {
		putDoubleOffset(index << PrimitiveType.FP64.SHIFT, b);
	}
	
	default void getDouble(double[] b) {
		getDoubleOffset(0, b);
	}
	
	default void putDouble(double[] b) {
		putDoubleOffset(0, b);
	}
	
	//boolean
	boolean getBooleanOffset(long offset);
	
	default boolean getBooleanIndex(long index) {
		return getBooleanOffset(index << PrimitiveType.BOOLEAN.SHIFT);
	}
	
	void getBooleanOffset(long offset, boolean[] b);
	
	default void getBooleanIndex(long index, boolean[] b) {
		getBooleanOffset(index << PrimitiveType.BOOLEAN.SHIFT, b);
	}
	
	void putBooleanOffset(long offset, boolean b);
	
	default void putBooleanIndex(long index, boolean b) {
		putBooleanOffset(index << PrimitiveType.BOOLEAN.SHIFT, b);
	}
	
	void putBooleanOffset(long offset, boolean[] b);
	
	default void putBooleanIndex(long index, boolean[] b) {
		putBooleanOffset(index << PrimitiveType.BOOLEAN.SHIFT, b);
	}
	
	default void getBoolean(boolean[] b) {
		getBooleanOffset(0, b);
	}
	
	default void putBoolean(boolean[] b) {
		putBooleanOffset(0, b);
	}
}
