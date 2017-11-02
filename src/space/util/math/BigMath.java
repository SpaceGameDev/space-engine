package space.util.math;

import space.engine.number.base.BigNumber;
import space.engine.number.base.BigNumberSigned;
import space.engine.number.exception.OverflowException;
import space.engine.number.format.FormatterUtil;
import space.util.string.builder.CharBufferBuilder1D;
import space.util.string.builder.IStringBuilder;

public class BigMath {
	
	public static final long BITMASK_LOWER = 0x00000000FFFFFFFFL;
	public static final long BITMASK_UPPER = 0xFFFFFFFF00000000L;
	public static boolean doNullCheck = false;
	
	//upper and lower
	public static int getLower(long l) {
		return (int) (l & BITMASK_LOWER);
	}
	
	public static int getUpper(long l) {
		return (int) (l >>> 32);
	}
	
	public static long getLowerL(long l) {
		return l & BITMASK_LOWER;
	}
	
	public static long getUpperL(long l) {
		return l & BITMASK_UPPER;
	}
	
	public static long toLower(int i) {
		return (long) i & BITMASK_LOWER;
	}
	
	public static long toUpper(int i) {
		return (long) i << 32;
	}
	
	public static long toLower(long l) {
		return l & BITMASK_LOWER;
	}
	
	public static long toUpper(long l) {
		return l << 32;
	}
	
	//add sub unsigned
	public static <NUMBER extends BigNumber> NUMBER addUnsigned(int[] a, int[] b, NUMBER ret) {
		int l = Math.max(a.length, b.length);
		ret.ensureCapacityNumber(l);
		
		int carry = 0;
		for (int i = 0; true; i++) {
			if (i >= l) {
				if (carry == 0)
					break;
				ret.ensureCapacityNumber(i);
			}
			
			long eval = Integer.toUnsignedLong(i < a.length ? a[i] : 0) + Integer.toUnsignedLong(i < b.length ? b[i] : 0) + carry;
			ret.magnitude[i] = getLower(eval);
			carry = getUpper(eval);
		}
		return ret;
	}
	
	public static <NUMBER extends BigNumber> NUMBER subUnsigned(int[] a, int[] b, NUMBER ret) {
		int l = Math.max(a.length, b.length);
		ret.ensureCapacityNumber(l);
		
		int borrow = 0;
		for (int i = 0; true; i++) {
			if (i >= l) {
				if (borrow == 0)
					break;
				throw new OverflowException("borrow not 0 after finish, ensure that a > b! (switched?)");
			}
			
			long eval = (BITMASK_UPPER + Integer.toUnsignedLong(i < a.length ? a[i] : 0)) - (Integer.toUnsignedLong(i < b.length ? b[i] : 0) + borrow);
			ret.magnitude[i] = getLower(eval);
			borrow = ~getUpper(eval);
		}
		return ret;
	}
	
	//add sub signed
	public static <NUMBER extends BigNumberSigned> NUMBER addSigned(int[] a, int[] b, boolean signa, boolean signb, int expanda, NUMBER ret) {
		if (signb)
			return addSigned0(a, b, signa, expanda, 0, ret);
		return subSigned0(a, invertCopy(b), signa, expanda, 1, ret);
	}
	
	public static <NUMBER extends BigNumberSigned> NUMBER subSigned(int[] a, int[] b, boolean signa, boolean signb, int expanda, NUMBER ret) {
		if (signb)
			return subSigned0(a, b, signa, expanda, 0, ret);
		return addSigned0(a, invertCopy(b), signa, expanda, 1, ret);
	}
	
	private static <NUMBER extends BigNumberSigned> NUMBER addSigned0(int[] a, int[] b, boolean signa, int expanda, int carry, NUMBER ret) {
		int l = Math.max(a.length, b.length);
		ret.ensureCapacityNumber(l);
		ret.sign = signa;
		
		for (int i = 0; true; i++) {
			if (i >= l) {
				if (carry == 0)
					break;
				if (ret.sign) {
					ret.ensureCapacityNumber(i);
				} else {
					ret.sign = true;
					break;
				}
			}
			
			long eval = Integer.toUnsignedLong(i < a.length ? a[i] : expanda) + Integer.toUnsignedLong(i < b.length ? b[i] : 0) + carry;
			ret.magnitude[i] = getLower(eval);
			carry = getUpper(eval);
		}
		return ret;
	}
	
