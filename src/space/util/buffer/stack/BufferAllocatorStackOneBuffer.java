package space.util.buffer.stack;

import space.util.ArrayUtils;
import space.util.baseobject.ToString;
import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.buffers.Buffer;
import space.util.buffer.buffers.SimpleBuffer;
import space.util.buffer.buffers.SubBuffer;
import space.util.stack.PointerList;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class BufferAllocatorStackOneBuffer implements BufferAllocatorStack, ToString {
	
	public static final long DEFAULTCAPACITY = 1024;
	public static final long DUMPCAP = 1024;
	
	public BufferAllocator alloc;
	public SimpleBuffer buffer;
	public long topOfStack = 0;
	public PointerList pointerList = new PointerList();
	
	public BufferAllocatorStackOneBuffer(BufferAllocator alloc) {
		this(alloc, DEFAULTCAPACITY);
	}
	
	//constructor
	public BufferAllocatorStackOneBuffer(BufferAllocator alloc, long initCapacity) {
		this.alloc = alloc;
		makeInternalBuffer(initCapacity);
	}
	
	//expansion
	public void ensureCapacity(long capacity) {
		if (buffer.capacity < capacity)
			makeInternalBuffer(ArrayUtils.getOptimalArraySizeExpansion(buffer.capacity, capacity, 1));
	}
	
	public void makeInternalBuffer(long capacity) {
		buffer = new SimpleBuffer(capacity);
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
		topOfStack += capacity;
		ensureCapacity(topOfStack);
		
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
	public String dump() {
		if (buffer.capacity > DUMPCAP)
			return "DUMP CAP reached!";
		StringBuilder b = new StringBuilder((int) buffer.capacity * 3);
		StringBuilderWrapperCommaPolicy bw = new StringBuilderWrapperCommaPolicy(b);
		for (long i = 0; i < buffer.capacity; i++) {
			HexFormat.toHex(bw, unsafe.getByte(buffer.address + i));
			b.append(' ');
		}
		b.setLength(b.length() - 1);
		return b.toString();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("alloc", this.alloc);
		tsh.add("buffer", this.buffer);
		tsh.add("topOfStack", this.topOfStack);
		tsh.add("pointerList", this.pointerList);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
