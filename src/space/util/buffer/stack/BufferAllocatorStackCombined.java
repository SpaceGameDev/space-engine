package space.util.buffer.stack;

import space.util.buffer.alloc.IBufferAllocator;
import space.util.buffer.buffers.IBuffer;
import spaceOld.util.stack.IStack;
import spaceOld.util.stack.Stack;

import java.util.function.Consumer;

public class BufferAllocatorStackCombined implements IBufferAllocatorStack {
	
	public static final int defaultLargeThreshold = 64;
	
	public final IBufferAllocator alloc;
	public final BufferAllocatorStackOneBuffer oneBuffer;
	public final BufferAllocatorStackBufferList bufferList;
	
	public long largeThreshold;
	public IStack<BiIntEntry> stack = new Stack<>();
	
	public BufferAllocatorStackCombined(IBufferAllocator alloc) {
		this(alloc, defaultLargeThreshold);
	}
	
	public BufferAllocatorStackCombined(IBufferAllocator alloc, int largeThreshold) {
		this.largeThreshold = largeThreshold;
		
		this.alloc = alloc;
		this.oneBuffer = new BufferAllocatorStackOneBuffer(alloc);
		this.bufferList = new BufferAllocatorStackBufferList(alloc);
	}
	
	public BufferAllocatorStackCombined setLargeThreshold(long largeThreshold) {
		this.largeThreshold = largeThreshold;
		return this;
	}
	
	public BufferAllocatorStackCombined setLargeThresholdInfinite() {
		return setLargeThreshold(Long.MAX_VALUE);
	}
	
	@Override
	public long pushPointer() {
		return stack.pushPointer(new BiIntEntry(this));
	}
	
	@Override
	public void push() {
		stack.push(new BiIntEntry(this));
	}
	
	@Override
	public void popPointer(long pointer) {
		stack.popPointer(pointer).apply(this);
	}
	
	@Override
	public void pop() {
		stack.pop().apply(this);
	}
	
	@Override
	@Deprecated
	public <X extends IBuffer> X put(X t) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	@Deprecated
	public void setOnDelete(Consumer<IBuffer> onDelete) {
		throw new UnsupportedOperationException();
	}
	
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
	
	@Override
	public IBuffer alloc(long address, long capacity) {
		return bufferList.alloc(address, capacity);
	}
	
	@Override
	public IBuffer malloc(long capacity) {
		if (capacity < largeThreshold) {
			return oneBuffer.malloc(capacity);
		}
		return bufferList.malloc(capacity);
	}
}