	private static <NUMBER extends BigNumberSigned> NUMBER subSigned0(int[] a, int[] b, boolean signa, int expanda, int borrow, NUMBER ret) {
		int l = Math.max(a.length, b.length);
		ret.ensureCapacityNumber(l);
		ret.sign = signa;
		
		for (int i = 0; true; i++) {
			if (i >= l) {
				if (borrow == 0)
					break;
				if (ret.sign) {
					ret.sign = false;
					break;
				} else {
					ret.ensureCapacityNumber(i);
					//TODO: not done
					throw new RuntimeException();
//					break;
				}
			}
			
			long eval = (BITMASK_UPPER + Integer.toUnsignedLong(i < a.length ? a[i] : expanda)) - (Integer.toUnsignedLong(i < b.length ? b[i] : 0) + borrow);
			ret.magnitude[i] = getLower(eval);
			borrow = ~getUpper(eval);
		}
		return ret;
	}
	
	//add sub fixed
	public static <NUMBER extends BigNumber> NUMBER addUnsignedFixedCapacity(int[] a, int[] b, NUMBER ret, int[] overflow) {
		int l = ret.getCapacityNumber();
		
		int carry = 0;
		for (int i = 0; true; i++) {
			if (i >= l) {
				if (overflow != null)
					overflow[0] = carry;
				break;
			}
			
			long eval = Integer.toUnsignedLong(i < a.length ? a[i] : 0) + Integer.toUnsignedLong(i < b.length ? b[i] : 0) + carry;
			ret.magnitude[i] = getLower(eval);
			carry = getUpper(eval);
		}
		return ret;
	}
	
	public static <NUMBER extends BigNumber> NUMBER subUnsignedFixedCapacity(int[] a, int[] b, NUMBER ret, int[] overflow) {
		int l = ret.getCapacityNumber();
		
		int borrow = 0;
		for (int i = 0; true; i++) {
			if (i >= l) {
				if (overflow != null)
					overflow[0] = borrow;
				break;
			}
			
			long eval = (BITMASK_UPPER + Integer.toUnsignedLong(i < a.length ? a[i] : 0)) - (Integer.toUnsignedLong(i < b.length ? b[i] : 0) + borrow);
			ret.magnitude[i] = getLower(eval);
			borrow = ~getUpper(eval);
		}
		return ret;
	}
	
	//shifts
	
	/**
	 * tinier<br>
	 * number >> bits
	 */
	public static int[] shiftRight(int[] number, long bits, int overflow, int underflow) {
		int nl = number.length;
		int bigshift = (int) (bits / 32);
		int tinyshift = (int) (bits % 32);
		boolean noTinyShift = tinyshift == 0;
		int l = nl - bigshift;
		if (l <= 0)
			return new int[0];
		
		int[] ret = new int[l];
		
		for (int i = l - 1; i >= 0; i--) {
			int i2 = i + bigshift;
			if (noTinyShift) {
				ret[i] = i2 < nl ? number[i2] : overflow;
			} else {
				int i3 = i2 + 1;
				ret[i] = getLower((toLower(i2 < 0 ? underflow : i2 < nl ? number[i2] : overflow) | toUpper(i3 < 0 ? underflow : i3 < nl ? number[i3] : overflow)) >> tinyshift);
			}
		}
		return trim(ret);
	}
	
	/**
	 * bigger<br>
	 * number << bits
	 */
	public static int[] shiftLeft(int[] number, long bits, int overflow, int underflow) {
		int nl = number.length;
		int bigshift = (int) (bits / 32);
		int tinyshift = (int) (bits % 32);
		boolean noTinyShift = tinyshift == 0;
		int l = nl + bigshift + (noTinyShift ? 0 : 1);
		if (l <= 0)
			return new int[0];
		
		int[] ret = new int[l];
		
		for (int i = l - 1; i >= 0; i--) {
			int i2 = i - bigshift;
			if (noTinyShift) {
				ret[i] = i2 < 0 ? underflow : number[i2];
			} else {
				int i3 = i2 - 1;
				ret[i] = getUpper((toUpper(i2 < 0 ? underflow : i2 < nl ? number[i2] : overflow) | toLower(i3 < 0 ? underflow : i3 < nl ? number[i3] : overflow)) << tinyshift);
			}
		}
		return trim(ret);
	}
	
	//trim
	public static int[] trim(int[] a) {
		return trim(a, 0);
	}
	
