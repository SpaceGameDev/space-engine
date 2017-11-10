package space.util.string;

/**
 * height: dimension[0]
 * length: dimension[1]
 */
public interface CharSequence2D {
	
	//size
	int height();
	
	int length(int h);
	
	int maxLength();
	
	//get result
	char[][] getChars();
	
	default String toString0() {
		char[][] chars = getChars();
		
		int l = chars.length == 0 ? 0 : (chars.length - 1) * StringUtil.nextLineLength;
		for (char[] b : chars)
			l += b.length;
		int last = chars.length - 1;
		
		int pos = 0;
		char[] ret = new char[l];
		for (int i = 0; i < chars.length; i++) {
			char[] b = chars[i];
			System.arraycopy(b, 0, ret, pos, b.length);
			pos += b.length;
			
			if (i < last) {
				System.arraycopy(StringUtil.nextLineChars, 0, ret, pos, StringUtil.nextLineChars.length);
				pos += StringUtil.nextLineChars.length;
			}
		}
		
		return new String(ret);
	}
}
