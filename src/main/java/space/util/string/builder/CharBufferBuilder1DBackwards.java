package space.util.string.builder;

import space.util.ArrayUtils;
import space.util.annotation.Self;

import java.util.Arrays;

public class CharBufferBuilder1DBackwards<@Self SELF extends CharBufferBuilder1D<SELF>> extends CharBufferBuilder1D<SELF> {
	
	public CharBufferBuilder1DBackwards() {
		pos = buffer.length;
	}
	
	public CharBufferBuilder1DBackwards(int size) {
		super(size);
		pos = buffer.length;
	}
	
	public CharBufferBuilder1DBackwards(char[] buffer) {
		super(buffer);
		pos = buffer.length;
	}
	
	//ensureCapacity
	@Override
	@Deprecated
	public boolean ensureCapacityIndex(int index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	@Deprecated
	public boolean ensureCapacity(int capa) {
		throw new UnsupportedOperationException();
	}
	
	public int ensureCapacityBackwardIndex(int index) {
		return ensureCapacityBackward(index - 1);
	}
	
	public int ensureCapacityBackward(int capa) {
		if (capa < 0) {
			char[] old = buffer;
			buffer = new char[ArrayUtils.getOptimalArraySizeExpansion(old.length, capa, 1)];
			int exp = buffer.length - old.length;
			System.arraycopy(old, 0, buffer, exp, old.length);
			
			pos += exp;
			return capa + exp;
		}
		return capa;
	}
	
	//length
	@Override
	public SELF setLength(int length) {
		pos = buffer.length - length;
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF addLength(int length) {
		pos -= length;
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF reduceLength(int length) {
		pos += length;
		//noinspection unchecked
		return (SELF) this;
	}
	
	//append
	@Override
	public SELF append(String str) {
		int l = str.length();
		int start = ensureCapacityBackward(pos - l);
		str.getChars(0, l, buffer, start);
		pos = start;
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF append(char[] str) {
		int l = str.length;
		int start = ensureCapacityBackward(pos - l);
		System.arraycopy(str, 0, buffer, start, l);
		pos = start;
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF append(char c) {
		int start = ensureCapacityBackward(pos - 1);
		buffer[start] = c;
		pos = start;
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF fill(int l, char c) {
		int start = ensureCapacityBackward(pos - l);
		Arrays.fill(buffer, start, pos, c);
		pos = start;
		//noinspection unchecked
		return (SELF) this;
	}
	
	//toString
	@Override
	public int length() {
		return buffer.length - pos;
	}
	
	@Override
	public char charAt(int index) {
		return buffer[pos + index];
	}
	
	@Override
	public CharSequence subSequence(int start, int end) {
		if (start < pos)
			throw new IndexOutOfBoundsException("start " + start + " was tinier than " + pos);
		return new String(buffer, start, end - start);
	}
	
	@Override
	public char[] getChars() {
		return Arrays.copyOfRange(buffer, pos, buffer.length);
	}
	
	@Override
	public String toString() {
		return new String(buffer, pos, buffer.length - pos);
	}
}
