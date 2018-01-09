package space.engineOld.number.format;

import space.util.math.UnsignedMath;
import space.util.string.builder.CharBufferBuilder1D;
import space.util.string.builder.CharBufferBuilder2D;
import space.util.string.builder.IStringBuilder;
import spaceOld.engine.bufferAllocator.buffers.IBuffer;

/**
 * radix can only be power of two
 */
public interface IBytesToString {
	
	//byte[] heap
	default String toStringRadix(byte[] n) {
		return toStringRadix(new CharBufferBuilder1D(), n).toString();
	}
	
	default String toStringRadix(byte[] n, int radix) {
		return toStringRadix(new CharBufferBuilder1D(), n, radix).toString();
	}
	
	default String toStringRadix(byte[] n, ByteDisplayPolicy displayPolicy) {
		return toStringRadix(displayPolicy == ByteDisplayPolicy.NORMAL ? new CharBufferBuilder1D<>() : new CharBufferBuilder2D<>(), n, displayPolicy).toString();
	}
	
	default String toStringRadix(byte[] n, int radix, ByteDisplayPolicy displayPolicy) {
		return toStringRadix(displayPolicy == ByteDisplayPolicy.NORMAL ? new CharBufferBuilder1D() : new CharBufferBuilder2D(), n, radix, displayPolicy).toString();
	}
	
	default String toStringRadix(byte[] n, int radix, int cnt, ByteDisplayPolicy displayPolicy) {
		return toStringRadix(displayPolicy == ByteDisplayPolicy.NORMAL ? new CharBufferBuilder1D() : new CharBufferBuilder2D(), n, radix, cnt, displayPolicy).toString();
	}
	
	default IStringBuilder<?> toStringRadix(IStringBuilder<?> b, byte[] n) {
		return toStringRadix(b, n, 16, Integer.MAX_VALUE, ByteDisplayPolicy.NORMAL);
	}
	
	default IStringBuilder<?> toStringRadix(IStringBuilder<?> b, byte[] n, int radix) {
		return toStringRadix(b, n, radix, Integer.MAX_VALUE, ByteDisplayPolicy.NORMAL);
	}
	
	default IStringBuilder<?> toStringRadix(IStringBuilder<?> b, byte[] n, ByteDisplayPolicy displayPolicy) {
		return toStringRadix(b, n, 16, Integer.MAX_VALUE, displayPolicy);
	}
	
	default IStringBuilder<?> toStringRadix(IStringBuilder<?> b, byte[] n, int radix, ByteDisplayPolicy displayPolicy) {
		return toStringRadix(b, n, radix, Integer.MAX_VALUE, displayPolicy);
	}
	
	default IStringBuilder<?> toStringRadix(IStringBuilder<?> b, byte[] n, int radix, int cnt, ByteDisplayPolicy displayPolicy) {
		return toStringShift(b, n, UnsignedMath.getPowerOfTwoFloorFast(radix), cnt, displayPolicy);
	}
	
	IStringBuilder<?> toStringShift(IStringBuilder<?> b, byte[] n, int shift, int cnt, ByteDisplayPolicy displayPolicy);
	
	//IBuffer direct
	default String toStringRadix(IBuffer n) {
		return toStringRadix(new CharBufferBuilder1D(), n).toString();
	}
	
	default String toStringRadix(IBuffer n, int radix) {
		return toStringRadix(new CharBufferBuilder1D(), n, radix).toString();
	}
	
	default String toStringRadix(IBuffer n, ByteDisplayPolicy displayPolicy) {
		return toStringRadix(displayPolicy == ByteDisplayPolicy.NORMAL ? new CharBufferBuilder1D() : new CharBufferBuilder2D(), n, displayPolicy).toString();
	}
	
	default String toStringRadix(IBuffer n, int radix, ByteDisplayPolicy displayPolicy) {
		return toStringRadix(displayPolicy == ByteDisplayPolicy.NORMAL ? new CharBufferBuilder1D() : new CharBufferBuilder2D(), n, radix, displayPolicy).toString();
	}
	
	default String toStringRadix(IBuffer n, int radix, int cnt, ByteDisplayPolicy displayPolicy) {
		return toStringRadix(displayPolicy == ByteDisplayPolicy.NORMAL ? new CharBufferBuilder1D() : new CharBufferBuilder2D(), n, radix, cnt, displayPolicy).toString();
	}
	
	default IStringBuilder<?> toStringRadix(IStringBuilder<?> b, IBuffer n) {
		return toStringRadix(b, n, 16, Integer.MAX_VALUE, ByteDisplayPolicy.NORMAL);
	}
	
	default IStringBuilder<?> toStringRadix(IStringBuilder<?> b, IBuffer n, int radix) {
		return toStringRadix(b, n, radix, Integer.MAX_VALUE, ByteDisplayPolicy.NORMAL);
	}
	
	default IStringBuilder<?> toStringRadix(IStringBuilder<?> b, IBuffer n, ByteDisplayPolicy displayPolicy) {
		return toStringRadix(b, n, 16, Integer.MAX_VALUE, displayPolicy);
	}
	
	default IStringBuilder<?> toStringRadix(IStringBuilder<?> b, IBuffer n, int radix, ByteDisplayPolicy displayPolicy) {
		return toStringRadix(b, n, radix, Integer.MAX_VALUE, displayPolicy);
	}
	
	default IStringBuilder<?> toStringRadix(IStringBuilder<?> b, IBuffer n, int radix, int cnt, ByteDisplayPolicy displayPolicy) {
		return toStringShift(b, n, UnsignedMath.getPowerOfTwoFloorFast(radix), cnt, displayPolicy);
	}
	
	IStringBuilder<?> toStringShift(IStringBuilder<?> b, IBuffer n, int shift, int cnt, ByteDisplayPolicy displayPolicy);
	
	//classes
	enum ByteDisplayPolicy {
		
		NORMAL,
		NICE,
		NICEST
		
	}
}
