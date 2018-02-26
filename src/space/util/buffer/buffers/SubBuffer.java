package space.util.buffer.buffers;

import space.util.ref.freeable.IFreeableStorage;

public class SubBuffer extends NotFreeableBuffer {
	
	public Object parent;
	
	public SubBuffer(long address, long capacity, Object parent, IFreeableStorage... lists) {
		super(address, capacity, lists);
		this.parent = parent;
	}
	
	@Override
	public synchronized void free() {
		super.free();
		parent = null;
	}
}
