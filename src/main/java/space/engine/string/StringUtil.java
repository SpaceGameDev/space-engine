package space.engine.string;

import space.engine.string.builder.CharBufferBuilder2D;
import space.engine.string.builder.IStringBuilder2D;

import java.util.Arrays;

@SuppressWarnings("SameParameterValue")
public class StringUtil {
	
	public static final String nextLine = String.format("%n");
	public static final char[] nextLineChars = nextLine.toCharArray();
	public static final int nextLineLength = nextLine.length();
	
	public static IStringBuilder2D<?> hrLittle(int length) {
		char[] c = new char[length];
		Arrays.fill(c, '-');
		String m = new String(c);
		
		IStringBuilder2D<?> b = new CharBufferBuilder2D<>();
		b.nextLine();
		b.append(m).nextLine();
		b.append("");
		
		return b;
	}
	
	public static IStringBuilder2D<?> hrBig(int length) {
		char[] c = new char[length];
		Arrays.fill(c, '-');
		String m = new String(c);
		
		IStringBuilder2D<?> b = new CharBufferBuilder2D();
		b.nextLine();
		b.append(m).nextLine();
		b.append(m).nextLine();
		b.append(m).nextLine();
		b.append("");
		
		return b;
	}
}
