package space.engine.vulkan.managed.surface;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.vulkan.VkExtent2D;
import org.lwjgl.vulkan.VkPresentInfoKHR;
import org.lwjgl.vulkan.VkSwapchainCreateInfoKHR;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack.AllocatorFrame;
import space.engine.buffer.pointer.PointerBufferInt;
import space.engine.buffer.pointer.PointerBufferPointer;
import space.engine.sync.TaskCreator;
import space.engine.sync.barrier.Barrier;
import space.engine.sync.future.Future;
import space.engine.vulkan.VkException;
import space.engine.vulkan.VkQueueFamilyProperties;
import space.engine.vulkan.exception.UnsupportedConfigurationException;
import space.engine.vulkan.managed.device.ManagedDevice;
import space.engine.vulkan.managed.device.ManagedQueue;
import space.engine.vulkan.managed.device.ManagedQueue.Entry;
import space.engine.vulkan.surface.VkSurface;
import space.engine.vulkan.surface.VkSwapchain;
import space.engine.window.Window;

import static org.lwjgl.vulkan.KHRSurface.*;
import static org.lwjgl.vulkan.KHRSwapchain.*;
import static org.lwjgl.vulkan.VK10.*;
import static space.engine.lwjgl.LwjglStructAllocator.mallocStruct;
import static space.engine.sync.barrier.Barrier.ALWAYS_TRIGGERED_BARRIER;
import static space.engine.vulkan.VkException.assertVk;
import static space.engine.vulkan.managed.device.ManagedDevice.*;

public class ManagedSwapchain<WINDOW extends Window> extends VkSwapchain<WINDOW> {
	
	//private static
	private static final int[] BEST_SWAPCHAIN_QUEUE_TYPES = new int[] {
			QUEUE_TYPE_TRANSFER,
			QUEUE_TYPE_GRAPHICS,
			QUEUE_TYPE_COMPUTE
	};
	
	private static final int[][] BEST_IMAGE_FORMATS = new int[][] {
			{VK_FORMAT_R8G8B8_UNORM, VK_COLOR_SPACE_SRGB_NONLINEAR_KHR},
			{VK_FORMAT_B8G8R8_UNORM, VK_COLOR_SPACE_SRGB_NONLINEAR_KHR},
			{VK_FORMAT_R8G8B8A8_UNORM, VK_COLOR_SPACE_SRGB_NONLINEAR_KHR},
			{VK_FORMAT_B8G8R8A8_UNORM, VK_COLOR_SPACE_SRGB_NONLINEAR_KHR},
	};
	
	private static final int[] BEST_PRESENT_MODES = new int[] {
			VK_PRESENT_MODE_MAILBOX_KHR,
			VK_PRESENT_MODE_IMMEDIATE_KHR,
			VK_PRESENT_MODE_FIFO_KHR
	};
	
	private static int bestSwapchainQueue(@NotNull ManagedDevice device, @NotNull VkSurface<?> surface) throws UnsupportedConfigurationException {
		try (AllocatorFrame frame = Allocator.frame()) {
			PointerBufferInt ptr = PointerBufferInt.malloc(frame);
			for (int queueType : BEST_SWAPCHAIN_QUEUE_TYPES) {
				VkQueueFamilyProperties queueFamily = device.getQueueFamily(queueType);
				VkException.assertVk(nvkGetPhysicalDeviceSurfaceSupportKHR(device.physicalDevice(), queueFamily.index(), surface.address(), ptr.address()));
				if (ptr.getInt() == VK_TRUE)
					return queueType;
			}
		}
		throw new UnsupportedConfigurationException("No supported queue found!");
	}
	
	private static int bestMinImageCount(@NotNull VkSurface<?> surface) {
		int minImageCount = surface.capabilities().minImageCount();
		int maxImageCount = surface.capabilities().maxImageCount();
		
		for (int i : new int[] {3, 2})
			if (minImageCount <= i && i <= maxImageCount)
				return i;
		return minImageCount;
	}
	
