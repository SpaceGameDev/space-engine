package space.engine.vulkan;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.vulkan.VkCommandBufferBeginInfo;
import org.lwjgl.vulkan.VkCommandBufferInheritanceInfo;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack.AllocatorFrame;
import space.engine.freeableStorage.Freeable;
import space.engine.freeableStorage.Freeable.FreeableWrapper;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.sync.barrier.Barrier;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import static org.lwjgl.vulkan.VK10.*;
import static space.engine.freeableStorage.Freeable.addIfNotContained;
import static space.engine.lwjgl.LwjglStructAllocator.mallocStruct;
import static space.engine.vulkan.VkException.assertVk;

public class VkCommandBuffer extends org.lwjgl.vulkan.VkCommandBuffer implements FreeableWrapper {
	
	//create
	public static @NotNull VkCommandBuffer create(long address, @NotNull VkDevice device, @NotNull VkCommandPool commandPool, @NotNull Object[] parents) {
		return new VkCommandBuffer(address, device, commandPool, Storage::new, parents);
	}
	
	public static @NotNull VkCommandBuffer wrap(long address, @NotNull VkDevice device, @NotNull VkCommandPool commandPool, @NotNull Object[] parents) {
		return new VkCommandBuffer(address, device, commandPool, Freeable::createDummy, parents);
	}
	
	//const
	public VkCommandBuffer(long address, @NotNull VkDevice device, @NotNull VkCommandPool commandPool, @NotNull BiFunction<VkCommandBuffer, Object[], Freeable> storageCreator, @NotNull Object[] parents) {
		super(address, device);
		this.address = address;
		this.device = device;
		this.commandPool = commandPool;
		this.storage = storageCreator.apply(this, addIfNotContained(parents, device, commandPool));
	}
	
	//parents
	private final @NotNull VkDevice device;
	
	public VkDevice device() {
		return device;
	}
	
	public VkInstance instance() {
		return device.instance();
	}
	
	private final @NotNull VkCommandPool commandPool;
	
	public VkCommandPool commandPool() {
		return commandPool;
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
		private final @NotNull VkCommandPool commandPool;
		private final long address;
		
		public Storage(@NotNull VkCommandBuffer o, @NotNull Object[] parents) {
			super(o, parents);
			this.device = o.device;
			this.commandPool = o.commandPool;
			this.address = o.address;
		}
		
		@Override
		protected @NotNull Barrier handleFree() {
			try (AllocatorFrame frame = Allocator.frame()) {
				nvkFreeCommandBuffers(device, commandPool.address(), 1, address);
			}
			return Barrier.ALWAYS_TRIGGERED_BARRIER;
		}
	}
	
	//recording
	@SuppressWarnings({"FieldCanBeLocal", "unused"})
	private @Nullable Object recordingDependencies;
	
	//begin
	public void begin(int flags) {
		begin(flags, null);
	}
	
	public void begin(int flags, @Nullable VkCommandBufferInheritanceInfo inheritanceInfo) {
		try (AllocatorFrame frame = Allocator.frame()) {
			begin(mallocStruct(frame, VkCommandBufferBeginInfo::create, VkCommandBufferBeginInfo.SIZEOF).set(
					VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO,
					0,
					flags,
					inheritanceInfo
			));
		}
	}
	
	public void begin(VkCommandBufferBeginInfo info) {
		assertVk(vkBeginCommandBuffer(this, info));
	}
	
	//end
	public void end() {
		end(null);
	}
	
	public void end(@Nullable Object recordingDependencies) {
		this.recordingDependencies = recordingDependencies;
		assertVk(vkEndCommandBuffer(this));
	}
	
	//record
	public void record(int flags, Runnable function) {
		record(flags, null, function);
	}
	
	public void record(int flags, Supplier<Object> function) {
		record(flags, null, function);
	}
	
	public void record(int flags, @Nullable VkCommandBufferInheritanceInfo inheritanceInfo, Runnable function) {
		record(flags, inheritanceInfo, () -> {
			function.run();
			return null;
		});
	}
	
	public void record(int flags, @Nullable VkCommandBufferInheritanceInfo inheritanceInfo, Supplier<Object> function) {
		begin(flags, inheritanceInfo);
		Object recordingDependencies = function.get();
		end(recordingDependencies);
	}
	
	//reset
	public void reset(int flags) {
		assertVk(vkResetCommandBuffer(this, flags));
	}
}
