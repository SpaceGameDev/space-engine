package space.util.string.toStringHelper;

import space.util.delegate.iterator.Iteratorable;
import space.util.string.CharSequence2D;
import space.util.string.String2D;
import space.util.string.builder.CharBufferBuilder1D;

import java.util.ListIterator;
import java.util.Objects;

public class ToStringHelperDefault implements ToStringHelper<String> {
	
	public static final ToStringHelperDefault INSTANCE = new ToStringHelperDefault();
	
	//native
	@Override
	public String toString(byte b) {
		return Byte.toString(b);
	}
	
	@Override
	public String toString(short s) {
		return Short.toString(s);
	}
	
	@Override
	public String toString(int i) {
		return Integer.toString(i);
	}
	
	@Override
	public String toString(long l) {
		return Long.toString(l);
	}
	
	@Override
	public String toString(float f) {
		return Float.toString(f);
	}
	
	@Override
	public String toString(double d) {
		return Double.toString(d);
	}
	
	@Override
	public String toString(boolean b) {
		return Boolean.toString(b);
	}
	
	@Override
	public String toString(char c) {
		return Character.toString(c);
	}
	
	@Override
	public String toString(Object o) {
		return Objects.toString(o);
	}
	
	//array
	@Override
	public String toString(byte[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(short[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(int[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(long[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(float[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(double[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(boolean[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(char[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	//array object
	@Override
	public String toString(Object[] obj, int from, int to) {
		if (obj == null)
			return toStringNull();
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	//String
	@Override
	public String toString(CharSequence str) {
		return str == null ? toStringNull() : str.toString();
	}
	
	@Override
	public String toString(String str) {
		return str == null ? toStringNull() : str;
	}
	
	@Override
	public String toString(CharSequence2D str) {
		return str == null ? toStringNull() : str.toString();
	}
	
	@Override
	public String toString(String2D str) {
		return str == null ? toStringNull() : str.toString();
	}
	
	//null
	@Override
	public String toStringNull() {
		return "null";
	}
	
	//modifier
	@Override
	public String createModifier(String modifier, Object value) {
		return modifier + " " + value;
	}
	
	//objects
	@Override
	public ToStringHelperObjectsInstance<String> createObjectInstance(Object obj) {
		return new AbstractToStringHelperObjectsInstance<String>(obj, this) {
			@Override
			public String build() {
				CharBufferBuilder1D b = new CharBufferBuilder1D();
				b.append(obj.getClass().getName()).append("{ ");
				
				ListIterator<Entry<String>> iter = list.listIterator();
				for (Entry<String> entry : Iteratorable.toIteratorable(iter)) {
					b.append(entry.name).append(": ").append(entry.value);
					if (iter.hasNext())
						b.append(", ");
				}
				
				b.append('}');
				return b.toString();
			}
		};
	}
}
