package space.engine.vulkan.managed.device;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack.AllocatorFrame;
import space.engine.buffer.array.ArrayBufferLong;
import space.engine.freeableStorage.Freeable;
import space.engine.freeableStorage.Freeable.FreeableWrapper;
import space.engine.simpleQueue.SimpleQueue;
import space.engine.simpleQueue.pool.AbstractSimpleMessagePool;
import space.engine.sync.barrier.Barrier;
import space.engine.sync.barrier.BarrierImpl;
import space.engine.vulkan.VkFence;

import java.util.ArrayList;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lwjgl.vulkan.VK10.*;
import static space.engine.vulkan.VkException.assertVk;

public class EventAwaiter implements FreeableWrapper {
	
	public static final long TIMEOUT_NANOS = 20_000_000L;
	
	public static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory() {
		private AtomicInteger COUNTER = new AtomicInteger();
		
		@Override
		public Thread newThread(@NotNull Runnable r) {
			return new Thread(r, "Vulkan-EventWaiter-" + COUNTER.getAndIncrement());
		}
	};
	
	public EventAwaiter(@NotNull ManagedDevice device, @NotNull SimpleQueue<Entry> queue, Object[] parents) {
		this(device, queue, DEFAULT_THREAD_FACTORY, parents);
	}
	
	public EventAwaiter(@NotNull ManagedDevice device, @NotNull SimpleQueue<Entry> queue, ThreadFactory threadFactory, Object[] parents) {
		this.device = device;
		this.storage = Freeable.createDummy(this, parents);
		
		//pool
		this.pool = new AbstractSimpleMessagePool<>(1, queue, threadFactory) {
			
			private ArrayList<Entry> accumulator = new ArrayList<>();
			
			@Override
			protected void handle(Entry entry) {
				accumulator.add(entry);
			}
			
			@Override
			protected void park() {
				if (accumulator.size() == 0) {
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException ignored) {
						
						}
					}
				} else {
					try (AllocatorFrame frame = Allocator.frame()) {
						ArrayBufferLong fenceBuffer = ArrayBufferLong.alloc(frame, accumulator.stream().mapToLong(e -> e.fence.address()).toArray());
						int result = assertVk(nvkWaitForFences(device, (int) fenceBuffer.length(), fenceBuffer.address(), VK_FALSE, TIMEOUT_NANOS));
						if (result != VK_TIMEOUT) {
							accumulator.removeIf(entry -> {
								if (assertVk(vkGetFenceStatus(device, entry.fence.address())) == VK_SUCCESS) {
									entry.barrier.triggerNow();
									return true;
								} else {
									return false;
								}
							});
						}
					}
				}
			}
			
			@Override
			protected synchronized void unparkThreads() {
				this.notifyAll();
			}
		};
		this.pool.createStopFreeable(new Object[] {this});
	}
	
	//parents
	private final @NotNull ManagedDevice device;
	
	public @NotNull ManagedDevice device() {
		return device;
	}
	
	//storage
	private final @NotNull Freeable storage;
	
	@Override
	public @NotNull Freeable getStorage() {
		return storage;
	}
	
	//pool
	private final AbstractSimpleMessagePool<Entry> pool;
	
	public Barrier add(@NotNull VkFence fence, @Nullable Object container) {
		Entry entry = new Entry(fence, container);
		pool.add(entry);
		return entry.barrier;
	}
	
	public Barrier add(@NotNull VkFence fence) {
		Entry entry = new Entry(fence);
		pool.add(entry);
		return entry.barrier;
	}
	
	//entry
	public static class Entry {
		
		private final @NotNull VkFence fence;
		private final @NotNull BarrierImpl barrier = new BarrierImpl();
		@SuppressWarnings({"FieldCanBeLocal", "unused"})
		private final @Nullable Object container;
		
		private Entry(@NotNull VkFence fence) {
			this(fence, null);
		}
		
		private Entry(@NotNull VkFence fence, @Nullable Object container) {
			this.fence = fence;
			this.container = container;
		}
	}
}
