package space.util.bufferAllocator.buffers;

public class BufferOutOfBoundsException extends RuntimeException {
	
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
	
	public BufferOutOfBoundsException(long offset, long length) {
		super(makeMessage(offset, length));
	}
	
	public BufferOutOfBoundsException(long offset) {
		super(makeMessage(offset));
	}
	
	private static String makeMessage(long offset, long length) {
		return "offset: " + offset + ", length: " + length;
	}
	
	private static String makeMessage(long offset) {
		return "offset: " + offset;
	}
}
