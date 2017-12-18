package space.util.number.big;

import space.util.math.BigMath;
import space.util.math.BigPrimitiveMath;
import space.util.number.exception.OverflowException;

import java.util.Arrays;

public class BigNumber {
	
	public int[] magnitude;
	
	public BigNumber() {
	}
	
	public BigNumber(int[] magnitude) {
		this.magnitude = magnitude;
	}
	
	//pharse
	public BigNumber(int magnitude) {
		this(BigPrimitiveMath.intArrayFromIntUnsigned(magnitude));
	}
	
	public BigNumber(long magnitude) {
		this(BigPrimitiveMath.intArrayFromLongUnsigned(magnitude));
	}
	
	//capacity get
	public long getCapacityNumberBit() {
		return magnitude.length * 32;
	}
	
	public int getCapacityNumber() {
		return magnitude.length;
	}
	
	//capacity ensure
	public void ensureCapacityNumberBit(long capacity) throws OverflowException {
		if (capacity < 0)
			throw new OverflowException();
		
		int arraySize = (int) (capacity / 32L);
		if (capacity % 32L != 0)
			arraySize++;
		ensureCapacityNumber(arraySize);
	}
	
	public void ensureCapacityNumber(long arraySize) throws OverflowException {
		if (arraySize > Integer.MAX_VALUE)
			throw new OverflowException();
		int capacity = (int) arraySize;
		if (magnitude.length >= capacity)
			return;
		
		int[] old = magnitude;
		magnitude = new int[capacity];
		System.arraycopy(old, 0, magnitude, 0, old.length);
		Arrays.fill(magnitude, old.length, magnitude.length, getFillNumber());
	}
	
	//set
	public void setNumber(int[] i) {
		ensureCapacityNumber(i.length);
		System.arraycopy(i, 0, magnitude, 0, i.length);
		if (magnitude.length > i.length)
			Arrays.fill(magnitude, i.length, magnitude.length, getFillNumber());
	}
	
	//simple math
	public BigNumber addUnsigned(BigNumber n) {
		BigMath.addUnsigned(magnitude, n.magnitude, this);
		return this;
	}
	
	public BigNumber subUnsigned(BigNumber n) {
		BigMath.addUnsigned(magnitude, n.magnitude, this);
		return this;
	}
	
	//util
	public BigNumber copy() {
		return new BigNumber(magnitude.clone());
	}
	
	public BigNumber trim() {
		magnitude = BigMath.trim(magnitude);
		return this;
	}
	
	public boolean isZero() {
		if (magnitude.length == 0)
			return true;
		
		for (int i : magnitude)
			if (i != 0)
				return false;
		return true;
	}
	
	public int getFillNumber() {
		return 0;
	}
	
	@Override
	public String toString() {
		if (magnitude.length == 0)
			return "0";
		return BigMath.toString(magnitude);
	}
}
