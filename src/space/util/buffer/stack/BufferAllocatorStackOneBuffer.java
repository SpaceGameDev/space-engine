package space.util.buffer.stack;

import space.util.ArrayUtils;
import space.util.baseobject.ToString;
import space.util.baseobject.additional.Dumpable;
import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.buffers.Buffer;
import space.util.buffer.buffers.BufferImpl;
import space.util.buffer.buffers.SubBuffer;
import space.util.stack.PointerList;
import space.util.string.String2D;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class BufferAllocatorStackOneBuffer implements BufferAllocatorStack, ToString, Dumpable {
	
	public static final long DEFAULT_CAPACITY = 1024;
	
	public BufferAllocator alloc;
	public BufferImpl buffer;
	public long topOfStack = 0;
	public PointerList pointerList;
	
	//constructor
	public BufferAllocatorStackOneBuffer(BufferAllocator alloc) {
		this(alloc, DEFAULT_CAPACITY);
	}
	
	public BufferAllocatorStackOneBuffer(BufferAllocator alloc, long initCapacity) {
		this(alloc, initCapacity, new PointerList());
	}
	
	protected BufferAllocatorStackOneBuffer(BufferAllocator alloc, long initCapacity, PointerList pointerList) {
		this.alloc = alloc;
		makeInternalBuffer(initCapacity);
		this.pointerList = pointerList;
	}
	
	//expansion
	public void ensureCapacity(long capacity) {
		if (buffer.capacity < capacity)
			makeInternalBuffer(ArrayUtils.getOptimalArraySizeExpansion(buffer.capacity, capacity, 1));
	}
	
	public void makeInternalBuffer(long capacity) {
		buffer = new BufferImpl(capacity);
	}
	
	//push
	@Override
	public long pushPointer() {
		return topOfStack;
	}
	
	@Override
	public void push() {
		pointerList.push(topOfStack);
	}
	
	//pop
	@Override
	public void popPointer(long pointer) {
		this.topOfStack = pointer;
	}
	
	@Override
	public void pop() {
		popPointer(pointerList.pop());
	}
	
	//put
	@Override
	@Deprecated
	public <X extends Buffer> X put(X t) {
		throw new UnsupportedOperationException();
	}
	
	//alloc
	protected long allocInternal(long capacity) {
		long oldTopOfStack = topOfStack;
		ensureCapacity(topOfStack += capacity);
		return buffer.address + oldTopOfStack;
	}
	
	@Override
	public Buffer malloc(long capacity) {
		return new SubBuffer(allocInternal(capacity), capacity, buffer);
	}
	
	@Override
	public Buffer alloc(long address, long capacity) {
		return alloc.alloc(address, capacity);
	}
	
	//dump
	public String2D dump() {
		return buffer.dump();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("alloc", this.alloc);
		tsh.add("buffer", this.buffer);
		tsh.add("topOfStack", this.topOfStack);
		tsh.add("pointerList", this.pointerList);
		tsh.add("dump", dump());
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
