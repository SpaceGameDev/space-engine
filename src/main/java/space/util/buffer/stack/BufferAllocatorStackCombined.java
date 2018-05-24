package space.util.buffer.stack;

import org.jetbrains.annotations.NotNull;
import space.util.baseobject.ToString;
import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;
import space.util.stack.SimpleStack;
import space.util.stack.Stack;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

/**
 * Combines both {@link BufferAllocatorStackBufferList} and {@link BufferAllocatorStackOneBuffer}.
 * Any to be allocated {@link DirectBuffer} below or equal the {@link BufferAllocatorStackCombined#largeThreshold} will be delegated to {@link BufferAllocatorStackOneBuffer OneBuffer},
 * anything larger will be delegated to {@link BufferAllocatorStackBufferList BufferList}.
 */
public class BufferAllocatorStackCombined implements BufferAllocatorStack, ToString {
	
	public static final int DEFAULT_LARGE_THRESHOLD = 64;
	
	public final BufferAllocator alloc;
	public final BufferAllocatorStackOneBuffer oneBuffer;
	public final BufferAllocatorStackBufferList bufferList;
	
	public long largeThreshold;
	public Stack<BiIntEntry> stack = new SimpleStack<>();
	
	//constructor
	public BufferAllocatorStackCombined(BufferAllocator alloc) {
		this(alloc, DEFAULT_LARGE_THRESHOLD);
	}
	
	public BufferAllocatorStackCombined(BufferAllocator alloc, int largeThreshold, FreeableStorage... lists) {
		this.largeThreshold = largeThreshold;
		
		this.alloc = alloc;
		this.oneBuffer = new BufferAllocatorStackOneBuffer(alloc, BufferAllocatorStackOneBuffer.DEFAULT_CAPACITY, null, lists);
		this.bufferList = new BufferAllocatorStackBufferList(alloc, null);
	}
	
	//setLargeThreshold
	public BufferAllocatorStackCombined setLargeThreshold(long largeThreshold) {
		this.largeThreshold = largeThreshold;
		return this;
	}
	
	public BufferAllocatorStackCombined setLargeThresholdEverythingOnStack() {
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
	public <X extends DirectBuffer> X put(X t) {
		throw new UnsupportedOperationException();
	}
	
	//alloc
	@Override
	public DirectBuffer alloc(long address, long capacity, FreeableStorage... parents) {
		return bufferList.alloc(address, capacity, parents);
	}
	
	@Override
	public DirectBuffer allocNoFree(long address, long capacity, FreeableStorage... parents) {
		return alloc.allocNoFree(address, capacity, parents);
	}
	
	@Override
	public DirectBuffer malloc(long capacity, FreeableStorage... parents) {
		return capacity > largeThreshold ? bufferList.malloc(capacity, parents) : oneBuffer.malloc(capacity, parents);
	}
	
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("alloc", this.alloc);
		tsh.add("oneBuffer", this.oneBuffer);
		tsh.add("bufferList", this.bufferList);
		tsh.add("largeThreshold", this.largeThreshold);
		tsh.add("stack", this.stack);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
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
