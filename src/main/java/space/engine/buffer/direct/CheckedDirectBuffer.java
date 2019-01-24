package space.engine.buffer.direct;

import org.jetbrains.annotations.NotNull;
import space.engine.buffer.exception.BufferAddressNullException;
import space.engine.buffer.exception.BufferOutOfBoundsException;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.string.String2D;

/**
 * Wraps a {@link DirectBuffer} and checks all parameters,
 * throwing exceptions like {@link BufferOutOfBoundsException} or {@link BufferAddressNullException} when something is wrong.
 */
public class CheckedDirectBuffer implements DirectBuffer {
	
	public DirectBuffer buffer;
	
	public CheckedDirectBuffer(DirectBuffer buffer) {
		this.buffer = buffer;
	}
	
	//capacity checks
	public void checkOrThrow(long offset) {
		long address = buffer.address();
		long capacity = buffer.capacity();
		
		if (address == 0)
			throw new BufferAddressNullException();
		if (!(0 <= offset && offset < capacity))
			throw new BufferOutOfBoundsException(address, capacity, offset);
	}
	
	public void checkOrThrow(long offset, long length) {
		long address = buffer.address();
		long capacity = buffer.capacity();
		
		if (address == 0)
			throw new BufferAddressNullException();
		if (!(0 <= offset && offset + length <= capacity))
			throw new BufferOutOfBoundsException(address, capacity, offset, length);
	}
	
	//delegates
	@NotNull
	@Override
	public FreeableStorage getStorage() {
		return buffer.getStorage();
	}
	
	@Override
	public long address() {
		return buffer.address();
	}
	
	@Override
	public long capacity() {
		return buffer.capacity();
	}
	
	@Override
	public void clear() {
		buffer.clear();
	}
	
	@NotNull
	@Override
	public String2D dump() {
		return buffer.dump();
	}
	
	//checked delegates
	@Override
	public byte getByte(long offset) {
		checkOrThrow(offset);
		return buffer.getByte(offset);
	}
	
	@Override
	public void putByte(long offset, byte b) {
		checkOrThrow(offset);
		buffer.putByte(offset, b);
	}
	
	@Override
	public short getShort(long offset) {
		checkOrThrow(offset);
		return buffer.getShort(offset);
	}
	
	@Override
	public void putShort(long offset, short b) {
		checkOrThrow(offset);
		buffer.putShort(offset, b);
	}
	
	@Override
	public int getInt(long offset) {
		checkOrThrow(offset);
		return buffer.getInt(offset);
	}
	
	@Override
	public void putInt(long offset, int b) {
		checkOrThrow(offset);
		buffer.putInt(offset, b);
	}
	
	@Override
	public long getLong(long offset) {
		checkOrThrow(offset);
		return buffer.getLong(offset);
	}
	
	@Override
	public void putLong(long offset, long b) {
		checkOrThrow(offset);
		buffer.putLong(offset, b);
	}
	
	@Override
	public float getFloat(long offset) {
		checkOrThrow(offset);
		return buffer.getFloat(offset);
	}
	
	@Override
	public void putFloat(long offset, float b) {
		checkOrThrow(offset);
		buffer.putFloat(offset, b);
	}
	
	@Override
	public double getDouble(long offset) {
		checkOrThrow(offset);
		return buffer.getDouble(offset);
	}
	
	@Override
	public void putDouble(long offset, double b) {
		checkOrThrow(offset);
		buffer.putDouble(offset, b);
	}
	
	@Override
	public boolean getBoolean(long offset) {
		checkOrThrow(offset);
		return buffer.getBoolean(offset);
	}
	
	@Override
	public void putBoolean(long offset, boolean b) {
		checkOrThrow(offset);
		buffer.putBoolean(offset, b);
	}
	
	@Override
	public long getPointer(long offset) {
		checkOrThrow(offset);
		return buffer.getPointer(offset);
	}
	
	@Override
	public void putPointer(long offset, long b) {
		checkOrThrow(offset);
		buffer.putPointer(offset, b);
	}
	
	@Override
	public void copyInto(long offset, byte[] dest, int destPos, int length) {
		checkOrThrow(offset, length);
		buffer.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(byte[] src, int srcPos, int length, long offset) {
		checkOrThrow(offset, length);
		buffer.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void copyInto(long offset, short[] dest, int destPos, int length) {
		checkOrThrow(offset, length);
		buffer.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(short[] src, int srcPos, int length, long offset) {
		checkOrThrow(offset, length);
		buffer.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void copyInto(long offset, int[] dest, int destPos, int length) {
		checkOrThrow(offset, length);
		buffer.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(int[] src, int srcPos, int length, long offset) {
		checkOrThrow(offset, length);
		buffer.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void copyInto(long offset, long[] dest, int destPos, int length) {
		checkOrThrow(offset, length);
		buffer.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(long[] src, int srcPos, int length, long offset) {
		checkOrThrow(offset, length);
		buffer.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void copyInto(long offset, float[] dest, int destPos, int length) {
		checkOrThrow(offset, length);
		buffer.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(float[] src, int srcPos, int length, long offset) {
		checkOrThrow(offset, length);
		buffer.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void copyInto(long offset, double[] dest, int destPos, int length) {
		checkOrThrow(offset, length);
		buffer.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(double[] src, int srcPos, int length, long offset) {
		checkOrThrow(offset, length);
		buffer.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void copyInto(long offset, boolean[] dest, int destPos, int length) {
		checkOrThrow(offset, length);
		buffer.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(boolean[] src, int srcPos, int length, long offset) {
		checkOrThrow(offset, length);
		buffer.copyFrom(src, srcPos, length, offset);
	}
	
	@Override
	public void fillByte(long offset, byte b, long length) {
		checkOrThrow(offset, length);
		buffer.fillByte(offset, b, length);
	}
	
	@Override
	public void fillShort(long offset, short b, long length) {
		checkOrThrow(offset, length);
		buffer.fillShort(offset, b, length);
	}
	
	@Override
	public void fillInt(long offset, int b, long length) {
		checkOrThrow(offset, length);
		buffer.fillInt(offset, b, length);
	}
	
	@Override
	public void fillLong(long offset, long b, long length) {
		checkOrThrow(offset, length);
		buffer.fillLong(offset, b, length);
	}
	
	@Override
	public void fillFloat(long offset, float b, long length) {
		checkOrThrow(offset, length);
		buffer.fillFloat(offset, b, length);
	}
	
	@Override
	public void fillDouble(long offset, double b, long length) {
		checkOrThrow(offset, length);
		buffer.fillDouble(offset, b, length);
	}
	
	@Override
	public void fillBoolean(long offset, boolean b, long length) {
		checkOrThrow(offset, length);
		buffer.fillBoolean(offset, b, length);
	}
	
	@Override
	public void copyInto(long offset, DirectBuffer dest, long destPos, long length) {
		checkOrThrow(offset, length);
		buffer.copyInto(offset, dest, destPos, length);
	}
	
	@Override
	public void copyFrom(DirectBuffer src, long srcPos, long length, long offset) {
		checkOrThrow(offset, length);
		buffer.copyFrom(src, srcPos, length, offset);
	}
}
