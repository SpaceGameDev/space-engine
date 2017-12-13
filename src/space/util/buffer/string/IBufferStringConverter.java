package space.util.buffer.string;

import space.util.buffer.buffers.Buffer;

public interface IBufferStringConverter {
	
	//memString from String
	Buffer memStringUTF8(String str, boolean nullTerm);
	
	Buffer memStringUTF16(String str, boolean nullTerm);
	
	Buffer memStringASCII(String str, boolean nullTerm);
	
	//memString length
	int memStringUTF8Length(String str, boolean nullTerm);
	
	int memStringUTF16Length(String str, boolean nullTerm);
	
	int memStringASCIILength(String str, boolean nullTerm);
	
	//memString from Buffer
	String memUTF8String(Buffer buffer);
	
	String memUTF16String(Buffer buffer);
	
	String memASCIIString(Buffer buffer);
	
	//memString from Buffer with length
	String memUTF8String(Buffer buffer, int length);
	
	String memUTF16String(Buffer buffer, int length);
	
	String memASCIIString(Buffer buffer, int length);
}
