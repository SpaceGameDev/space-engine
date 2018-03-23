package space.util.buffer.stack;

import space.util.baseobject.ToString;
import space.util.buffer.direct.CheckedDirectBuffer;
import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.IFreeableStorage;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

/**
 * wraps every created buffer in a {@link CheckedDirectBuffer}
 */
public class CheckedBufferAllocatorStack implements BufferAllocatorStack, ToString {
	
	public BufferAllocatorStack alloc;
	
	public CheckedBufferAllocatorStack(BufferAllocatorStack alloc) {
		this.alloc = alloc;
	}
	
	//BufferAllocator
	@Override
	public DirectBuffer malloc(long capacity, IFreeableStorage... parents) {
		return new CheckedDirectBuffer(alloc.malloc(capacity, parents));
	}
	
	@Override
	public DirectBuffer alloc(long address, long capacity, IFreeableStorage... parents) {
		return new CheckedDirectBuffer(alloc.alloc(address, capacity, parents));
	}
	
	@Override
	public DirectBuffer allocNoFree(long address, long capacity, IFreeableStorage... parents) {
		return new CheckedDirectBuffer(alloc.allocNoFree(address, capacity, parents));
	}
	
	//MultiStack
	@Override
	public <X extends DirectBuffer> X put(X t) {
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
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("alloc", this.alloc);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
