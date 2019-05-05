package space.engine.string.builder;

import org.jetbrains.annotations.NotNull;
import space.engine.delegate.specific.CharArrayStringIterable;
import space.engine.string.CharSequence2D;
import space.engine.string.ToString2D;
import space.engine.string.toStringHelper.ToStringHelper;

import java.util.Iterator;

public interface IStringBuilder2D<SELF extends IStringBuilder2D<SELF>> extends IStringBuilder<SELF>, Iterable<String>, CharSequence2D {
	
	//capacity height
	default boolean ensureHeightIndex(int index) {
		return ensureHeight(index + 1);
	}
	
	boolean ensureHeight(int height);
	
	//append
	@NotNull SELF append(@NotNull char[][] chars);
	
	/**
	 * fills the rest of the defined block [untily | untilx] (absolute coords) with fillup
	 */
	@NotNull SELF append(@NotNull char[][] chars, int untily, int untilx, char fillup);
	
	@NotNull
	default SELF append(@NotNull CharSequence2D b) {
		return append(b.getChars());
	}
	
	/**
	 * fills the rest of the defined block [untily | untilx] (absolute coords) with fillup
	 */
	default SELF append(@NotNull CharSequence2D b, int untily, int untilx, char fillup) {
		return append(b.getChars(), untily, untilx, fillup);
	}
	
	@NotNull
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
	
	@NotNull SELF setX(int pos);
	
	int getY();
	
	@NotNull SELF setY(int pos);
	
	//nextLine
	@NotNull SELF nextLine();
	
	@NotNull
	@Override
	default Iterator<String> iterator() {
		return new CharArrayStringIterable(getChars()).iterator();
	}
}
