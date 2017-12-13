package space.util.buffer.stack;

import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.buffers.Buffer;
import space.util.stack.IStack;
import space.util.stack.Stack;

public class BufferAllocatorStackCombined implements BufferAllocatorStack {
	
	public static final int DEFAULT_LARGE_THRESHOLD = 64;
	
	public final BufferAllocator alloc;
	public final BufferAllocatorStackOneBuffer oneBuffer;
	public final BufferAllocatorStackBufferList bufferList;
	
	public long largeThreshold;
	public IStack<BiIntEntry> stack = new Stack<>();
	
	//constructor
	public BufferAllocatorStackCombined(BufferAllocator alloc) {
		this(alloc, DEFAULT_LARGE_THRESHOLD);
	}
	
	public BufferAllocatorStackCombined(BufferAllocator alloc, int largeThreshold) {
		this.largeThreshold = largeThreshold;
		
		this.alloc = alloc;
		this.oneBuffer = new BufferAllocatorStackOneBuffer(alloc, BufferAllocatorStackOneBuffer.DEFAULT_CAPACITY, null);
		this.bufferList = new BufferAllocatorStackBufferList(alloc, null);
	}
	
	//setLargeThreshold
	public BufferAllocatorStackCombined setLargeThreshold(long largeThreshold) {
		this.largeThreshold = largeThreshold;
		return this;
	}
	
	public BufferAllocatorStackCombined setLargeThresholdInfinite() {
		return setLargeThreshold(Long.MAX_VALUE);
	}
	
	//push
	@Override
	public long pushPointer() {
		return stack.pushPointer(new BiIntEntry(this));
	}
	
	@Override
	public void push() {
		stack.push(new BiIntEntry(this));
	}
	
	//pop
	@Override
	public void popPointer(long pointer) {
		stack.popPointer(pointer).apply(this);
	}
	
	@Override
	public void pop() {
		stack.pop().apply(this);
	}
	
	//put
	@Override
	@Deprecated
	public <X extends Buffer> X put(X t) {
		throw new UnsupportedOperationException();
	}
	
	//alloc
	@Override
	public Buffer alloc(long address, long capacity) {
		return bufferList.alloc(address, capacity);
	}
	
	@Override
	public Buffer malloc(long capacity) {
		return capacity > largeThreshold ? bufferList.malloc(capacity) : oneBuffer.malloc(capacity);
	}
	
	//entry
	public static class BiIntEntry {
		
		public long oneBufferPointer;
		public long bufferListPointer;
		
		public BiIntEntry(BufferAllocatorStackCombined b) {
			oneBufferPointer = b.oneBuffer.pushPointer();
			bufferListPointer = b.bufferList.pushPointer();
		}
		
		public void apply(BufferAllocatorStackCombined b) {
			b.oneBuffer.popPointer(oneBufferPointer);
			b.bufferList.popPointer(bufferListPointer);
		}
	}
}
