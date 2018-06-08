package space.util.buffer.direct.alloc.stack;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import space.util.ArrayUtils;
import space.util.baseobject.Dumpable;
import space.util.baseobject.ToString;
import space.util.buffer.Allocator;
import space.util.buffer.direct.DirectBuffer;
import space.util.buffer.direct.SubDirectBuffer;
import space.util.freeableStorage.FreeableStorage;
import space.util.freeableStorage.FreeableStorageImpl;
import space.util.stack.PointerList;
import space.util.string.String2D;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Arrays;

/**
 * A {@link AllocatorStack} which has one big {@link DirectBuffer} and will create {@link SubDirectBuffer SubBuffers} of that {@link DirectBuffer}, also increasing it in size if necessary.
 */
public class AllocatorStackOneBuffer implements AllocatorStack<DirectBuffer>, ToString, Dumpable {
	
	public static final long DEFAULT_CAPACITY = 1024;
	
	@NotNull
	public Allocator<DirectBuffer> alloc;
	@NotNull
	public FreeableStorageImpl storage;
	@SuppressWarnings("NullableProblems")
	@NotNull
	public DirectBuffer buffer;
	public long topOfStack = 0;
	@NotNull
	public PointerList pointerList;
	
	//constructor
	public AllocatorStackOneBuffer(@NotNull Allocator<DirectBuffer> alloc, FreeableStorage... parents) {
		this(alloc, DEFAULT_CAPACITY, parents);
	}
	
	public AllocatorStackOneBuffer(@NotNull Allocator<DirectBuffer> alloc, long initCapacity, FreeableStorage... parents) {
		this(alloc, initCapacity, new PointerList(), parents);
	}
	
	protected AllocatorStackOneBuffer(@NotNull Allocator<DirectBuffer> alloc, long initCapacity, @NotNull PointerList pointerList, FreeableStorage... parents) {
		this.alloc = alloc;
		this.storage = FreeableStorage.createDummy(parents);
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
	
	//stack
	@Override
	public long pushPointer() {
		return topOfStack;
	}
	
	@Override
	public void push() {
		pointerList.push(topOfStack);
	}
	
	@Override
	public void popPointer(long pointer) {
		this.topOfStack = pointer;
	}
	
	@Override
	public void pop() {
		popPointer(pointerList.pop());
	}
	
	//stack -> unsupported
	@Override
	@Deprecated
	@Contract("_ -> fail")
	public <X extends DirectBuffer> X put(X t) {
		throw new UnsupportedOperationException();
	}
	
	//malloc
	protected long mallocInternal(long capacity) {
		long oldTopOfStack = topOfStack;
		ensureCapacity(topOfStack += capacity);
		return buffer.address() + oldTopOfStack;
	}
	
	@NotNull
	@Override
	public DirectBuffer malloc(long capacity, @NotNull FreeableStorage... parents) {
		FreeableStorage[] parents2 = Arrays.copyOf(parents, parents.length + 1);
		parents2[parents.length] = buffer.getStorage();
		return new SubDirectBuffer(mallocInternal(capacity), capacity, buffer, parents2);
	}
	
	@NotNull
	@Override
	public DirectBuffer calloc(long capacity, @NotNull FreeableStorage... parents) {
		DirectBuffer buffer = malloc(capacity, parents);
		buffer.clear();
		return buffer;
	}
	
	//create -> unsupported
	@NotNull
	@Override
	@Deprecated
	@Contract("_, _, _ -> fail")
	public DirectBuffer create(long address, long capacity, @NotNull FreeableStorage... parents) {
		throw new UnsupportedOperationException();
	}
	
	@NotNull
	@Override
	@Deprecated
	@Contract("_, _, _ -> fail")
	public DirectBuffer createNoFree(long address, long capacity, @NotNull FreeableStorage... parents) {
		throw new UnsupportedOperationException();
	}
	
	//dump
	@NotNull
	public String2D dump() {
		return buffer.dump();
	}
	
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("create", this.alloc);
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
