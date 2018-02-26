package space.util.buffer.stack;

import space.util.buffer.buffers.Buffer;
import space.util.buffer.buffers.CheckedBuffer;
import space.util.ref.freeable.IFreeableStorage;

public class CheckedBufferAllocatorStack implements BufferAllocatorStack {
	
	public BufferAllocatorStack alloc;
	
	public CheckedBufferAllocatorStack(BufferAllocatorStack alloc) {
		this.alloc = alloc;
	}
	
	//BufferAllocator
	@Override
	public Buffer malloc(long capacity, IFreeableStorage... lists) {
		return new CheckedBuffer(alloc.malloc(capacity, lists));
	}
	
	@Override
	public Buffer alloc(long address, long capacity, IFreeableStorage... lists) {
		return new CheckedBuffer(alloc.alloc(address, capacity, lists));
	}
	
	//MultiStack
	@Override
	public <X extends Buffer> X put(X t) {
		return alloc.put(t);
	}
	
	@Override
	public void push() {
		alloc.push();
	}
	
	@Override
	public long pushPointer() {
		return alloc.pushPointer();
	}
	
	@Override
	public void pop() {
		alloc.pop();
	}
	
	@Override
	public void popPointer(long pointer) {
		alloc.popPointer(pointer);
	}
}
