package space.engine.buffer.direct.alloc.stack;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import space.engine.ArrayUtils;
import space.engine.baseobject.Dumpable;
import space.engine.baseobject.ToString;
import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.buffer.direct.SubDirectBuffer;
import space.engine.freeableStorage.Freeable;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.stack.PointerList;
import space.engine.string.String2D;
import space.engine.string.toStringHelper.ToStringHelper;
import space.engine.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Arrays;

/**
 * A {@link AllocatorStack} which has one big {@link DirectBuffer} and will create {@link SubDirectBuffer SubBuffers} of that {@link DirectBuffer}, also increasing it in size if necessary.
 */
public class AllocatorStackOneBuffer implements AllocatorStack<DirectBuffer>, ToString, Dumpable {
	
	public static final long DEFAULT_CAPACITY = 1024;
	
	@NotNull
	public Allocator<DirectBuffer> alloc;
	@NotNull
	public FreeableStorage storage;
	@SuppressWarnings("NullableProblems")
	@NotNull
	public DirectBuffer buffer;
	public long topOfStack = 0;
	@NotNull
	public PointerList pointerList;
	
	//constructor
	public AllocatorStackOneBuffer(@NotNull Allocator<DirectBuffer> alloc, Object[] parents) {
		this(alloc, DEFAULT_CAPACITY, parents);
	}
	
	public AllocatorStackOneBuffer(@NotNull Allocator<DirectBuffer> alloc, long initCapacity, Object[] parents) {
		this(alloc, initCapacity, new PointerList(), parents);
	}
	
	protected AllocatorStackOneBuffer(@NotNull Allocator<DirectBuffer> alloc, long initCapacity, @SuppressWarnings("NullableProblems") @NotNull PointerList pointerList, Object[] parents) {
		this.alloc = alloc;
		this.storage = Freeable.createDummy(parents);
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
		buffer = alloc.malloc(capacity, new Object[] {storage});
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
	public DirectBuffer malloc(long capacity, @NotNull Object[] parents) {
		Object[] parents2 = Arrays.copyOf(parents, parents.length + 1);
		parents2[parents.length] = buffer;
		return new SubDirectBuffer(mallocInternal(capacity), capacity, buffer, parents2);
	}
	
	@NotNull
	@Override
	public DirectBuffer calloc(long capacity, @NotNull Object[] parents) {
		DirectBuffer buffer = malloc(capacity, parents);
		buffer.clear();
		return buffer;
	}
	
	//create -> unsupported
	@NotNull
	@Override
	@Deprecated
	@Contract("_, _, _ -> fail")
	public DirectBuffer create(long address, long capacity, @NotNull Object[] parents) {
		throw new UnsupportedOperationException();
	}
	
	@NotNull
	@Override
	@Deprecated
	@Contract("_, _, _ -> fail")
	public DirectBuffer createNoFree(long address, long capacity, @NotNull Object[] parents) {
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
