package space.util.buffer.string;

import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.buffers.Buffer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

import static space.util.unsafe.UnsafeInstance.*;

public class DefaultStringConverter implements IBufferStringConverter {
	
	public static final String CHARACTER_CODING_EXCEPTION = "CHARACTER_CODING_EXCEPTION";
	public static final byte NULL_CHARACTER = 0;
	public static final byte[] TWO_NULL_CHARACTERS = new byte[] {NULL_CHARACTER, NULL_CHARACTER};
	
	public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;
	public static final CharsetEncoder UTF8_ENCODER = UTF8_CHARSET.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
	public static final CharsetDecoder UTF8_DECODER = UTF8_CHARSET.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
	public static final Charset UTF16_CHARSET = StandardCharsets.UTF_16;
	public static final CharsetEncoder UTF16_ENCODER = UTF16_CHARSET.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
	public static final CharsetDecoder UTF16_DECODER = UTF16_CHARSET.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
	public static final Charset ASCII_CHARSET = StandardCharsets.US_ASCII;
	public static final CharsetEncoder ASCII_ENCODER = ASCII_CHARSET.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
	public static final CharsetDecoder ASCII_DECODER = ASCII_CHARSET.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
	
	public static final Class<?> BYTE_BUFFER_CLASS;
	public static final long BYTE_BUFFER_PARENT;
	public static final long BYTE_BUFFER_ADDRESS;
	public static final long BYTE_BUFFER_CAPACITY;
	
	static {
		throwIfUnavailable();
		try {
			ByteBuffer parent = ByteBuffer.allocateDirect(0);
			ByteBuffer bb = parent.slice();
			BYTE_BUFFER_CLASS = bb.getClass();
			Field f = find(bb, parent);
			BYTE_BUFFER_PARENT = UNSAFE.objectFieldOffset(f);
			BYTE_BUFFER_ADDRESS = objectFieldOffsetWithSuper(BYTE_BUFFER_CLASS, "address");
			BYTE_BUFFER_CAPACITY = objectFieldOffsetWithSuper(BYTE_BUFFER_CLASS, "capacity");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public BufferAllocator alloc;
	
	public DefaultStringConverter(BufferAllocator alloc) {
		this.alloc = alloc;
	}
	
	//memString from String
	@Override
	public Buffer memStringUTF8(String str, boolean nullTerm) {
		byte[] ba = new byte[memStringUTF8Length(str, nullTerm)];
		ByteBuffer buffer = ByteBuffer.wrap(ba);
		UTF8_ENCODER.encode(CharBuffer.wrap(str), buffer, false);
		if (nullTerm)
			buffer.put(NULL_CHARACTER);
		return alloc.allocByte(ba);
	}
	
	@Override
	public Buffer memStringUTF16(String str, boolean nullTerm) {
		byte[] ba = new byte[memStringUTF16Length(str, nullTerm)];
		ByteBuffer buffer = ByteBuffer.wrap(ba);
		UTF16_ENCODER.encode(CharBuffer.wrap(str), buffer, false);
		if (nullTerm)
			buffer.put(TWO_NULL_CHARACTERS);
		return alloc.allocByte(ba);
	}
	
	@Override
	public Buffer memStringASCII(String str, boolean nullTerm) {
		byte[] ba = new byte[memStringASCIILength(str, nullTerm)];
		ByteBuffer buffer = ByteBuffer.wrap(ba);
		ASCII_ENCODER.encode(CharBuffer.wrap(str), buffer, false);
		if (nullTerm)
			buffer.put(NULL_CHARACTER);
		return alloc.allocByte(ba);
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
	public String memUTF8String(Buffer buffer) {
		return memUTF8String(buffer, findNull(buffer));
	}
	
	@Override
	public String memUTF16String(Buffer buffer) {
		return memUTF16String(buffer, findNull(buffer));
	}
	
	@Override
	public String memASCIIString(Buffer buffer) {
		return memASCIIString(buffer, findNull(buffer));
	}
	
	//memString from Buffer with length
	@Override
	public String memUTF8String(Buffer buffer, int length) {
		if (length == -1)
			throw new IllegalArgumentException("Illegal length");
		try {
			return UTF8_DECODER.decode(wrap(buffer, length)).toString();
		} catch (CharacterCodingException e) {
			return CHARACTER_CODING_EXCEPTION;
		}
	}
	
	@Override
	public String memUTF16String(Buffer buffer, int length) {
		if (length == -1)
			throw new IllegalArgumentException("Illegal length");
		try {
			return UTF16_DECODER.decode(wrap(buffer, length)).toString();
		} catch (CharacterCodingException e) {
			return CHARACTER_CODING_EXCEPTION;
		}
	}
	
	@Override
	public String memASCIIString(Buffer buffer, int length) {
		if (length == -1)
			throw new IllegalArgumentException("Illegal length");
		try {
			return ASCII_DECODER.decode(wrap(buffer, length)).toString();
		} catch (CharacterCodingException e) {
			return CHARACTER_CODING_EXCEPTION;
		}
	}
	
	//static
	static int findNull(Buffer buffer) {
		for (int i = (int) (buffer.capacity() - 1); i >= 0; i--)
			if (buffer.getByte(i) != NULL_CHARACTER)
				return i + 1;
		return -1;
	}
	
	public static ByteBuffer wrap(Buffer buffer, int length) {
		if (length < buffer.capacity())
			throw new RuntimeException("length exceeds capacity!");
		try {
			ByteBuffer b = (ByteBuffer) UNSAFE.allocateInstance(BYTE_BUFFER_CLASS);
			UNSAFE.putLong(b, BYTE_BUFFER_ADDRESS, buffer.address());
			UNSAFE.putInt(b, BYTE_BUFFER_CAPACITY, length);
			UNSAFE.putObject(b, BYTE_BUFFER_PARENT, null);
			return b;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Field find(Object o, Object find) throws IllegalArgumentException, IllegalAccessException {
		Class<?> s = o.getClass();
		while (s != null) {
			for (Field f : s.getDeclaredFields()) {
				if (Modifier.isStatic(f.getModifiers()))
					continue;
				f.setAccessible(true);
				if (f.get(o) == find) {
					return f;
				}
			}
			s = s.getSuperclass();
		}
		return null;
	}
}
