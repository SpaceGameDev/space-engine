package space.engine.vulkan;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.vulkan.VkDescriptorSetLayoutBinding;
import org.lwjgl.vulkan.VkDescriptorSetLayoutCreateInfo;
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
import static space.engine.lwjgl.LwjglStructAllocator.mallocStruct;
import static space.engine.vulkan.VkException.assertVk;

public class VkDescriptorSetLayout implements FreeableWrapper {
	
	//alloc
	public static @NotNull VkDescriptorSetLayout alloc(@NotNull VkDevice device, int flags, @Nullable VkDescriptorSetLayoutBinding.Buffer bindings, @NotNull Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			return alloc(mallocStruct(frame, VkDescriptorSetLayoutCreateInfo::create, VkDescriptorSetLayoutCreateInfo.SIZEOF).set(
					VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO,
					0,
					flags,
					bindings
			), device, parents);
		}
	}
	
	public static @NotNull VkDescriptorSetLayout alloc(VkDescriptorSetLayoutCreateInfo info, @NotNull VkDevice device, @NotNull Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			PointerBufferPointer ptr = PointerBufferPointer.malloc(frame);
			assertVk(nvkCreateDescriptorSetLayout(device, info.address(), 0, ptr.address()));
			return create(ptr.getPointer(), device, parents);
		}
	}
	
	//create
	public static @NotNull VkDescriptorSetLayout create(long address, @NotNull VkDevice device, @NotNull Object[] parents) {
		return new VkDescriptorSetLayout(address, device, Storage::new, parents);
	}
	
	public static @NotNull VkDescriptorSetLayout wrap(long address, @NotNull VkDevice device, @NotNull Object[] parents) {
		return new VkDescriptorSetLayout(address, device, Freeable::createDummy, parents);
	}
	
	//const
	public VkDescriptorSetLayout(long address, @NotNull VkDevice device, @NotNull BiFunction<VkDescriptorSetLayout, Object[], Freeable> storageCreator, @NotNull Object[] parents) {
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
		
		public Storage(@NotNull VkDescriptorSetLayout o, @NotNull Object[] parents) {
			super(o, parents);
			this.device = o.device;
			this.address = o.address;
		}
		
		@Override
		protected @NotNull Barrier handleFree() {
			vkDestroyDescriptorSetLayout(device, address, null);
			return Barrier.ALWAYS_TRIGGERED_BARRIER;
		}
	}
}
