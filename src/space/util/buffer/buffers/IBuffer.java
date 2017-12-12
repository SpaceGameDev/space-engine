package space.util.buffer.buffers;

import space.util.baseobject.additional.IReleasable;

public interface IBuffer extends IReleasable {
	
	long address();
	
	long capacity();
	
	@Override
	void release();
	
	void clear();
	
	//single
	//byte
	byte getByte(long offset);
	
	void putByte(long offset, byte b);
	
	//short
	short getShort(long offset);
	
	void putShort(long offset, short b);
	
	//int
	int getInt(long offset);
	
	void putInt(long offset, int b);
	
	//long
	long getLong(long offset);
	
	void putLong(long offset, long b);
	
	//float
	float getFloat(long offset);
	
	void putFloat(long offset, float b);
	
	//double
	double getDouble(long offset);
	
	void putDouble(long offset, double b);
	
	//boolean
	boolean getBoolean(long offset);
	
	void putBoolean(long offset, boolean b);
	
	//arrays
	//byte array
	default void copyInto(byte[] dest) {
		copyInto(0, dest, 0, dest.length);
	}
	
	default void copyInto(byte[] dest, long offset) {
		copyInto(offset, dest, 0, dest.length);
	}
	
	void copyInto(long offset, byte[] dest, int destPos, int length);
	
	default void copyFrom(byte[] src) {
		copyFrom(src, 0, src.length, 0);
	}
	
	default void copyFrom(byte[] src, long offset) {
		copyFrom(src, 0, src.length, offset);
	}
	
	void copyFrom(byte[] src, int srcPos, int length, long offset);
	
	//short array
	default void copyInto(short[] dest) {
		copyInto(0, dest, 0, dest.length);
	}
	
	default void copyInto(short[] dest, long offset) {
		copyInto(offset, dest, 0, dest.length);
	}
	
	void copyInto(long offset, short[] dest, int destPos, int length);
	
	default void copyFrom(short[] src) {
		copyFrom(src, 0, src.length, 0);
	}
	
	default void copyFrom(short[] src, long offset) {
		copyFrom(src, 0, src.length, offset);
	}
	
	void copyFrom(short[] src, int srcPos, int length, long offset);
	
	//int array
	default void copyInto(int[] dest) {
		copyInto(0, dest, 0, dest.length);
	}
	
	default void copyInto(int[] dest, long offset) {
		copyInto(offset, dest, 0, dest.length);
	}
	
	void copyInto(long offset, int[] dest, int destPos, int length);
	
	default void copyFrom(int[] src) {
		copyFrom(src, 0, src.length, 0);
	}
	
	default void copyFrom(int[] src, long offset) {
		copyFrom(src, 0, src.length, offset);
	}
	
	void copyFrom(int[] src, int srcPos, int length, long offset);
	
	//long array
	default void copyInto(long[] dest) {
		copyInto(0, dest, 0, dest.length);
	}
	
	default void copyInto(long[] dest, long offset) {
		copyInto(offset, dest, 0, dest.length);
	}
	
	void copyInto(long offset, long[] dest, int destPos, int length);
	
	default void copyFrom(long[] src) {
		copyFrom(src, 0, src.length, 0);
	}
	
	default void copyFrom(long[] src, long offset) {
		copyFrom(src, 0, src.length, offset);
	}
	
	void copyFrom(long[] src, int srcPos, int length, long offset);
	
	//float array
	default void copyInto(float[] dest) {
		copyInto(0, dest, 0, dest.length);
	}
	
	default void copyInto(float[] dest, long offset) {
		copyInto(offset, dest, 0, dest.length);
	}
	
	void copyInto(long offset, float[] dest, int destPos, int length);
	
	default void copyFrom(float[] src) {
		copyFrom(src, 0, src.length, 0);
	}
	
	default void copyFrom(float[] src, long offset) {
		copyFrom(src, 0, src.length, offset);
	}
	
	void copyFrom(float[] src, int srcPos, int length, long offset);
	
	//double array
	default void copyInto(double[] dest) {
		copyInto(0, dest, 0, dest.length);
	}
	
	default void copyInto(double[] dest, long offset) {
		copyInto(offset, dest, 0, dest.length);
	}
	
	void copyInto(long offset, double[] dest, int destPos, int length);
	
	default void copyFrom(double[] src) {
		copyFrom(src, 0, src.length, 0);
	}
	
	default void copyFrom(double[] src, long offset) {
		copyFrom(src, 0, src.length, offset);
	}
	
	void copyFrom(double[] src, int srcPos, int length, long offset);
	
	//boolean array
	default void copyInto(boolean[] dest) {
		copyInto(0, dest, 0, dest.length);
	}
	
	default void copyInto(boolean[] dest, long offset) {
		copyInto(offset, dest, 0, dest.length);
	}
	
	void copyInto(long offset, boolean[] dest, int destPos, int length);
	
	default void copyFrom(boolean[] src) {
		copyFrom(src, 0, src.length, 0);
	}
	
	default void copyFrom(boolean[] src, long offset) {
		copyFrom(src, 0, src.length, offset);
	}
	
	void copyFrom(boolean[] src, int srcPos, int length, long offset);
	
	//fill
	//byte
	void fillByte(long offset, byte b, long length);
	
	//short
	void fillShort(long offset, short b, long length);
	
	//int
	void fillInt(long offset, int b, long length);
	
	//long
	void fillLong(long offset, long b, long length);
	
	//float
	void fillFloat(long offset, float b, long length);
	
	//double
	void fillDouble(long offset, double b, long length);
	
	//boolean
	void fillBoolean(long offset, boolean b, long length);
	
	//buffers
	default void copyInto(long offset, IBuffer dest) {
		copyInto(offset, dest, 0, dest.capacity());
	}
	
	void copyInto(long offset, IBuffer dest, long destPos, long length);
	
	default void copyFrom(IBuffer src, long offset) {
		copyFrom(src, 0, src.capacity(), offset);
	}
	
	void copyFrom(IBuffer src, long srcPos, long length, long offset);
}