	//alloc
	public static <WINDOW extends Window> ManagedSwapchain<WINDOW> alloc(
			@NotNull ManagedDevice device,
			@NotNull VkSurface<WINDOW> surface,
			@Nullable Integer minImageCount,
			@Nullable int[] imageFormat,
			@NotNull VkExtent2D swapExtend,
			@Nullable Integer imageArrayLayers,
			int imageUsage,
			@Nullable Integer preTransform,
			@Nullable Integer compositeAlpha,
			@Nullable Integer presentMode,
			@Nullable Boolean clipped,
			@Nullable Long oldSwapchain,
			@NotNull Object[] parents
	) throws UnsupportedConfigurationException {
		
		if (minImageCount == null)
			minImageCount = bestMinImageCount(surface);
		if (imageFormat == null)
			imageFormat = surface.getBestSurfaceFormat(BEST_IMAGE_FORMATS);
		if (imageArrayLayers == null)
			imageArrayLayers = 1;
		if (preTransform == null)
			preTransform = surface.capabilities().currentTransform();
		if (compositeAlpha == null)
			compositeAlpha = VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR;
		if (presentMode == null)
			presentMode = surface.getBestPresentMode(BEST_PRESENT_MODES);
		if (clipped == null)
			clipped = true;
		if (oldSwapchain == null)
			oldSwapchain = 0L;
		
		ManagedQueue queue = device.getQueue(bestSwapchainQueue(device, surface), QUEUE_FLAG_REALTIME_BIT);
		
		try (AllocatorFrame frame = Allocator.frame()) {
			VkSwapchainCreateInfoKHR info = mallocStruct(frame, VkSwapchainCreateInfoKHR::create, VkSwapchainCreateInfoKHR.SIZEOF).set(
					VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR,
					0,
					0,
					surface.address(),
					minImageCount,
					imageFormat[0],
					imageFormat[1],
					swapExtend,
					imageArrayLayers,
					imageUsage,
					VK_SHARING_MODE_EXCLUSIVE,
					null,
					preTransform,
					compositeAlpha,
					presentMode,
					clipped,
					oldSwapchain
			);
			PointerBufferPointer swapChainPtr = PointerBufferPointer.malloc(frame);
			assertVk(nvkCreateSwapchainKHR(device, info.address(), 0, swapChainPtr.address()));
			return new ManagedSwapchain<>(swapChainPtr.getPointer(), device, surface, queue, imageFormat, parents);
		}
	}
	
	protected ManagedSwapchain(long address, @NotNull ManagedDevice device, @NotNull VkSurface<WINDOW> surface, @NotNull ManagedQueue queue, @NotNull int[] imageFormat, @NotNull Object[] parents) throws UnsupportedConfigurationException {
		super(address, device, surface, imageFormat[0], VkSwapchain.Storage::new, parents);
		this.imageFormat = imageFormat;
		this.queue = queue;
	}
	
	//parents
	private final int[] imageFormat;
	
	@NotNull
	@Override
	public ManagedDevice device() {
		return (ManagedDevice) super.device();
	}
	
	public int[] imageFormat() {
		return imageFormat;
	}
	
	//queue
	private final @NotNull ManagedQueue queue;
	
	public ManagedQueue queue() {
		return queue;
	}
	
	//methods
	public TaskCreator<? extends Future<Barrier>> present(VkPresentInfoKHR info) {
		return queue.submit(new ManagedQueue_PresentEntry(info));
	}
	
	public static class ManagedQueue_PresentEntry implements Entry {
		
		private final VkPresentInfoKHR info;
		
		public ManagedQueue_PresentEntry(VkPresentInfoKHR info) {
			this.info = info;
		}
		
		@Override
		public Barrier run(ManagedQueue queue) {
			vkQueuePresentKHR(queue, info);
			return ALWAYS_TRIGGERED_BARRIER;
		}
	}
}
