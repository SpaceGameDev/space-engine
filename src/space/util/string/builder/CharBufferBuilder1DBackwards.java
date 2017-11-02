package space.util.string.builder;

import space.util.annotation.Self;

import java.util.Arrays;

public class CharBufferBuilder1DBackwards<@Self SELF extends CharBufferBuilder1D<SELF>> extends CharBufferBuilder1D<SELF> {
	
	public CharBufferBuilder1DBackwards() {
	}
	
	public CharBufferBuilder1DBackwards(int size) {
		super(size);
	}
	
	public CharBufferBuilder1DBackwards(char[] buffer) {
		super(buffer);
	}
	
	@Override
	public boolean ensureCapacity(int capa) {
		if (pos < 0) {
			int newSizeOld = buffer.length << expandShift;
			char[] n = new char[capa >= newSizeOld ? capa << expandShift : newSizeOld];
			System.arraycopy(buffer, 0, n, buffer.length, buffer.length);
			pos += n.length;
			buffer = n;
			return true;
		}
		return false;
	}
	
	@Override
	public SELF append(String str) {
		int l = str.length();
		int start = pos - l;
		ensureCapacity(start);
		str.getChars(0, l, buffer, start);
		pos = start;
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF append(char[] str) {
		int l = str.length;
		int start = pos - l;
		ensureCapacity(start);
		System.arraycopy(str, 0, buffer, start, l);
		pos = start;
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF append(char c) {
		ensureCapacityIndex(pos);
		buffer[pos] = c;
		pos--;
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public SELF fill(int l, char c) {
		int start = pos - l;
		ensureCapacity(start);
		Arrays.fill(buffer, start, pos, c);
		pos = start;
		//noinspection unchecked
		return (SELF) this;
	}
	
	@Override
	public CharSequence subSequence(int start, int end) {
		if (start < pos)
			throw new IndexOutOfBoundsException("start " + start + " was tinier than " + pos);
		return new String(buffer, start, end - start);
	}
	
	@Override
	public String toString() {
		return new String(buffer, pos, buffer.length - pos);
	}
}
