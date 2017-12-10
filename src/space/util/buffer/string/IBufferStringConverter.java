package space.util.buffer.string;

import space.util.buffer.alloc.IBufferAllocator;
import space.util.buffer.buffers.IBuffer;

public interface IBufferStringConverter {
	
	void setAlloc(IBufferAllocator alloc);
	
	IBufferAllocator alloc();
	
	IBuffer memStringUTF8(String str, boolean nullTerm);
	
	IBuffer memStringUTF16(String str, boolean nullTerm);
	
	IBuffer memStringASCII(String str, boolean nullTerm);
	
	int memStringUTF8Length(String str, boolean nullTerm);
	
	int memStringUTF16Length(String str, boolean nullTerm);
	
	int memStringASCIILength(String str, boolean nullTerm);
	
	String memUTF8String(IBuffer buffer);
	
	String memUTF16String(IBuffer buffer);
	
	String memASCIIString(IBuffer buffer);
	
	String memUTF8String(IBuffer buffer, int length);
	
	String memUTF16String(IBuffer buffer, int length);
	
	String memASCIIString(IBuffer buffer, int length);
}
