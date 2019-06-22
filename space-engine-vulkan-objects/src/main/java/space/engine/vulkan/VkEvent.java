package space.engine.vulkan;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VkEventCreateInfo;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack.AllocatorFrame;
import space.engine.buffer.pointer.PointerBufferPointer;
import space.engine.freeableStorage.Freeable;
import space.engine.freeableStorage.Freeable.FreeableWrapper;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.sync.barrier.Barrier;

import java.util.function.BiFunction;

import static org.lwjgl.vulkan.VK10.*;
import static space.engine.freeableStorage.Freeable.addIfNotContained;
import static space.engine.vulkan.VkException.assertVk;

public class VkEvent implements FreeableWrapper {
	
	//alloc
	public static @NotNull VkEvent alloc(VkEventCreateInfo info, @NotNull VkDevice device, @NotNull Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			PointerBufferPointer ptr = PointerBufferPointer.malloc(frame);
			assertVk(nvkCreateEvent(device, info.address(), 0, ptr.address()));
			return create(ptr.getPointer(), device, parents);
		}
	}
	
	//create
	public static @NotNull VkEvent create(long address, @NotNull VkDevice device, @NotNull Object[] parents) {
		return new VkEvent(address, device, Storage::new, parents);
	}
	
	public static @NotNull VkEvent wrap(long address, @NotNull VkDevice device, @NotNull Object[] parents) {
		return new VkEvent(address, device, Freeable::createDummy, parents);
	}
	
	//const
	public VkEvent(long address, @NotNull VkDevice device, @NotNull BiFunction<VkEvent, Object[], Freeable> storageCreator, @NotNull Object[] parents) {
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
		
		private final @NotNull VkDevice device;
		private final long address;
		
		public Storage(@NotNull VkEvent o, @NotNull Object[] parents) {
			super(o, parents);
			this.device = o.device;
			this.address = o.address;
		}
		
		@Override
		protected @NotNull Barrier handleFree() {
			vkDestroyEvent(device, address, null);
			return Barrier.ALWAYS_TRIGGERED_BARRIER;
		}
	}
}