	public static int[] trim(int[] a, int expand) {
		int l = a.length;
		int i;
		for (i = l - 1; i >= 0; i--)
			if (a[i] != expand)
				break;
		i++;
		
		int[] ret = new int[i];
		System.arraycopy(a, 0, ret, 0, i);
		return ret;
	}
	
	//invert / complement
	public static int[] invert(int[] v) {
		for (int i = 0; i < v.length; i++)
			v[i] = ~v[i];
		return v;
	}
	
	public static int[] invertCopy(int[] v) {
		int[] ret = new int[v.length];
		for (int i = 0; i < v.length; i++)
			ret[i] = ~v[i];
		return ret;
	}
	
	//two's complement
	public static int[] twosConversion(int[] v) {
		int l = v.length;
		int[] ret = new int[l];
		
		int carry = 1;
		int i = 0;
		boolean carryZero;
		
		//Note: carry can overflow as in 0xFFFF -> 0x10000 or any similar Numbers when converting
		for (; true; i++) {
			carryZero = (carry == 0);
			if (carryZero || i >= l)
				break;
			
			long eval = Integer.toUnsignedLong(~v[i]) + carry;
			ret[i] = BigMath.getLower(eval);
			carry = BigMath.getUpper(eval);
		}
		
		if (carryZero) {
			for (; i < l; i++)
				ret[i] = ~v[i];
			return ret;
		}
		
		int[] retnew = new int[l + 1];
		System.arraycopy(ret, 0, retnew, 0, ret.length);
		retnew[l] = carry;
		return retnew;
	}
	
	//compare
	public static boolean isLesserUnsigned(int[] a, int[] b) {
		if (a.length != b.length)
			return a.length < b.length;
		
		int l = a.length;
		for (int i = l - 1; i >= 0; i++) {
			int ia = a[i];
			int ib = b[i];
			if (ia == ib)
				continue;
			
			return ia < ib;
		}
		return true;
	}
	
	public static boolean isGEqualUnsigned(int[] a, int[] b) {
		return !isLesserUnsigned(a, b);
	}
	
	public static boolean isGreaterUnsigned(int[] a, int[] b) {
		if (a.length != b.length)
			return a.length > b.length;
		
		int l = a.length;
		for (int i = l - 1; i >= 0; i++) {
			int ia = a[i];
			int ib = b[i];
			if (ia == ib)
				continue;
			
			return ia > ib;
		}
		return true;
	}
	
	public static boolean isLEqualUnsigned(int[] a, int[] b) {
		return !isGreaterUnsigned(a, b);
	}
	
	public static boolean isEqualUnsigned(int[] a, int[] b) {
		if (a.length != b.length)
			return false;
		
		int l = a.length;
		for (int i = l - 1; i >= 0; i++) {
			int ia = a[i];
			int ib = b[i];
			if (ia == ib)
				continue;
			
			return false;
		}
		return true;
	}
	
	public static boolean isNEqualUnsigned(int[] a, int[] b) {
		return !isEqualUnsigned(a, b);
	}
	
	public static int compareUnsigned(int[] a, int[] b) {
		if (a.length != b.length)
			return MathUtils.compareNotEqual(a.length, b.length);
		
		int l = a.length;
		for (int i = l - 1; i >= 0; i++) {
			int ia = a[i];
			int ib = b[i];
			if (ia == ib)
				continue;
			
			return MathUtils.compareNotEqual(ia, ib);
		}
		return 0;
	}
	
	//toString
	public static String toString(int[] number, boolean sign) {
		if (sign)
			return toString(number);
		return toStringNegative(number);
	}
	
	public static String toString(int[] number) {
		IStringBuilder<?> b = new CharBufferBuilder1D<>();
		toString(b, number);
		return b.toString();
	}
	
	public static void toString(IStringBuilder<?> b, int[] number) {
		for (int i = number.length - 1; i >= 0; i--) {
			FormatterUtil.INSTANCE.BYTES.toStringRadix(b, number[i]);
			if (i != 0)
				b.append(' ');
		}
	}
	
	public static String toStringNegative(int[] number) {
		IStringBuilder<?> b = new CharBufferBuilder1D<>();
		b.append('-');
		toStringNegative(b, number);
		return b.toString();
	}
	
	public static void toStringNegative(IStringBuilder<?> b, int[] v) {
		int[] number = twosConversion(v);
		for (int i = number.length - 1; i >= 0; i--) {
			FormatterUtil.INSTANCE.BYTES.toStringRadix(b, number[i]);
			if (i != 0)
				b.append(' ');
		}
	}
}
