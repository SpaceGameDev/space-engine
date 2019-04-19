package space.engine.buffer;

import space.engine.unsafe.UnsafeInstance;
import sun.misc.Unsafe;

public abstract class Allocator {
	
	protected static final Unsafe UNSAFE = UnsafeInstance.getUnsafe();
	
	//default allocators
	private static final Allocator ALLOCATOR_HEAP = new Allocator() {
		@Override
		public long malloc(long sizeOf) {
			return UNSAFE.allocateMemory(sizeOf);
		}
		
		@Override
		public long calloc(long sizeOf) {
			//calloc sadly not supported by Unsafe
			long address = UNSAFE.allocateMemory(sizeOf);
			UNSAFE.setMemory(address, sizeOf, (byte) 0);
			return address;
		}
		
		@Override
		public void free(long address) {
			UNSAFE.freeMemory(address);
		}
	};
	
	public static Allocator allocatorHeap() {
		return ALLOCATOR_HEAP;
	}
	
	private static final Allocator ALLOCATOR_NOOP = new Allocator() {
		@Override
		public long malloc(long sizeOf) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public long calloc(long sizeOf) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void free(long address) {
		
		}
	};
	
	public static Allocator allocatorNoop() {
		return ALLOCATOR_NOOP;
	}
	
	//object
	public abstract long malloc(long sizeOf);
	
	public abstract long calloc(long sizeOf);
	
	public abstract void free(long address);
}
