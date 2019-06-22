package space.engine.vulkan.vma;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.util.vma.VmaAllocatorCreateInfo;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack.AllocatorFrame;
import space.engine.buffer.pointer.PointerBufferPointer;
import space.engine.freeableStorage.Freeable;
import space.engine.freeableStorage.Freeable.FreeableWrapper;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.sync.barrier.Barrier;
import space.engine.vulkan.VkDevice;
import space.engine.vulkan.VkInstance;

import java.util.function.BiFunction;

import static org.lwjgl.util.vma.Vma.*;
import static space.engine.freeableStorage.Freeable.addIfNotContained;
import static space.engine.vulkan.VkException.assertVk;

public class VmaAllocator implements FreeableWrapper {
	
	//alloc
	public static @NotNull VmaAllocator alloc(VmaAllocatorCreateInfo info, @NotNull VkDevice device, @NotNull Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			PointerBufferPointer ptr = PointerBufferPointer.malloc(frame);
			assertVk(nvmaCreateAllocator(info.address(), ptr.address()));
			return create(ptr.getPointer(), device, parents);
		}
	}
	
	//create
	public static @NotNull VmaAllocator create(long address, @NotNull VkDevice device, @NotNull Object[] parents) {
		return new VmaAllocator(address, device, Storage::new, parents);
	}
	
	public static @NotNull VmaAllocator wrap(long address, @NotNull VkDevice device, @NotNull Object[] parents) {
		return new VmaAllocator(address, device, Freeable::createDummy, parents);
	}
	
	//const
	public VmaAllocator(long address, @NotNull VkDevice device, @NotNull BiFunction<VmaAllocator, Object[], Freeable> storageCreator, @NotNull Object[] parents) {
		this.address = address;
		this.device = device;
		this.storage = storageCreator.apply(this, addIfNotContained(parents, device));
	}
	
	//parents
	private final @NotNull VkDevice device;
	
	public VkDevice device() {
		return device;
	}
	
	public VkInstance instance() {
		return device.instance();
	}
	
	//address
	private final long address;
	
	public long address() {
		return address;
	}
	
	//storage
	private final @NotNull Freeable storage;
	
	@Override
	public @NotNull Freeable getStorage() {
		return storage;
	}
	
	public static class Storage extends FreeableStorage {
		
		private final long address;
		
		public Storage(@NotNull VmaAllocator o, @NotNull Object[] parents) {
			super(o, parents);
			this.address = o.address;
		}
		
		@Override
		protected @NotNull Barrier handleFree() {
			vmaDestroyAllocator(address);
			return Barrier.ALWAYS_TRIGGERED_BARRIER;
		}
	}
}
