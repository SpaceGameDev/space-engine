package space.engine.vulkan.managed.device;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.Pointer;
import org.lwjgl.vulkan.VkFenceCreateInfo;
import org.lwjgl.vulkan.VkSubmitInfo;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack.AllocatorFrame;
import space.engine.buffer.pointer.PointerBufferPointer;
import space.engine.freeableStorage.Freeable;
import space.engine.simpleQueue.ConcurrentLinkedSimpleQueue;
import space.engine.simpleQueue.pool.SimpleThreadPool;
import space.engine.sync.TaskCreator;
import space.engine.sync.barrier.Barrier;
import space.engine.sync.future.Future;
import space.engine.vulkan.VkFence;
import space.engine.vulkan.VkQueue;
import space.engine.vulkan.VkQueueFamilyProperties;

import java.util.concurrent.atomic.AtomicInteger;

import static org.lwjgl.vulkan.VK10.*;
import static space.engine.Empties.EMPTY_OBJECT_ARRAY;
import static space.engine.lwjgl.LwjglStructAllocator.mallocStruct;
import static space.engine.sync.Tasks.future;

public class ManagedQueue extends VkQueue {
	
	private static final AtomicInteger MANAGED_QUEUE_THREAD_COUNTER = new AtomicInteger();
	private static final VkFenceCreateInfo VK_FENCE_CREATE_INFO = mallocStruct(Allocator.heap(), VkFenceCreateInfo::create, VkFenceCreateInfo.SIZEOF, new Object[] {ROOT_LIST}).set(
			VK_STRUCTURE_TYPE_FENCE_CREATE_INFO,
			0,
			0
	);
	
	//alloc
	public static @NotNull ManagedQueue alloc(@NotNull ManagedDevice device, @NotNull VkQueueFamilyProperties family, int queueIndex, @NotNull Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			PointerBufferPointer ptr = PointerBufferPointer.malloc(frame);
			nvkGetDeviceQueue(device, family.index(), queueIndex, ptr.address());
			return new ManagedQueue(ptr.getPointer(), device, family, parents);
		}
	}
	
	//object
	protected ManagedQueue(long address, @NotNull ManagedDevice device, @NotNull VkQueueFamilyProperties queueFamily, @NotNull Object[] parents) {
		super(address, device, queueFamily);
		this.device = device;
		init(Freeable::createDummy, parents);
		
		//submit
		this.pool = new SimpleThreadPool(
				1,
				new ConcurrentLinkedSimpleQueue<>(),
				r -> new Thread(r, "ManagedQueue-" + MANAGED_QUEUE_THREAD_COUNTER.getAndIncrement())
		);
		this.pool.createStopFreeable(new Object[] {this});
	}
	
	//parents
	private final ManagedDevice device;
	
	@Override
	public ManagedDevice device() {
		return device;
	}
	
	//submit
	private final SimpleThreadPool pool;
	
	/**
	 * Executes vkQueueSubmit() on a {@link VkSubmitInfo}
	 *
	 * @return a {@link Future}, triggered when cmd is submitted, containing a {@link Barrier}, triggered when execution of cmd finished
	 */
	public TaskCreator<? extends Future<Barrier>> submit(Entry cmd) {
		return future(pool, () -> cmd.run(ManagedQueue.this));
	}
	
	/**
	 * Executes vkQueueSubmit() on a {@link VkSubmitInfo}
	 *
	 * @return a {@link Future}, triggered when cmd is submitted, containing a {@link Barrier}, triggered when execution of cmd finished
	 */
	public TaskCreator<? extends Future<Barrier>> submit(VkSubmitInfo cmd) {
		return submit(new SubmitQueueEntry(cmd));
	}
	
	/**
	 * Executes vkQueueSubmit() on a {@link VkSubmitInfo}
	 *
	 * @return a {@link Future}, triggered when cmd is submitted, containing a {@link Barrier}, triggered when execution of cmd finished
	 */
	public TaskCreator<? extends Future<Barrier>> submit(VkSubmitInfo.Buffer cmd) {
		return submit(new SubmitQueueEntry(cmd));
	}
	
	//Entry
	@FunctionalInterface
	public interface Entry {
		
		Barrier run(ManagedQueue queue);
	}
	
	public static class SubmitQueueEntry implements Entry {
		
		private final int infoCount;
		private final Pointer infoAddress;
		
		private SubmitQueueEntry(VkSubmitInfo info) {
			this(1, info);
		}
		
		private SubmitQueueEntry(VkSubmitInfo.Buffer infoBuffer) {
			this(infoBuffer.capacity(), infoBuffer);
		}
		
		private SubmitQueueEntry(int infoCount, Pointer infoAddress) {
			this.infoCount = infoCount;
			this.infoAddress = infoAddress;
		}
		
		@Override
		public Barrier run(ManagedQueue queue) {
			VkFence fence = VkFence.alloc(VK_FENCE_CREATE_INFO, queue.device(), EMPTY_OBJECT_ARRAY);
			nvkQueueSubmit(queue, infoCount, infoAddress.address(), fence.address());
			Barrier doneBarrier = queue.device().eventAwaiter().add(fence);
			doneBarrier.addHook(fence::free);
			return doneBarrier;
		}
	}
}
