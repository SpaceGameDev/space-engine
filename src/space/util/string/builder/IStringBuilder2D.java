package space.util.string.builder;

import space.util.annotation.Self;
import space.util.delegate.impl.CharArrayStringIterable;
import space.util.string.CharSequence2D;
import space.util.string.ToString2D;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Iterator;

public interface IStringBuilder2D<@Self SELF extends IStringBuilder2D<SELF>> extends IStringBuilder<SELF>, Iterable<String>, CharSequence2D {
	
	//capacity height
	default boolean ensureHeightIndex(int index) {
		return ensureHeight(index + 1);
	}
	
	boolean ensureHeight(int height);
	
	//append
	SELF append(char[][] chars);
	
	/**
	 * fills the rest of the defined block [untily | untilx] (absolute coords) with fillup
	 */
	SELF append(char[][] chars, int untily, int untilx, char fillup);
	
	default SELF append(CharSequence2D b) {
		return append(b.getChars());
	}
	
	/**
	 * fills the rest of the defined block [untily | untilx] (absolute coords) with fillup
	 */
	default SELF append(CharSequence2D b, int untily, int untilx, char fillup) {
		return append(b.getChars(), untily, untilx, fillup);
	}
	
	default SELF append(Object obj) {
		if (obj == null)
			return append("null");
		
		Object o = ToStringHelper.getDefault().toString(obj);
		if (o instanceof ToString2D)
			return append(((ToString2D) o).toString2D());
		return append(o.toString());
	}
	
	//pos
	int getX();
	
	SELF setX(int pos);
	
	int getY();
	
	SELF setY(int pos);
	
	//nextLine
	SELF nextLine();
	
	@Override
	default Iterator<String> iterator() {
		return new CharArrayStringIterable(getChars()).iterator();
	}
}
