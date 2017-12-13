package space.util.buffer.buffers;

public class NotFreeableBuffer extends BufferImpl {
	
	public NotFreeableBuffer(long capacity) {
		super(capacity);
	}
	
	public NotFreeableBuffer(long address, long capacity) {
		super(address, capacity);
	}
	
	@Override
	public synchronized void free() {
		address = 0;
		capacity = 0;
	}
}
