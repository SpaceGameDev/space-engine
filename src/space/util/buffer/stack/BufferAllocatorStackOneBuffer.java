package space.util.buffer.stack;

import space.util.ArrayUtils;
import space.util.baseobject.ToString;
import space.util.baseobject.additional.Dumpable;
import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.buffer.direct.SubDirectBuffer;
import space.util.freeableStorage.FreeableStorage;
import space.util.freeableStorage.IFreeableStorage;
import space.util.stack.PointerList;
import space.util.string.String2D;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Arrays;

/**
 * A {@link BufferAllocatorStack} which has one big {@link DirectBuffer} and will create {@link SubDirectBuffer SubBuffers} of that {@link DirectBuffer}, also increasing it in size if necessary.
 */
public class BufferAllocatorStackOneBuffer implements BufferAllocatorStack, ToString, Dumpable {
	
	public static final long DEFAULT_CAPACITY = 1024;
	
	public BufferAllocator alloc;
	public FreeableStorage storage;
	public DirectBuffer buffer;
	public long topOfStack = 0;
	public PointerList pointerList;
	
	//constructor
	public BufferAllocatorStackOneBuffer(BufferAllocator alloc, IFreeableStorage... lists) {
		this(alloc, DEFAULT_CAPACITY, lists);
	}
	
	public BufferAllocatorStackOneBuffer(BufferAllocator alloc, long initCapacity, IFreeableStorage... lists) {
		this(alloc, initCapacity, new PointerList(), lists);
	}
	
	protected BufferAllocatorStackOneBuffer(BufferAllocator alloc, long initCapacity, PointerList pointerList, IFreeableStorage[] lists) {
		this.alloc = alloc;
		this.storage = IFreeableStorage.createAnonymous(lists);
		makeInternalBuffer(initCapacity);
		this.pointerList = pointerList;
	}
	
	//expansion
	public void ensureCapacity(long capacity) {
		long bufferCapa = buffer.capacity();
		if (bufferCapa < capacity)
			makeInternalBuffer(ArrayUtils.getOptimalArraySizeExpansion(bufferCapa, capacity, 1));
	}
	
	public void makeInternalBuffer(long capacity) {
		buffer = alloc.malloc(capacity, storage);
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
	public <X extends DirectBuffer> X put(X t) {
		throw new UnsupportedOperationException();
	}
	
	//alloc
	protected long allocInternal(long capacity) {
		long oldTopOfStack = topOfStack;
		ensureCapacity(topOfStack += capacity);
		return buffer.address() + oldTopOfStack;
	}
	
	@Override
	public DirectBuffer malloc(long capacity, IFreeableStorage... parents) {
		IFreeableStorage[] list2 = Arrays.copyOf(parents, parents.length + 1);
		list2[parents.length] = buffer.getStorage();
		return new SubDirectBuffer(allocInternal(capacity), capacity, buffer, list2);
	}
	
	@Override
	public DirectBuffer alloc(long address, long capacity, IFreeableStorage... parents) {
		return alloc.alloc(address, capacity, parents);
	}
	
	@Override
	public DirectBuffer allocNoFree(long address, long capacity, IFreeableStorage... parents) {
		return alloc.allocNoFree(address, capacity, parents);
	}
	
	//dump
	public String2D dump() {
		return buffer.dump();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("alloc", this.alloc);
		tsh.add("storage", this.storage);
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
