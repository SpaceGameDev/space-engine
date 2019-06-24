package space.engine.vulkan;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VkCommandBufferAllocateInfo;
import org.lwjgl.vulkan.VkCommandPoolCreateInfo;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack.AllocatorFrame;
import space.engine.buffer.array.ArrayBufferLong;
import space.engine.buffer.array.ArrayBufferPointer;
import space.engine.buffer.pointer.PointerBufferLong;
import space.engine.buffer.pointer.PointerBufferPointer;
import space.engine.freeableStorage.Freeable;
import space.engine.freeableStorage.Freeable.FreeableWrapper;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.sync.barrier.Barrier;
import space.engine.vulkan.VkCommandBuffer.Default;
import space.engine.vulkan.VkCommandBuffer.DestroyStorage;

import java.util.Arrays;
import java.util.function.BiFunction;

import static org.lwjgl.vulkan.VK10.*;
import static space.engine.freeableStorage.Freeable.addIfNotContained;
import static space.engine.lwjgl.LwjglStructAllocator.mallocStruct;
import static space.engine.vulkan.VkException.assertVk;

public class VkCommandPool implements FreeableWrapper {
	
	//alloc
	public static @NotNull VkCommandPool alloc(int flags, VkQueueFamilyProperties queueFamily, @NotNull VkDevice device, @NotNull Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			return alloc(mallocStruct(frame, VkCommandPoolCreateInfo::create, VkCommandPoolCreateInfo.SIZEOF).set(
					VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO,
					0,
					flags,
					queueFamily.index()
			), device, parents);
		}
	}
	
	public static @NotNull VkCommandPool alloc(VkCommandPoolCreateInfo info, @NotNull VkDevice device, @NotNull Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			PointerBufferPointer ptr = PointerBufferPointer.malloc(frame);
			assertVk(nvkCreateCommandPool(device, info.address(), 0, ptr.address()));
			return create(ptr.getPointer(), device, (info.flags() & VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT) != 0, parents);
		}
	}
	
	//create
	public static @NotNull VkCommandPool create(long address, @NotNull VkDevice device, boolean allowReset, @NotNull Object[] parents) {
		return new VkCommandPool(address, device, allowReset, Storage::new, parents);
	}
	
	public static @NotNull VkCommandPool wrap(long address, @NotNull VkDevice device, boolean allowReset, @NotNull Object[] parents) {
		return new VkCommandPool(address, device, allowReset, Freeable::createDummy, parents);
	}
	
	//const
	public VkCommandPool(long address, @NotNull VkDevice device, boolean allowReset, @NotNull BiFunction<VkCommandPool, Object[], Freeable> storageCreator, @NotNull Object[] parents) {
		this.address = address;
		this.device = device;
		this.allowReset = allowReset;
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
		
		public Storage(@NotNull VkCommandPool o, @NotNull Object[] parents) {
			super(o, parents);
			this.device = o.device;
			this.address = o.address;
		}
		
		@Override
		protected @NotNull Barrier handleFree() {
			vkDestroyCommandPool(device, address, null);
			return Barrier.ALWAYS_TRIGGERED_BARRIER;
		}
	}
	
	//allocCommandBuffer
	private final boolean allowReset;
	
	public boolean allowReset() {
		return allowReset;
	}
	
	public synchronized VkCommandBuffer allocCommandBuffer(int level, Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			VkCommandBufferAllocateInfo info = mallocStruct(frame, VkCommandBufferAllocateInfo::create, VkCommandBufferAllocateInfo.SIZEOF).set(
					VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO,
					0,
					this.address(),
					level,
					1
			);
			PointerBufferPointer ptr = PointerBufferPointer.malloc(frame);
			synchronized (this) {
				assertVk(nvkAllocateCommandBuffers(device(), info.address(), ptr.address()));
			}
			return new Default(ptr.getPointer(), this, allowReset ? DestroyStorage::new : Freeable::createDummy, parents);
		}
	}
	
	public synchronized VkCommandBuffer[] allocCommandBuffers(int level, int count, Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			VkCommandBuffer[] ret = new VkCommandBuffer[count];
			
			VkCommandBufferAllocateInfo info = mallocStruct(frame, VkCommandBufferAllocateInfo::create, VkCommandBufferAllocateInfo.SIZEOF).set(
					VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO,
					0,
					this.address(),
					level,
					count
			);
			ArrayBufferPointer ptr = ArrayBufferPointer.malloc(frame, count);
			synchronized (this) {
				assertVk(nvkAllocateCommandBuffers(device(), info.address(), ptr.address()));
			}
			for (int i = 0; i < count; i++)
				ret[i] = new Default(ptr.getPointer(i), this, allowReset ? DestroyStorage::new : Freeable::createDummy, parents);
			return ret;
		}
	}
	
	public void releaseCommandBuffer(VkCommandBuffer commandBuffer) {
		releaseCommandBuffer(commandBuffer.address());
	}
	
	public void releaseCommandBuffer(long commandBuffer) {
		try (AllocatorFrame frame = Allocator.frame()) {
			PointerBufferLong ptr = PointerBufferLong.alloc(frame, commandBuffer);
			synchronized (this) {
				nvkFreeCommandBuffers(device, this.address(), 1, ptr.address());
				assertVk();
			}
		}
	}
	
	public void releaseCommandBuffers(VkCommandBuffer[] commandBuffers) {
		releaseCommandBuffers(Arrays.stream(commandBuffers).mapToLong(VkCommandBuffer::address).toArray());
	}
	
	public void releaseCommandBuffers(long[] commandBuffers) {
		try (AllocatorFrame frame = Allocator.frame()) {
			ArrayBufferLong ptrs = ArrayBufferLong.alloc(frame, commandBuffers);
			synchronized (this) {
				nvkFreeCommandBuffers(device, this.address(), commandBuffers.length, ptrs.address());
				assertVk();
			}
		}
	}
}
