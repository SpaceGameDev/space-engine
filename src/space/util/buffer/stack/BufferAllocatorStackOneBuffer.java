package space.util.buffer.stack;

import space.util.buffer.alloc.IBufferAllocator;
import space.util.buffer.buffers.IBuffer;
import space.util.buffer.buffers.SimpleBuffer;
import space.util.releasable.IReleasable;
import space.util.releasable.IReleasableWrapper;
import spaceOld.util.math.format.old.HexFormat;
import spaceOld.util.stack.PointerList;
import spaceOld.util.string.builder.wrapper.StringBuilderWrapperCommaPolicy;

import java.util.ArrayList;
import java.util.function.Consumer;

import static java.lang.Math.max;
import static spaceOld.engine.base.sideSpecific.ClientSide.client;
import static spaceOld.util.UnsafeInstance.unsafe;
import static spaceOld.util.math.MathUtils.floor;

public class BufferAllocatorStackOneBuffer implements IBufferAllocatorStack, IReleasableWrapper {
	
	public static final int DEFAULTCAPACITY = 64;
	public static final float DEFAULTEXPANDER = 2;
	public static final int POINTERCAPACITY = 8;
	public static final float POINTEREXPANDER = 1.2f;
	public static final long DUMPCAP = 512;
	
	public IBufferAllocator alloc;
	public SimpleBuffer storage;
	public float expander;
	
	public PointerList pointerList;
	public long pointer = 0;
	
	private ArrayList<SimpleBuffer> oldBufferList = new ArrayList<>(0);
	
	public BufferAllocatorStackOneBuffer() {
		this(null);
	}
	
	public BufferAllocatorStackOneBuffer(IBufferAllocator alloc) {
		this(alloc, DEFAULTCAPACITY, DEFAULTEXPANDER, new PointerList(POINTERCAPACITY, POINTEREXPANDER));
	}
	
	public BufferAllocatorStackOneBuffer(IBufferAllocator alloc, long startCapacity) {
		this(alloc, startCapacity, DEFAULTEXPANDER, new PointerList(POINTERCAPACITY, POINTEREXPANDER));
	}
	
	public BufferAllocatorStackOneBuffer(IBufferAllocator alloc, long startCapacity, float expander, PointerList pointerList) {
		this.alloc = alloc;
		this.expander = expander;
		this.pointerList = pointerList;
		makeInternalBuffer(startCapacity);
	}
	
	@Override
	public long pushPointer() {
		return pointer;
	}
	
	@Override
	public void push() {
		pointerList.push(pushPointer());
	}
	
	@Override
	public void popPointer(long pointer) {
		this.pointer = pointer;
		if (pointer == 0)
			clearOldBuffers0();
	}
	
	@Override
	public void pop() {
		popPointer(pointerList.pop());
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
	
	public void ensureCapacity(long capacity) {
		if (storage.capacity < capacity) {
			expandTo(capacity);
		}
	}
	
	public void expandTo(long capacity) {
		makeInternalBuffer(max((long) floor((double) storage.capacity * expander), capacity));
	}
	
	public void makeInternalBuffer(long capacity) {
		if (storage != null)
			oldBufferList.add(storage);
		storage = new SimpleBuffer(capacity);
		
		//in case I'm testing stuff
		try {
			client.getSide().releaseTracker.addWrapper(this);
		} catch (IllegalStateException e) {
			
		}
	}
	
	public void clearOldBuffers() {
		if (pointer != 0)
			throw new IllegalStateException();
		clearOldBuffers0();
	}
	
	private void clearOldBuffers0() {
		oldBufferList.clear();
	}
	
	public long alloc(long capacity) {
		long newPointer = pointer + capacity;
		ensureCapacity(newPointer);
		
		long oldPointer = pointer;
		pointer = newPointer;
		
		return storage.address + oldPointer;
	}
	
	@Override
	public IBuffer malloc(long capacity) {
		return alloc.alloc(alloc(capacity), capacity);
	}
	
	@Override
	public IBuffer alloc(long address, long capacity) {
		return alloc.alloc(address, capacity);
	}
	
	@Override
	public IReleasable getReleasable() {
		return storage;
	}
	
	public String dump() {
		if (storage.capacity > DUMPCAP)
			return "DUMP CAP reached!";
		StringBuilder b = new StringBuilder((int) storage.capacity * 3);
		StringBuilderWrapperCommaPolicy bw = new StringBuilderWrapperCommaPolicy(b);
		for (long i = 0; i < storage.capacity; i++) {
			HexFormat.toHex(bw, unsafe.getByte(storage.address + i));
			b.append(' ');
		}
		b.setLength(b.length() - 1);
		return b.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{buffer address: ");
		builder.append(storage.address);
		builder.append(", buffer capacity: ");
		builder.append(storage.capacity);
		builder.append(", expander: ");
		builder.append(expander);
		builder.append(", pointer: ");
		builder.append(pointer);
		builder.append(", oldBuffers: ");
		builder.append(oldBufferList.size());
		builder.append(", buffer dump: ");
		builder.append(dump());
		builder.append("}");
		return builder.toString();
	}
}
