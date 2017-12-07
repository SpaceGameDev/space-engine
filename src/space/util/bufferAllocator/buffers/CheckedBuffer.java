package space.util.bufferAllocator.buffers;

public class CheckedBuffer extends Buffer {
	
	public IBuffer buffer;
	
	public CheckedBuffer() {
	
	}
	
	public CheckedBuffer(IBuffer buffer) {
		this.buffer = buffer;
	}
	
	public boolean hasCapacity(long offset) {
		return offset >= 0 && storage.capacity > offset;
	}
	
	public boolean hasCapacity(long offset, long length) {
		return offset >= 0 && storage.capacity >= length + offset;
	}
	
	public void requireCapacity(long offset) {
		if (!hasCapacity(offset))
			throw new BufferOutOfBoundsException(offset);
	}
	
	public void requireCapacity(long offset, long length) {
		if (!hasCapacity(offset, length))
			throw new BufferOutOfBoundsException(offset, length);
	}
	
	@Override
	public byte getByteOffset(long offset) {
		requireCapacity(offset);
		return buffer.getByteOffset(offset);
	}
	
	@Override
	public void getByteOffset(long offset, byte[] b) {
		requireCapacity(offset, b.length);
		buffer.getByteOffset(offset, b);
	}
	
	@Override
	public void putByteOffset(long offset, byte b) {
		requireCapacity(offset);
		buffer.putByteOffset(offset, b);
	}
	
	@Override
	public void putByteOffset(long offset, byte[] b) {
		requireCapacity(offset, b.length);
		buffer.putByteOffset(offset, b);
	}
	
	@Override
	public short getShortOffset(long offset) {
		requireCapacity(offset);
		return buffer.getShortOffset(offset);
	}
	
	@Override
	public void getShortOffset(long offset, short[] b) {
		requireCapacity(offset, b.length);
		buffer.getShortOffset(offset, b);
	}
	
	@Override
	public void putShortOffset(long offset, short b) {
		requireCapacity(offset);
		buffer.putShortOffset(offset, b);
	}
	
	@Override
	public void putShortOffset(long offset, short[] b) {
		requireCapacity(offset, b.length);
		buffer.putShortOffset(offset, b);
	}
	
	@Override
	public int getIntOffset(long offset) {
		requireCapacity(offset);
		return buffer.getIntOffset(offset);
	}
	
	@Override
	public void getIntOffset(long offset, int[] b) {
		requireCapacity(offset, b.length);
		buffer.getIntOffset(offset, b);
	}
	
	@Override
	public void putIntOffset(long offset, int b) {
		requireCapacity(offset);
		buffer.putIntOffset(offset, b);
	}
	
	@Override
	public void putIntOffset(long offset, int[] b) {
		requireCapacity(offset, b.length);
		buffer.putIntOffset(offset, b);
	}
	
	@Override
	public long getLongOffset(long offset) {
		requireCapacity(offset);
		return buffer.getLongOffset(offset);
	}
	
	@Override
	public void getLongOffset(long offset, long[] b) {
		requireCapacity(offset, b.length);
		buffer.getLongOffset(offset, b);
	}
	
	@Override
	public void putLongOffset(long offset, long b) {
		requireCapacity(offset);
		buffer.putLongOffset(offset, b);
	}
	
	@Override
	public void putLongOffset(long offset, long[] b) {
		requireCapacity(offset, b.length);
		buffer.putLongOffset(offset, b);
	}
	
	@Override
	public float getFloatOffset(long offset) {
		requireCapacity(offset);
		return buffer.getFloatOffset(offset);
	}
	
	@Override
	public void getFloatOffset(long offset, float[] b) {
		requireCapacity(offset, b.length);
		buffer.getFloatOffset(offset, b);
	}
	
	@Override
	public void putFloatOffset(long offset, float b) {
		requireCapacity(offset);
		buffer.putFloatOffset(offset, b);
	}
	
	@Override
	public void putFloatOffset(long offset, float[] b) {
		requireCapacity(offset, b.length);
		buffer.putFloatOffset(offset, b);
	}
	
	@Override
	public double getDoubleOffset(long offset) {
		requireCapacity(offset);
		return buffer.getDoubleOffset(offset);
	}
	
	@Override
	public void getDoubleOffset(long offset, double[] b) {
		requireCapacity(offset, b.length);
		buffer.getDoubleOffset(offset, b);
	}
	
	@Override
	public void putDoubleOffset(long offset, double b) {
		requireCapacity(offset);
		buffer.putDoubleOffset(offset, b);
	}
	
	@Override
	public void putDoubleOffset(long offset, double[] b) {
		requireCapacity(offset, b.length);
		buffer.putDoubleOffset(offset, b);
	}
	
	@Override
	public boolean getBooleanOffset(long offset) {
		requireCapacity(offset);
		return buffer.getBooleanOffset(offset);
	}
	
	@Override
	public void getBooleanOffset(long offset, boolean[] b) {
		requireCapacity(offset, b.length);
		buffer.getBooleanOffset(offset, b);
	}
	
	@Override
	public void putBooleanOffset(long offset, boolean b) {
		requireCapacity(offset);
		buffer.putBooleanOffset(offset, b);
	}
	
	@Override
	public void putBooleanOffset(long offset, boolean[] b) {
		requireCapacity(offset, b.length);
		buffer.putBooleanOffset(offset, b);
	}
}
