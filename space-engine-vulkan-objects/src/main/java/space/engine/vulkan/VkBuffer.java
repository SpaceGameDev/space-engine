package space.engine.vulkan;

import org.jetbrains.annotations.NotNull;
import space.engine.buffer.Buffer;
import space.engine.freeableStorage.Freeable;
import space.engine.freeableStorage.Freeable.FreeableWrapper;
import space.engine.sync.barrier.Barrier;

import java.util.function.BiFunction;

import static space.engine.freeableStorage.Freeable.addIfNotContained;

public interface VkBuffer extends FreeableWrapper {
	
	//wrap
	static @NotNull VkBuffer wrap(long address, long sizeOf, @NotNull VkDevice device, @NotNull Object[] parents) {
		return new VkBuffer.Default(address, sizeOf, device, Freeable::createDummy, parents);
	}
	
	//parents
	VkDevice device();
	
	//address
	long address();
	
	long sizeOf();
	
	//uploadData
	default Barrier uploadData(Buffer src) {
		return uploadData(src, 0, 0, src.sizeOf());
	}
	
	Barrier uploadData(Buffer src, long srcOffset, long dstOffset, long length);
	
	class Default implements VkBuffer {
		
		//const
		public Default(long address, long sizeOf, @NotNull VkDevice device, @NotNull BiFunction<VkBuffer, Object[], Freeable> storageCreator, @NotNull Object[] parents) {
			this.device = device;
			this.address = address;
			this.sizeOf = sizeOf;
			this.storage = storageCreator.apply(this, addIfNotContained(parents, device));
		}
		
		//parents
		private final VkDevice device;
		
		@Override
		public VkDevice device() {
			return device;
		}
		
		//address
		private final long address;
		private final long sizeOf;
		
		@Override
		public long address() {
			return address;
		}
		
		@Override
		public long sizeOf() {
			return sizeOf;
		}
		
		//storage
		private final @NotNull Freeable storage;
		
		@Override
		public @NotNull Freeable getStorage() {
			return storage;
		}
		
		//uploadData
		@Override
		public Barrier uploadData(Buffer src, long srcOffset, long dstOffset, long length) {
			//we cannot resolve the memory (and offset) of a random buffer or image
			throw new UnsupportedOperationException();
		}
	}
}
