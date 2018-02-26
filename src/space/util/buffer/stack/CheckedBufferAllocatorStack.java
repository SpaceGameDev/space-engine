package space.util.buffer.stack;

import space.util.baseobject.ToString;
import space.util.buffer.buffers.Buffer;
import space.util.buffer.buffers.CheckedBuffer;
import space.util.freeableStorage.IFreeableStorage;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

/**
 * wraps every created buffer in a {@link CheckedBuffer}
 */
public class CheckedBufferAllocatorStack implements BufferAllocatorStack, ToString {
	
	public BufferAllocatorStack alloc;
	
	public CheckedBufferAllocatorStack(BufferAllocatorStack alloc) {
		this.alloc = alloc;
	}
	
	//BufferAllocator
	@Override
	public Buffer malloc(long capacity, IFreeableStorage... parents) {
		return new CheckedBuffer(alloc.malloc(capacity, parents));
	}
	
	@Override
	public Buffer alloc(long address, long capacity, IFreeableStorage... parents) {
		return new CheckedBuffer(alloc.alloc(address, capacity, parents));
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
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("alloc", this.alloc);
		return tsh.build();
	}
}
