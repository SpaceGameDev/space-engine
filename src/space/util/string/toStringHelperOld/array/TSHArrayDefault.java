package space.util.string.toStringHelperOld.array;

import space.util.string.builder.CharBufferBuilder1D;

public class TSHArrayDefault extends AbstractTSHArray {
	
	public static final TSHArrayDefault INSTANCE = new TSHArrayDefault();
	
	@Override
	public String toString(byte[] obj, int from, int to) {
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(helperCollection.getNativeType().toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(short[] obj, int from, int to) {
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(helperCollection.getNativeType().toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(int[] obj, int from, int to) {
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(helperCollection.getNativeType().toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(long[] obj, int from, int to) {
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(helperCollection.getNativeType().toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(float[] obj, int from, int to) {
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(helperCollection.getNativeType().toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(double[] obj, int from, int to) {
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(helperCollection.getNativeType().toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(boolean[] obj, int from, int to) {
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(helperCollection.getNativeType().toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(char[] obj, int from, int to) {
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(helperCollection.getNativeType().toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	@Override
	public String toString(Object[] obj, int from, int to) {
		CharBufferBuilder1D b = new CharBufferBuilder1D();
		b.append('[');
		for (int i = from; i < to; i++) {
			b.append(helperCollection.getNativeType().toString(obj[i]));
			if (i < to - 1)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
}
