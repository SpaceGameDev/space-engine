package space.util.bufferAllocator.buffers;

import sun.misc.Unsafe;

import static space.util.unsafe.UnsafeInstance.UNSAFE;

public class Buffer extends SimpleBuffer implements IBuffer {
	
	public Buffer(long capacity) {
		super(capacity);
	}
	
	public Buffer(long address, long capacity) {
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
	
	@Override
	public byte getByteOffset(long offset) {
		return UNSAFE.getByte(address + offset);
	}
	
	@Override
	public void getByteOffset(long offset, byte[] b) {
		UNSAFE.copyMemory(null, address + offset, b, Unsafe.ARRAY_BYTE_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putByteOffset(long offset, byte b) {
		UNSAFE.putByte(address + offset, b);
	}
	
	@Override
	public void putByteOffset(long offset, byte[] b) {
		UNSAFE.copyMemory(b, Unsafe.ARRAY_BYTE_BASE_OFFSET, null, address + offset, b.length);
	}
	
	@Override
	public short getShortOffset(long offset) {
		return UNSAFE.getShort(address + offset);
	}
	
	@Override
	public void getShortOffset(long offset, short[] b) {
		UNSAFE.copyMemory(null, address + offset, b, Unsafe.ARRAY_SHORT_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putShortOffset(long offset, short b) {
		UNSAFE.putShort(address + offset, b);
	}
	
	@Override
	public void putShortOffset(long offset, short[] b) {
		UNSAFE.copyMemory(b, Unsafe.ARRAY_SHORT_BASE_OFFSET, null, address + offset, b.length);
	}
	
	@Override
	public int getIntOffset(long offset) {
		return UNSAFE.getInt(address + offset);
	}
	
	@Override
	public void getIntOffset(long offset, int[] b) {
		UNSAFE.copyMemory(null, address + offset, b, Unsafe.ARRAY_INT_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putIntOffset(long offset, int b) {
		UNSAFE.putInt(address + offset, b);
	}
	
	@Override
	public void putIntOffset(long offset, int[] b) {
		UNSAFE.copyMemory(b, Unsafe.ARRAY_INT_BASE_OFFSET, null, address + offset, b.length);
	}
	
	@Override
	public long getLongOffset(long offset) {
		return UNSAFE.getLong(address + offset);
	}
	
	@Override
	public void getLongOffset(long offset, long[] b) {
		UNSAFE.copyMemory(null, address + offset, b, Unsafe.ARRAY_LONG_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putLongOffset(long offset, long b) {
		UNSAFE.putLong(address + offset, b);
	}
	
	@Override
	public void putLongOffset(long offset, long[] b) {
		UNSAFE.copyMemory(b, Unsafe.ARRAY_LONG_BASE_OFFSET, null, address + offset, b.length);
	}
	
	@Override
	public float getFloatOffset(long offset) {
		return UNSAFE.getFloat(address + offset);
	}
	
	@Override
	public void getFloatOffset(long offset, float[] b) {
		UNSAFE.copyMemory(null, address + offset, b, Unsafe.ARRAY_FLOAT_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putFloatOffset(long offset, float b) {
		UNSAFE.putFloat(address + offset, b);
	}
	
	@Override
	public void putFloatOffset(long offset, float[] b) {
		UNSAFE.copyMemory(b, Unsafe.ARRAY_FLOAT_BASE_OFFSET, null, address + offset, b.length);
	}
	
	@Override
	public double getDoubleOffset(long offset) {
		return UNSAFE.getDouble(address + offset);
	}
	
	@Override
	public void getDoubleOffset(long offset, double[] b) {
		UNSAFE.copyMemory(null, address + offset, b, Unsafe.ARRAY_DOUBLE_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putDoubleOffset(long offset, double b) {
		UNSAFE.putDouble(address + offset, b);
	}
	
	@Override
	public void putDoubleOffset(long offset, double[] b) {
		UNSAFE.copyMemory(b, Unsafe.ARRAY_DOUBLE_BASE_OFFSET, null, address + offset, b.length);
	}
	
	@Override
	public boolean getBooleanOffset(long offset) {
		return UNSAFE.getByte(address + offset) != 0;
	}
	
	@Override
	public void getBooleanOffset(long offset, boolean[] b) {
		UNSAFE.copyMemory(null, address + offset, b, Unsafe.ARRAY_BOOLEAN_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putBooleanOffset(long offset, boolean b) {
		UNSAFE.putByte(address + offset, (byte) (b ? 1 : 0));
	}
	
	@Override
	public void putBooleanOffset(long offset, boolean[] b) {
		UNSAFE.copyMemory(b, Unsafe.ARRAY_BOOLEAN_BASE_OFFSET, null, address + offset, b.length);
	}
}
