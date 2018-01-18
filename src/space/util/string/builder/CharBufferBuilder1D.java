package space.util.string.builder;

import space.util.ArrayUtils;
import space.util.annotation.Self;

import java.util.Arrays;

public class CharBufferBuilder1D<@Self SELF extends CharBufferBuilder1D<SELF>> implements IStringBuilder1D<SELF> {
	
	public static final int defaultCapacity = 16;
	public static final int expandShift = 1;
	
	public char[] buffer;
	public int pos;
	
	public CharBufferBuilder1D() {
		this(defaultCapacity);
	}
	
	public CharBufferBuilder1D(int size) {
		this(new char[size]);
	}
	
	public CharBufferBuilder1D(char[] buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public boolean ensureCapacity(int capa) {
		if (capa > buffer.length) {
			buffer = Arrays.copyOf(buffer, ArrayUtils.getOptimalArraySizeExpansion(buffer.length, capa, expandShift));
			return true;
		}
		return false;
	}
	
	@Override
	public int length() {
		return pos;
	}
	
	@Override
	public SELF setLength(int length) {
		pos = length;
		ensureCapacity(length);
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF addLength(int length) {
		pos += length;
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF reduceLength(int length) {
		pos -= length;
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF append(String str) {
		int l = str.length();
		int start = pos;
		setLength(pos + l);
		str.getChars(0, l, buffer, start);
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF append(char[] str) {
		int l = str.length;
		int start = pos;
		setLength(pos + l);
		System.arraycopy(str, 0, buffer, start, l);
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF append(char c) {
		int start = pos;
		setLength(pos + 1);
		buffer[start] = c;
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF fill(int l, char c) {
		int start = pos;
		setLength(pos + l);
		Arrays.fill(buffer, start, pos, c);
		//noinspection unchecked
		return (SELF) this;
	}
	
	//get result
	@Override
	public char[] getChars() {
		return Arrays.copyOfRange(buffer, 0, pos);
	}
	
	@Override
	public String toString() {
		return new String(buffer, 0, pos);
	}
}
