package space.engine.string.builder;

import org.jetbrains.annotations.NotNull;
import space.engine.annotation.Self;

public interface IStringBuilder1D<@Self SELF extends IStringBuilder1D<SELF>> extends IStringBuilder<SELF>, CharSequence {
	
	//pos
	@NotNull SELF setLength(int length);
	
	@NotNull
	default SELF reduceLength(int length) {
		return setLength(length() - length);
	}
	
	@NotNull
	default SELF addLength(int length) {
		return setLength(length() + length);
	}
	
	//get result
	@NotNull char[] getChars();
	
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
