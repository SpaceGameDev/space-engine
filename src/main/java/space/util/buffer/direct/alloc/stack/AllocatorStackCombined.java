package space.util.buffer.direct.alloc.stack;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import space.util.baseobject.ToString;
import space.util.buffer.Allocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;
import space.util.stack.SimpleStack;
import space.util.stack.Stack;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

/**
 * Combines both {@link BufferAllocatorStackBufferList} and {@link AllocatorStackOneBuffer}.
 * Any to be allocated {@link DirectBuffer} below or equal the {@link AllocatorStackCombined#largeThreshold} will be delegated to {@link AllocatorStackOneBuffer OneBuffer},
 * anything larger will be delegated to {@link BufferAllocatorStackBufferList BufferList}.
 */
public class AllocatorStackCombined implements AllocatorStack<DirectBuffer>, ToString {
	
	public static final int DEFAULT_LARGE_THRESHOLD = 64;
	
	public final Allocator<DirectBuffer> alloc;
	public final AllocatorStackOneBuffer oneBuffer;
	public final BufferAllocatorStackBufferList bufferList;
	
	public long largeThreshold;
	public Stack<BiIntEntry> stack = new SimpleStack<>();
	
	//constructor
	public AllocatorStackCombined(Allocator<DirectBuffer> alloc) {
		this(alloc, DEFAULT_LARGE_THRESHOLD);
	}
	
	@SuppressWarnings("ConstantConditions")
	public AllocatorStackCombined(Allocator<DirectBuffer> alloc, int largeThreshold, FreeableStorage... lists) {
		this.alloc = alloc;
		this.oneBuffer = new AllocatorStackOneBuffer(alloc, AllocatorStackOneBuffer.DEFAULT_CAPACITY, null, lists);
		this.bufferList = new BufferAllocatorStackBufferList(alloc, null);
		this.largeThreshold = largeThreshold;
	}
	
	//setLargeThreshold
	public AllocatorStackCombined setLargeThreshold(long largeThreshold) {
		this.largeThreshold = largeThreshold;
		return this;
	}
	
	public AllocatorStackCombined setLargeThresholdEverythingOnStack() {
		return setLargeThreshold(Long.MAX_VALUE);
	}
	
	//stack
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
	
	//stack -> unsupported
	@Override
	@Deprecated
	@Contract("_ -> fail")
	public <X extends DirectBuffer> X put(X t) {
		throw new UnsupportedOperationException();
	}
	
	//create
	@NotNull
	@Override
	public DirectBuffer create(long address, long capacity, @NotNull FreeableStorage... parents) {
		return bufferList.create(address, capacity, parents);
	}
	
	@NotNull
	@Override
	public DirectBuffer createNoFree(long address, long capacity, @NotNull FreeableStorage... parents) {
		return alloc.createNoFree(address, capacity, parents);
	}
	
	//malloc
	@NotNull
	@Override
	public DirectBuffer malloc(long capacity, @NotNull FreeableStorage... parents) {
		return capacity > largeThreshold ? bufferList.malloc(capacity, parents) : oneBuffer.malloc(capacity, parents);
	}
	
	@NotNull
	@Override
	public DirectBuffer calloc(long capacity, @NotNull FreeableStorage... parents) {
		DirectBuffer buffer = malloc(capacity, parents);
		buffer.clear();
		return buffer;
	}
	
	//toString
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("create", this.alloc);
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
		
		public BiIntEntry(AllocatorStackCombined b) {
			oneBufferPointer = b.oneBuffer.pushPointer();
			bufferListPointer = b.bufferList.pushPointer();
		}
		
		public void apply(AllocatorStackCombined b) {
			b.oneBuffer.popPointer(oneBufferPointer);
			b.bufferList.popPointer(bufferListPointer);
		}
	}
}
