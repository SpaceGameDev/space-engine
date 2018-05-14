package space.util.string.builder;

import space.util.annotation.Self;

public interface IStringBuilder1D<@Self SELF extends IStringBuilder1D<SELF>> extends IStringBuilder<SELF>, CharSequence {
	
	//pos
	SELF setLength(int length);
	
	default SELF reduceLength(int length) {
		return setLength(length() - length);
	}
	
	default SELF addLength(int length) {
		return setLength(length() + length);
	}
	
	//get result
	char[] getChars();
	
	@Override
	default int length() {
		return getChars().length;
	}
	
	@Override
	default char charAt(int index) {
		return getChars()[index];
	}
	
	@Override
	default CharSequence subSequence(int start, int end) {
		return new String(getChars(), start, end - start);
	}
}
