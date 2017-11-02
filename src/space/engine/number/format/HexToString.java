package space.engine.number.format;

import space.util.math.MathUtils;
import space.util.math.UnsignedMath;
import space.util.string.StringUtil;
import space.util.string.builder.CharBufferBuilder1DBackwards;
import space.util.string.builder.IStringBuilder;
import spaceOld.engine.bufferAllocator.buffers.Buffer;
import spaceOld.engine.bufferAllocator.buffers.IBuffer;

public class HexToString implements IBytesToString {
	
	public FormatterUtil formatter;
	
	public HexToString() {
	}
	
	public HexToString(FormatterUtil formatter) {
		this.formatter = formatter;
	}
	
	public static void main(String[] args) {
		int radix = 16;
		
		byte[] t = new byte[] {0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF, 0x10, 0x11, 0x12, 0x13, 0x7F, (byte) 0xFF};
		System.out.println(FormatterUtil.INSTANCE.BYTES.toStringRadix(t, radix, ByteDisplayPolicy.NORMAL));
		System.out.println(FormatterUtil.INSTANCE.BYTES.toStringRadix(t, radix, ByteDisplayPolicy.NICE));
		
		System.out.println(StringUtil.hrLittle(200));
		
		IBuffer b = new Buffer(t.length);
		b.putByteOffset(0, t);
		System.out.println(FormatterUtil.INSTANCE.BYTES.toStringRadix(t, radix, ByteDisplayPolicy.NORMAL));
		System.out.println(FormatterUtil.INSTANCE.BYTES.toStringRadix(t, radix, ByteDisplayPolicy.NICE));
		b.free();
	}
	
	@Override
	public IStringBuilder<?> toStringShift(IStringBuilder<?> b, byte[] n, int shift, int cnt, ByteDisplayPolicy displayPolicy) {
		switch (displayPolicy) {
			case NORMAL:
				return toStringShiftNormal(b, n, shift, cnt);
			case NICE:
				return toStringShiftNice(b, n, shift, cnt);
		}
		throw new IllegalArgumentException();
	}
	
	public IStringBuilder<?> toStringShiftNormal(IStringBuilder<?> b, byte[] n, int shift, int cnt) {
		int l = MathUtils.min(n.length, cnt);
		int last = l - 1;
		int mask = (1 << shift) - 1;
		int size = 8 / shift;
		if ((8 % shift) != 0)
			size++;
		
		b.ensureCapacity(l * 3 - 1);
		
		CharBufferBuilder1DBackwards back = new CharBufferBuilder1DBackwards(size);
		for (int i = 0; i < l; i++) {
			int c = UnsignedMath.toInt(n[i]);
			
			int ci = size;
			for (; c != 0; c >>>= shift) {
				back.append(formatter.digits[c & mask]);
				ci--;
			}
			b.fill(ci, formatter.fillCharNumber);
			b.append(back.toString());
			back.setLength(size);
			
			if (i < last)
				b.append(formatter.fillChar);
		}
		
		return b;
	}
	
	public IStringBuilder<?> toStringShiftNice(IStringBuilder<?> sb, byte[] n, int shift, int cnt) {
		int l = MathUtils.min(n.length, cnt);
		int last = l - 1;
		int mask = (1 << shift) - 1;
		int indexCnt = (1 << shift) / 2;
		int size = 8 / shift;
		if ((8 % shift) != 0)
			size++;
		
		IStringBuilder<?> b = sb.startLine(IStringBuilder.Direction.DIRX);
		b.ensureCapacity(l * 3 - 1);
		
		CharBufferBuilder1DBackwards back = new CharBufferBuilder1DBackwards(size);
		for (int i = 0; i < l; i++) {
			IStringBuilder<?> cb = b.startLine(IStringBuilder.Direction.DIRY);
			int c = UnsignedMath.toInt(n[i]);
			
			if ((i % indexCnt) == 0)
				//noinspection ResultOfMethodCallIgnored
				formatter.UNSIGNED.toString(cb, i, 16);
			cb.nextLine();
			
			int ci = size;
			for (; c != 0; c >>>= shift) {
				back.append(formatter.digits[c & mask]);
				ci--;
			}
			cb.fill(ci, formatter.fillCharNumber);
			cb.append(back.toString());
			back.setLength(size);
			
			if (i < last)
				cb.append(formatter.fillChar);
			
			cb.endLine();
		}
		b.endLine();
		
		return sb;
	}
	
