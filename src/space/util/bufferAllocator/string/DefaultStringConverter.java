package space.util.bufferAllocator.string;

import space.util.SpaceException;
import space.util.bufferAllocator.alloc.IBufferAllocator;
import space.util.bufferAllocator.buffers.IBuffer;

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

import static spaceOld.util.UnsafeInstance.*;

public class DefaultStringConverter implements IBufferStringConverter {
	
	public static Class<?> byteBufferClass;
	public static long byteBufferParent;
	public static long byteBufferAddress;
	public static long byteBufferCapacity;
	
	static {
		try {
			ByteBuffer parent = ByteBuffer.allocateDirect(0);
			ByteBuffer bb = parent.slice();
			byteBufferClass = bb.getClass();
			Field f = find(bb, parent);
			byteBufferParent = unsafe.objectFieldOffset(f);
			byteBufferAddress = objectFieldOffsetSub(byteBufferClass, "address");
			byteBufferCapacity = objectFieldOffsetSub(byteBufferClass, "capacity");
		} catch (Exception e) {
			throw new SpaceException(e);
		}
	}
	
	public IBufferAllocator alloc;
	
	@Override
	public void setAlloc(IBufferAllocator alloc) {
		this.alloc = alloc;
	}
	
	@Override
	public IBufferAllocator alloc() {
		return alloc;
	}
	
	@Override
	public IBuffer memStringUTF8(String str, boolean nullTerm) {
		byte[] ba = new byte[memStringUTF8Length(str, nullTerm)];
		ByteBuffer buffer = ByteBuffer.wrap(ba);
		StringConverterUtil.util.UTF8Encoder.encode(CharBuffer.wrap(str), buffer, false);
		if (nullTerm)
			buffer.put(StringConverterUtil.NULLCHARACTER);
		return alloc().allocByte(ba);
	}
	
	@Override
	public IBuffer memStringUTF16(String str, boolean nullTerm) {
		byte[] ba = new byte[memStringUTF16Length(str, nullTerm)];
		ByteBuffer buffer = ByteBuffer.wrap(ba);
		StringConverterUtil.util.UTF16Encoder.encode(CharBuffer.wrap(str), buffer, false);
		if (nullTerm)
			buffer.put(StringConverterUtil.TWONULLCHARACTER);
		return alloc().allocByte(ba);
	}
	
	@Override
	public IBuffer memStringASCII(String str, boolean nullTerm) {
		byte[] ba = new byte[memStringASCIILength(str, nullTerm)];
		ByteBuffer buffer = ByteBuffer.wrap(ba);
		StringConverterUtil.util.ASCIIEncoder.encode(CharBuffer.wrap(str), buffer, false);
		if (nullTerm)
			buffer.put(StringConverterUtil.NULLCHARACTER);
		return alloc().allocByte(ba);
	}
	
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
	
	@Override
	public String memUTF8String(IBuffer buffer) {
		return memUTF8String(buffer, findNull(buffer));
	}
	
	@Override
	public String memUTF16String(IBuffer buffer) {
		return memUTF16String(buffer, findNull(buffer));
	}
	
	@Override
	public String memASCIIString(IBuffer buffer) {
		return memASCIIString(buffer, findNull(buffer));
	}
	
	static int findNull(IBuffer buffer) {
		byte[] str = new byte[(int) buffer.capacity()];
		buffer.getByte(str);
		for (int i = 0; i < str.length; i++)
			if (str[i] == StringConverterUtil.NULLCHARACTER)
				return i;
		return -1;
	}
	
	@Override
	public String memUTF8String(IBuffer buffer, int length) {
		if (length == -1)
			return "Invalid Length";
		try {
			return StringConverterUtil.util.UTF8Decoder.decode(wrap(buffer)).toString().substring(0, length);
		} catch (CharacterCodingException e) {
			return "CharacterCodingException";
		}
	}
	
	@Override
	public String memUTF16String(IBuffer buffer, int length) {
		if (length == -1)
			return "Invalid Length";
		try {
			return StringConverterUtil.util.UTF16Decoder.decode(wrap(buffer)).toString().substring(0, length);
		} catch (CharacterCodingException e) {
			return "CharacterCodingException";
		}
	}
	
	@Override
	public String memASCIIString(IBuffer buffer, int length) {
		if (length == -1)
			return "Invalid Length";
		try {
			return StringConverterUtil.util.ASCIIDecoder.decode(wrap(buffer)).toString().substring(0, length);
		} catch (CharacterCodingException e) {
			return "CharacterCodingException";
		}
	}
	
	public ByteBuffer wrap(IBuffer buffer) {
		try {
			ByteBuffer b = (ByteBuffer) unsafe.allocateInstance(byteBufferClass);
			unsafe.putLong(b, byteBufferAddress, buffer.address());
			unsafe.putInt(b, byteBufferCapacity, buffer.capacityInt());
			unsafe.putObject(b, byteBufferParent, null);
			return b;
		} catch (InstantiationException e) {
			throw new SpaceException(e);
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
	
	public static class StringConverterUtil {
		
		public static final StringConverterUtil util = new StringConverterUtil();
		
		public static final byte NULLCHARACTER = 0;
		public static final byte[] TWONULLCHARACTER = new byte[] {NULLCHARACTER, NULLCHARACTER};
		
		public final Charset UTF8Charset;
		public final CharsetEncoder UTF8Encoder;
		public final CharsetDecoder UTF8Decoder;
		
		public final Charset UTF16Charset;
		public final CharsetEncoder UTF16Encoder;
		public final CharsetDecoder UTF16Decoder;
		
		public final Charset ASCIICharset;
		public final CharsetEncoder ASCIIEncoder;
		public final CharsetDecoder ASCIIDecoder;
		
		public StringConverterUtil() {
			UTF8Charset = StandardCharsets.UTF_8;
			UTF8Encoder = UTF8Charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
			UTF8Decoder = UTF8Charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
			
			UTF16Charset = StandardCharsets.UTF_16;
			UTF16Encoder = UTF16Charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
			UTF16Decoder = UTF16Charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
			
			ASCIICharset = StandardCharsets.US_ASCII;
			ASCIIEncoder = ASCIICharset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
			ASCIIDecoder = ASCIICharset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
		}
	}
}
