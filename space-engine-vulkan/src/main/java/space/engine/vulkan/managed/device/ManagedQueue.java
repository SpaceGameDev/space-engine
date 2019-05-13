package space.engine.vulkan.managed.device;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.vulkan.VkSubmitInfo;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack.AllocatorFrame;
import space.engine.buffer.pointer.PointerBufferPointer;
import space.engine.freeableStorage.Freeable;
import space.engine.vulkan.VkDevice;
import space.engine.vulkan.VkFence;
import space.engine.vulkan.VkQueue;
import space.engine.vulkan.VkQueueFamilyProperties;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.lwjgl.vulkan.VK10.*;

public class ManagedQueue extends VkQueue {
	
	//alloc
	public static @NotNull ManagedQueue alloc(@NotNull VkDevice device, @NotNull VkQueueFamilyProperties family, int queueIndex, @NotNull Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			PointerBufferPointer ptr = PointerBufferPointer.malloc(frame);
			nvkGetDeviceQueue(device, family.index(), queueIndex, ptr.address());
			return new ManagedQueue(ptr.getPointer(), device, family, parents);
		}
	}
	
	//object
	protected ManagedQueue(long address, @NotNull VkDevice device, @NotNull VkQueueFamilyProperties queueFamily, @NotNull Object[] parents) {
		super(address, device, queueFamily, Freeable::createDummy, parents);
	}
	
	//submit
	public void submit(VkSubmitInfo info, @Nullable VkFence fence) {
		submit(new SubmitCmd(info, fence));
	}
	
	public void submit(QueueCmd cmd) {
		cmd.run(this);
	}
	
	public void submit(QueueCmd... cmds) {
		submit(Arrays.stream(cmds));
	}
	
	public void submit(Collection<QueueCmd> cmds) {
		submit(cmds.stream());
	}
	
	public void submit(Stream<QueueCmd> cmds) {
		cmds.forEach(this::submit);
	}
	
	//QueueCmd
	@FunctionalInterface
	public interface QueueCmd {
		
		void run(ManagedQueue queue);
	}
	
	public static class SubmitCmd implements QueueCmd {
		
		public final @NotNull VkSubmitInfo info;
		public final @Nullable VkFence fence;
		
		public SubmitCmd(@NotNull VkSubmitInfo info, @Nullable VkFence fence) {
			this.info = info;
			this.fence = fence;
		}
		
		@Override
		public void run(ManagedQueue queue) {
			vkQueueSubmit(queue, info, fence != null ? fence.address() : 0);
		}
	}
}