	public IStringBuilder<?> toStringShiftNicest(IStringBuilder<?> sb, byte[] n, int shift, int cnt, int indexPerLine) {
		int l = MathUtils.min(n.length, cnt);
		int last = l - 1;
		int mask = (1 << shift) - 1;
		int size = 8 / shift;
		if ((8 % shift) != 0)
			size++;
		
		int indexBigLength = formatter.UNSIGNED.toString(l, 16).length();
		int indexSmallLength = formatter.UNSIGNED.toString(l, 16).length();
		int indexSmallLengthTotal = (indexSmallLength + 1) * indexPerLine;
		IStringBuilder<?> b = sb.startLine(IStringBuilder.Direction.DIRY);
		
		b.fill(indexBigLength + 1, formatter.fillChar).append('+').fill(indexPerLine * 3 - 1, '-').append('+').nextLine();


//		CharBufferBuilder1DBackwards back = new CharBufferBuilder1DBackwards(size);
//		for (int i = 0; i < l; i++) {
//			IStringBuilder<?> cb = b.startLine(IStringBuilder.Direction.DIRX);
//			int buffer = UnsignedMath.toInt(n[i]);
//
//			int ci = size;
//			for (; buffer != 0; buffer >>>= shift) {
//				back.append(formatter.digits[buffer & mask]);
//				ci--;
//			}
//			cb.fill(ci, formatter.fillCharNumber);
//			cb.append(back.toString());
//			back.setLength(size);
//
//			if (i < last)
//				cb.append(formatter.fillChar);
//
//			cb.endLine();
//		}
//		b.endLine();
		
		return sb;
	}
	
	@Override
	public IStringBuilder<?> toStringShift(IStringBuilder<?> b, IBuffer n, int shift, int cnt, ByteDisplayPolicy displayPolicy) {
		switch (displayPolicy) {
			case NORMAL:
				return toStringShiftNormal(b, n, shift, cnt);
			case NICE:
				return toStringShiftNice(b, n, shift, cnt);
		}
		throw new IllegalArgumentException();
	}
	
	public IStringBuilder<?> toStringShiftNormal(IStringBuilder<?> b, IBuffer n, int shift, int cnt) {
		int l = MathUtils.min(n.capacityInt(), cnt);
		int last = l - 1;
		int mask = (1 << shift) - 1;
		int size = 8 / shift;
		if ((8 % shift) != 0)
			size++;
		
		b.ensureCapacity(l * 3 - 1);
		
		CharBufferBuilder1DBackwards back = new CharBufferBuilder1DBackwards(size);
		for (int i = 0; i < l; i++) {
			int c = UnsignedMath.toInt(n.getByteIndex(i));
			
			int ci = size;
			for (; c != 0; c >>>= shift) {
				back.append(formatter.digits[c & mask]);
				ci--;
			}
			b.fill(ci, formatter.fillCharNumber);
			b.append(back.toString());
			back.setLength(size);
			
			if (i < last)
				b.append(formatter.fillChar);
		}
		
		return b;
	}
	
	public IStringBuilder<?> toStringShiftNice(IStringBuilder<?> sb, IBuffer n, int shift, int cnt) {
		int l = MathUtils.min(n.capacityInt(), cnt);
		int last = l - 1;
		int mask = (1 << shift) - 1;
		int indexCnt = (1 << shift) / 2;
		int size = 8 / shift;
		if ((8 % shift) != 0)
			size++;
		
		IStringBuilder<?> b = sb.startLine(IStringBuilder.Direction.DIRX);
		b.ensureCapacity(l * 3 - 1);
		
		CharBufferBuilder1DBackwards back = new CharBufferBuilder1DBackwards(size);
		for (int i = 0; i < l; i++) {
			IStringBuilder<?> cb = b.startLine(IStringBuilder.Direction.DIRY);
			int c = UnsignedMath.toInt(n.getByteIndex(i));
			
			if ((i % indexCnt) == 0)
				//noinspection ResultOfMethodCallIgnored
				formatter.UNSIGNED.toString(cb, i, 16);
			cb.nextLine();
			
			int ci = size;
			for (; c != 0; c >>>= shift) {
				back.append(formatter.digits[c & mask]);
				ci--;
			}
			cb.fill(ci, formatter.fillCharNumber);
			cb.append(back.toString());
			back.setLength(size);
			
			if (i < last)
				cb.append(formatter.fillChar);
			
			cb.endLine();
		}
		b.endLine();
		
		return sb;
	}
}
