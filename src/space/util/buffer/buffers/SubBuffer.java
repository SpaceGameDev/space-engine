package space.util.buffer.buffers;

public class SubBuffer extends NotFreeableBuffer {
	
	public Object parent;
	
	public SubBuffer(long address, long capacity, Object parent) {
		super(address, capacity);
		this.parent = parent;
	}
	
	@Override
	public synchronized void free() {
		super.free();
		parent = null;
	}
}
