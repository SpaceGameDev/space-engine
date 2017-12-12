package space.util.buffer.buffers;

public class CheckedBuffer extends Buffer {
	
	public CheckedBuffer(long capacity) {
		super(capacity);
	}
	
	public CheckedBuffer(long address, long capacity) {
		super(address, capacity);
	}
	
	//capacity checks
	public boolean hasCapacity(long offset) {
		return 0 <= offset && offset < capacity;
	}
	
	public boolean hasCapacity(long offset, long length) {
		return 0 <= offset && length + offset <= capacity;
	}
	
	public void requireCapacity(long offset) {
		if (!hasCapacity(offset))
			throw new BufferOutOfBoundsException(address, capacity, offset);
	}
	
	public void requireCapacity(long offset, long length) {
		if (!hasCapacity(offset, length))
			throw new BufferOutOfBoundsException(address, capacity, offset, length);
	}
	
	//checks
	@Override
	public byte getByte(long offset) {
		requireCapacity(offset);
		requireCapacity(offset);
		return super.getByte(offset);
	}
	
	@Override
	public void putByte(long offset, byte b) {
		requireCapacity(offset);
		super.putByte(offset, b);
	}
	
	@Override
	public short getShort(long offset) {
		requireCapacity(offset);
		return super.getShort(offset);
	}
	
	@Override
	public void putShort(long offset, short b) {
		requireCapacity(offset);
		super.putShort(offset, b);
	}
	
	@Override
	public int getInt(long offset) {
		requireCapacity(offset);
		return super.getInt(offset);
	}
	
	@Override
	public void putInt(long offset, int b) {
		requireCapacity(offset);
		super.putInt(offset, b);
	}
	
	@Override
	public long getLong(long offset) {
		requireCapacity(offset);
		return super.getLong(offset);
	}
	
	@Override
	public void putLong(long offset, long b) {
		requireCapacity(offset);
		super.putLong(offset, b);
	}
	
	@Override
	public float getFloat(long offset) {
		requireCapacity(offset);
		return super.getFloat(offset);
	}
	
	@Override
	public void putFloat(long offset, float b) {
		requireCapacity(offset);
		super.putFloat(offset, b);
	}
	
	@Override
	public double getDouble(long offset) {
		requireCapacity(offset);
		return super.getDouble(offset);
	}
	
	@Override
	public void putDouble(long offset, double b) {
		requireCapacity(offset);
		super.putDouble(offset, b);
	}
	
	@Override
	public boolean getBoolean(long offset) {
		requireCapacity(offset);
		return super.getBoolean(offset);
	}
	
	@Override
	public void putBoolean(long offset, boolean b) {
		requireCapacity(offset);
		super.putBoolean(offset, b);
	}
	
	@Override
	public void copyInto(long offset, byte[] dest, int destPos, int length) {
		requireCapacity(offset, length);
		super.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(byte[] src, int srcPos, int length, long offset) {
		requireCapacity(offset, length);
		super.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void copyInto(long offset, short[] dest, int destPos, int length) {
		requireCapacity(offset, length);
		super.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(short[] src, int srcPos, int length, long offset) {
		requireCapacity(offset, length);
		super.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void copyInto(long offset, int[] dest, int destPos, int length) {
		requireCapacity(offset, length);
		super.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(int[] src, int srcPos, int length, long offset) {
		requireCapacity(offset, length);
		super.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void copyInto(long offset, long[] dest, int destPos, int length) {
		requireCapacity(offset, length);
		super.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(long[] src, int srcPos, int length, long offset) {
		requireCapacity(offset, length);
		super.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void copyInto(long offset, float[] dest, int destPos, int length) {
		requireCapacity(offset, length);
		super.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(float[] src, int srcPos, int length, long offset) {
		requireCapacity(offset, length);
		super.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void copyInto(long offset, double[] dest, int destPos, int length) {
		requireCapacity(offset, length);
		super.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(double[] src, int srcPos, int length, long offset) {
		requireCapacity(offset, length);
		super.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void copyInto(long offset, boolean[] dest, int destPos, int length) {
		requireCapacity(offset, length);
		super.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(boolean[] src, int srcPos, int length, long offset) {
		requireCapacity(offset, length);
		super.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void fillByte(long offset, byte b, long length) {
		requireCapacity(offset, length);
		super.fillByte(offset, b, length);
	}
	
	@Override
	public void fillShort(long offset, short b, long length) {
		requireCapacity(offset, length);
		super.fillShort(offset, b, length);
	}
	
	@Override
	public void fillInt(long offset, int b, long length) {
		requireCapacity(offset, length);
		super.fillInt(offset, b, length);
	}
	
	@Override
	public void fillLong(long offset, long b, long length) {
		requireCapacity(offset, length);
		super.fillLong(offset, b, length);
	}
	
	@Override
	public void fillFloat(long offset, float b, long length) {
		requireCapacity(offset, length);
		super.fillFloat(offset, b, length);
	}
	
	@Override
	public void fillDouble(long offset, double b, long length) {
		requireCapacity(offset, length);
		super.fillDouble(offset, b, length);
	}
	
	@Override
	public void fillBoolean(long offset, boolean b, long length) {
		requireCapacity(offset, length);
		super.fillBoolean(offset, b, length);
	}
	
	@Override
	public void copyInto(long offset, IBuffer dest, long destPos, long length) {
		requireCapacity(offset, length);
		super.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(IBuffer src, long srcPos, long length, long offset) {
		requireCapacity(offset, length);
		super.copyFrom(src, srcPos, length, offset);
	}
}
