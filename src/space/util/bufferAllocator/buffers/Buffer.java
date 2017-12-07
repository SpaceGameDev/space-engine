package space.util.bufferAllocator.buffers;

import space.util.base.MainInstance;
import space.util.releasable.IReleasable;
import space.util.releasable.IReleasableWrapper;
import sun.misc.Unsafe;

import static spaceOld.util.UnsafeInstance.unsafe;

public class Buffer implements IBuffer, IReleasableWrapper {
	
	public BufferStorage storage;
	
	public Buffer() {
	
	}
	
	public Buffer(BufferStorage storage) {
		this.storage = storage;
	}
	
	public Buffer(long capacity) {
		this.storage = new BufferStorage(capacity);
	}
	
	public Buffer(long address, long capacity) {
		this.storage = new BufferStorage(address, capacity);
	}
	
	@Override
	public IBuffer addTracking() {
		MainInstance.getMain().general.releaseTracker.addWrapper(this);
		return this;
	}
	
	@Override
	public IReleasable getReleasable() {
		return storage;
	}
	
	@Override
	public long address() {
		return storage.address;
	}
	
	@Override
	public long capacity() {
		return storage.capacity;
	}
	
	@Override
	public int capacityInt() {
		return (int) storage.capacity;
	}
	
	@Override
	public void free() {
		storage.release();
	}
	
	@Override
	public void clear() {
		unsafe.setMemory(storage.address, storage.capacity, (byte) 0);
	}
	
	@Override
	public byte getByteOffset(long offset) {
		return unsafe.getByte(storage.address + offset);
	}
	
	@Override
	public void getByteOffset(long offset, byte[] b) {
		unsafe.copyMemory(null, storage.address + offset, b, Unsafe.ARRAY_BYTE_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putByteOffset(long offset, byte b) {
		unsafe.putByte(storage.address + offset, b);
	}
	
	@Override
	public void putByteOffset(long offset, byte[] b) {
		unsafe.copyMemory(b, Unsafe.ARRAY_BYTE_BASE_OFFSET, null, storage.address + offset, b.length);
	}
	
	@Override
	public short getShortOffset(long offset) {
		return unsafe.getShort(storage.address + offset);
	}
	
	@Override
	public void getShortOffset(long offset, short[] b) {
		unsafe.copyMemory(null, storage.address + offset, b, Unsafe.ARRAY_SHORT_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putShortOffset(long offset, short b) {
		unsafe.putShort(storage.address + offset, b);
	}
	
	@Override
	public void putShortOffset(long offset, short[] b) {
		unsafe.copyMemory(b, Unsafe.ARRAY_SHORT_BASE_OFFSET, null, storage.address + offset, b.length);
	}
	
	@Override
	public int getIntOffset(long offset) {
		return unsafe.getInt(storage.address + offset);
	}
	
	@Override
	public void getIntOffset(long offset, int[] b) {
		unsafe.copyMemory(null, storage.address + offset, b, Unsafe.ARRAY_INT_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putIntOffset(long offset, int b) {
		unsafe.putInt(storage.address + offset, b);
	}
	
	@Override
	public void putIntOffset(long offset, int[] b) {
		unsafe.copyMemory(b, Unsafe.ARRAY_INT_BASE_OFFSET, null, storage.address + offset, b.length);
	}
	
	@Override
	public long getLongOffset(long offset) {
		return unsafe.getLong(storage.address + offset);
	}
	
	@Override
	public void getLongOffset(long offset, long[] b) {
		unsafe.copyMemory(null, storage.address + offset, b, Unsafe.ARRAY_LONG_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putLongOffset(long offset, long b) {
		unsafe.putLong(storage.address + offset, b);
	}
	
	@Override
	public void putLongOffset(long offset, long[] b) {
		unsafe.copyMemory(b, Unsafe.ARRAY_LONG_BASE_OFFSET, null, storage.address + offset, b.length);
	}
	
	@Override
	public float getFloatOffset(long offset) {
		return unsafe.getFloat(storage.address + offset);
	}
	
	@Override
	public void getFloatOffset(long offset, float[] b) {
		unsafe.copyMemory(null, storage.address + offset, b, Unsafe.ARRAY_FLOAT_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putFloatOffset(long offset, float b) {
		unsafe.putFloat(storage.address + offset, b);
	}
	
	@Override
	public void putFloatOffset(long offset, float[] b) {
		unsafe.copyMemory(b, Unsafe.ARRAY_FLOAT_BASE_OFFSET, null, storage.address + offset, b.length);
	}
	
	@Override
	public double getDoubleOffset(long offset) {
		return unsafe.getDouble(storage.address + offset);
	}
	
	@Override
	public void getDoubleOffset(long offset, double[] b) {
		unsafe.copyMemory(null, storage.address + offset, b, Unsafe.ARRAY_DOUBLE_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putDoubleOffset(long offset, double b) {
		unsafe.putDouble(storage.address + offset, b);
	}
	
	@Override
	public void putDoubleOffset(long offset, double[] b) {
		unsafe.copyMemory(b, Unsafe.ARRAY_DOUBLE_BASE_OFFSET, null, storage.address + offset, b.length);
	}
	
	@Override
	public boolean getBooleanOffset(long offset) {
		return unsafe.getByte(storage.address + offset) != 0;
	}
	
	@Override
	public void getBooleanOffset(long offset, boolean[] b) {
		unsafe.copyMemory(null, storage.address + offset, b, Unsafe.ARRAY_BOOLEAN_BASE_OFFSET, b.length);
	}
	
	@Override
	public void putBooleanOffset(long offset, boolean b) {
		unsafe.putByte(storage.address + offset, (byte) (b ? 1 : 0));
	}
	
	@Override
	public void putBooleanOffset(long offset, boolean[] b) {
		unsafe.copyMemory(b, Unsafe.ARRAY_BOOLEAN_BASE_OFFSET, null, storage.address + offset, b.length);
	}
}
