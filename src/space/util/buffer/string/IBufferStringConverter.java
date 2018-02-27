package space.util.buffer.string;

import space.util.buffer.direct.DirectBuffer;

/**
 * A utility class for converting:
 * <ul>
 * <li>Strings into C-like Null-terminated Buffers</li>
 * <li>C-like Null-terminated Buffers into Strings</li>
 * <li>getting the length of C-like Null-terminated Buffers</li>
 * </ul>
 * Supported Charsets: UTF8, UTF16, ASCII
 */
public interface IBufferStringConverter {
	
	//memString from String
	DirectBuffer memStringUTF8(String str, boolean nullTerm);
	
	DirectBuffer memStringUTF16(String str, boolean nullTerm);
	
	DirectBuffer memStringASCII(String str, boolean nullTerm);
	
	//memString length
	int memStringUTF8Length(String str, boolean nullTerm);
	
	int memStringUTF16Length(String str, boolean nullTerm);
	
	int memStringASCIILength(String str, boolean nullTerm);
	
	//memString from Buffer
	String memUTF8String(DirectBuffer buffer);
	
	String memUTF16String(DirectBuffer buffer);
	
	String memASCIIString(DirectBuffer buffer);
	
	//memString from Buffer with length
	String memUTF8String(DirectBuffer buffer, int length);
	
	String memUTF16String(DirectBuffer buffer, int length);
	
	String memASCIIString(DirectBuffer buffer, int length);
}
