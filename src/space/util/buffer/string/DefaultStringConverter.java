package space.util.buffer.string;

import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.buffer.direct.NioByteBufferWrapper;

import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

public class DefaultStringConverter implements IBufferStringConverter {
	
	public static final String CHARACTER_CODING_EXCEPTION = "CHARACTER_CODING_EXCEPTION";
	public static final byte NULL_CHARACTER = 0;
	
	public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;
	public static final CharsetEncoder UTF8_ENCODER = UTF8_CHARSET.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
	public static final CharsetDecoder UTF8_DECODER = UTF8_CHARSET.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
	public static final Charset UTF16_CHARSET = StandardCharsets.UTF_16;
	public static final CharsetEncoder UTF16_ENCODER = UTF16_CHARSET.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
	public static final CharsetDecoder UTF16_DECODER = UTF16_CHARSET.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
	public static final Charset ASCII_CHARSET = StandardCharsets.US_ASCII;
	public static final CharsetEncoder ASCII_ENCODER = ASCII_CHARSET.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
	public static final CharsetDecoder ASCII_DECODER = ASCII_CHARSET.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
	
	public BufferAllocator alloc;
	
	public DefaultStringConverter(BufferAllocator alloc) {
		this.alloc = alloc;
	}
	
	//memString from String
	@Override
	public DirectBuffer memStringUTF8(String str, boolean nullTerm) {
		int length = memStringUTF8Length(str, nullTerm);
		DirectBuffer buffer = alloc.malloc(length);
		UTF8_ENCODER.encode(CharBuffer.wrap(str), NioByteBufferWrapper.wrap(buffer), false);
		if (nullTerm)
			buffer.putByte(length - 1, NULL_CHARACTER);
		return buffer;
	}
	
	@Override
	public DirectBuffer memStringUTF16(String str, boolean nullTerm) {
		int length = memStringUTF16Length(str, nullTerm);
		DirectBuffer buffer = alloc.malloc(length);
		UTF16_ENCODER.encode(CharBuffer.wrap(str), NioByteBufferWrapper.wrap(buffer), false);
		if (nullTerm) {
			buffer.putByte(length - 2, NULL_CHARACTER);
			buffer.putByte(length - 1, NULL_CHARACTER);
		}
		return buffer;
	}
	
	@Override
	public DirectBuffer memStringASCII(String str, boolean nullTerm) {
		int length = memStringASCIILength(str, nullTerm);
		DirectBuffer buffer = alloc.malloc(length);
		ASCII_ENCODER.encode(CharBuffer.wrap(str), NioByteBufferWrapper.wrap(buffer), false);
		if (nullTerm)
			buffer.putByte(length - 1, NULL_CHARACTER);
		return buffer;
	}
	
	//memString length
	@Override
	public int memStringUTF8Length(String str, boolean nullTerm) {
		return str.length() + (nullTerm ? 1 : 0);
	}
	
	@Override
	public int memStringUTF16Length(String str, boolean nullTerm) {
		return str.length() + (nullTerm ? 2 : 0);
	}
	
	@Override
	public int memStringASCIILength(String str, boolean nullTerm) {
		return str.length() + (nullTerm ? 1 : 0);
	}
	
	//memString from Buffer
	@Override
	public String memUTF8String(DirectBuffer buffer) {
		return memUTF8String(buffer, findNullCharacter(buffer));
	}
	
	@Override
	public String memUTF16String(DirectBuffer buffer) {
		return memUTF16String(buffer, findNullCharacter(buffer));
	}
	
	@Override
	public String memASCIIString(DirectBuffer buffer) {
		return memASCIIString(buffer, findNullCharacter(buffer));
	}
	
	//memString from Buffer with length
	@Override
	public String memUTF8String(DirectBuffer buffer, int length) {
		if (length == -1)
			throw new IllegalArgumentException("Illegal length");
		try {
			return UTF8_DECODER.decode(NioByteBufferWrapper.wrap(buffer, length)).toString();
		} catch (CharacterCodingException e) {
			return CHARACTER_CODING_EXCEPTION;
		}
	}
	
	@Override
	public String memUTF16String(DirectBuffer buffer, int length) {
		if (length == -1)
			throw new IllegalArgumentException("Illegal length");
		try {
			return UTF16_DECODER.decode(NioByteBufferWrapper.wrap(buffer, length)).toString();
		} catch (CharacterCodingException e) {
			return CHARACTER_CODING_EXCEPTION;
		}
	}
	
	@Override
	public String memASCIIString(DirectBuffer buffer, int length) {
		if (length == -1)
			throw new IllegalArgumentException("Illegal length");
		try {
			return ASCII_DECODER.decode(NioByteBufferWrapper.wrap(buffer, length)).toString();
		} catch (CharacterCodingException e) {
			return CHARACTER_CODING_EXCEPTION;
		}
	}
	
	//static
	public static int findNullCharacter(DirectBuffer buffer) {
		for (int i = 0; i < buffer.capacity(); i++)
			if (buffer.getByte(i) == NULL_CHARACTER)
				return i;
		return -1;
	}
}
