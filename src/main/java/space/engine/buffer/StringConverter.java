package space.engine.buffer;

import space.engine.buffer.array.ArrayBufferByte;

import static java.nio.charset.StandardCharsets.*;

public class StringConverter {
	
	public static final byte NULL_CHARACTER = 0;
	
	//string to buffer
	public static ArrayBufferByte stringToUTF8(Allocator allocator, String str, boolean nullTerm, Object[] parents) {
		byte[] bytes = str.getBytes(UTF_8);
		ArrayBufferByte buffer = ArrayBufferByte.malloc(allocator, bytes.length + (nullTerm ? 1 : 0), parents);
		buffer.copyFrom(bytes);
		if (nullTerm)
			buffer.putByte(bytes.length, (byte) 0);
		return buffer;
	}
	
	public static ArrayBufferByte stringToUTF16(Allocator allocator, String str, boolean nullTerm, Object[] parents) {
		byte[] bytes = str.getBytes(UTF_16);
		ArrayBufferByte buffer = ArrayBufferByte.malloc(allocator, bytes.length + (nullTerm ? 2 : 0), parents);
		buffer.copyFrom(bytes);
		if (nullTerm) {
			buffer.putByte(bytes.length, (byte) 0);
			buffer.putByte(bytes.length + 1, (byte) 0);
		}
		return buffer;
	}
	
	public static ArrayBufferByte stringToASCII(Allocator allocator, String str, boolean nullTerm, Object[] parents) {
		byte[] bytes = str.getBytes(US_ASCII);
		ArrayBufferByte buffer = ArrayBufferByte.malloc(allocator, bytes.length + (nullTerm ? 1 : 0), parents);
		buffer.copyFrom(bytes);
		if (nullTerm)
			buffer.putByte(bytes.length, (byte) 0);
		return buffer;
	}
	
	//address to string
	public static String UTF8ToString(long address) {
		return UTF8ToString(ArrayBufferByte.wrap(address, Long.MAX_VALUE, new Object[0]));
	}
	
	public static String UTF16ToString(long address) {
		return UTF16ToString(ArrayBufferByte.wrap(address, Long.MAX_VALUE, new Object[0]));
	}
	
	public static String ASCIIToString(long address) {
		return ASCIIToString(ArrayBufferByte.wrap(address, Long.MAX_VALUE, new Object[0]));
	}
	
	//buffer to string
	public static String UTF8ToString(ArrayBufferByte buffer) {
		return UTF8ToString(buffer, findNullCharacter(buffer, 1));
	}
	
	public static String UTF16ToString(ArrayBufferByte buffer) {
		return UTF16ToString(buffer, findNullCharacter(buffer, 2));
	}
	
	public static String ASCIIToString(ArrayBufferByte buffer) {
		return ASCIIToString(buffer, findNullCharacter(buffer, 1));
	}
	
	//buffer with length to string
	public static String UTF8ToString(ArrayBufferByte buffer, int length) {
		byte[] bytes = new byte[length];
		buffer.copyInto(0, bytes, 0, length);
		return new String(bytes, 0, length, UTF_8);
	}
	
	public static String UTF16ToString(ArrayBufferByte buffer, int length) {
		byte[] bytes = new byte[length];
		buffer.copyInto(0, bytes, 0, length);
		return new String(bytes, 0, length, UTF_16);
	}
	
	public static String ASCIIToString(ArrayBufferByte buffer, int length) {
		byte[] bytes = new byte[length];
		buffer.copyInto(0, bytes, 0, length);
		return new String(bytes, 0, length, US_ASCII);
	}
	
	//other
	private static int findNullCharacter(ArrayBufferByte buffer, int step) {
		for (int i = 0; i < buffer.sizeOf(); i += step)
			if (buffer.getByte(i) == NULL_CHARACTER)
				return i;
		throw new StringBufferNotNullTerminatedException(buffer);
	}
	
	public static class StringBufferNotNullTerminatedException extends RuntimeException {
		
		public StringBufferNotNullTerminatedException(Buffer buffer) {
			super("Buffer not terminated with null character: \n" + buffer.dump().toString());
		}
	}
}
