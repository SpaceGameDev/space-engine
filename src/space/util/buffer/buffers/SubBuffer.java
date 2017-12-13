package space.util.buffer.buffers;

public class SubBuffer extends BufferImpl {
	
	public final Object parent;
	
	public SubBuffer(long address, long capacity, Object parent) {
		super(address, capacity);
		this.parent = parent;
	}
}
