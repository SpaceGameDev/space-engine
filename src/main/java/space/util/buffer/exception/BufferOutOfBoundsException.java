package space.util.buffer.exception;

import space.util.string.builder.CharBufferBuilder1D;

public class BufferOutOfBoundsException extends BufferException {
	
	public BufferOutOfBoundsException() {
		super();
	}
	
	public BufferOutOfBoundsException(String message) {
		super(message);
	}
	
	public BufferOutOfBoundsException(Throwable cause) {
		super(cause);
	}
	
	public BufferOutOfBoundsException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BufferOutOfBoundsException(long address, long capacity, long offset) {
		super(makeMessage(address, offset, capacity));
	}
	
	public BufferOutOfBoundsException(long address, long capacity, long offset, long length) {
		super(makeMessage(address, offset, length, capacity));
	}
	
	private static String makeMessage(long address, long capacity, long offset, long length) {
		//@formatter:off
		return new CharBufferBuilder1D<>()
				.append("address: ").append(address)
				.append(" >= offset: ").append(offset)
				.append(" +  length: ").append(length)
				.append(" -> end: ").append(offset + length)
				.append(" > capacity: ").append(capacity).toString();
		//@formatter:on
	}
	
	private static String makeMessage(long address, long capacity, long offset) {
		//@formatter:off
		return new CharBufferBuilder1D<>()
				.append("address: ").append(address)
				.append(" >= offset: ").append(offset)
				.append(" > capacity: ").append(capacity)
				.toString();
		//@formatter:on
	}
}
