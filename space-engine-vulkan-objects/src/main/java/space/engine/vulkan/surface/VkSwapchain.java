package space.engine.vulkan.surface;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VkImageSubresourceRange;
import org.lwjgl.vulkan.VkImageViewCreateInfo;
import org.lwjgl.vulkan.VkSwapchainCreateInfoKHR;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack.AllocatorFrame;
import space.engine.buffer.array.ArrayBufferPointer;
import space.engine.buffer.pointer.PointerBufferInt;
import space.engine.buffer.pointer.PointerBufferPointer;
import space.engine.freeableStorage.Freeable;
import space.engine.freeableStorage.Freeable.FreeableWrapper;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.sync.barrier.Barrier;
import space.engine.vulkan.VkDevice;
import space.engine.vulkan.VkImage;
import space.engine.vulkan.VkImageView;
import space.engine.window.Window;

import java.util.Arrays;
import java.util.function.BiFunction;

import static org.lwjgl.vulkan.KHRSwapchain.*;
import static org.lwjgl.vulkan.VK10.*;
import static space.engine.freeableStorage.Freeable.addIfNotContained;
import static space.engine.lwjgl.LwjglStructAllocator.mallocStruct;
import static space.engine.vulkan.VkException.assertVk;

public class VkSwapchain<WINDOW extends Window> implements FreeableWrapper {
	
	//alloc
	public static <WINDOW extends Window> VkSwapchain<WINDOW> alloc(VkSwapchainCreateInfoKHR info, @NotNull VkDevice device, @NotNull VkSurface<WINDOW> surface, @NotNull Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			PointerBufferPointer swapChainPtr = PointerBufferPointer.malloc(frame);
			assertVk(nvkCreateSwapchainKHR(device, info.address(), 0, swapChainPtr.address()));
			return create(swapChainPtr.getPointer(), device, surface, info.imageFormat(), parents);
		}
	}
	
	//create
	public static <WINDOW extends Window> VkSwapchain<WINDOW> create(long address, @NotNull VkDevice device, @NotNull VkSurface<WINDOW> surface, int imageFormat, @NotNull Object[] parents) {
		return new VkSwapchain<>(address, device, surface, imageFormat, Storage::new, parents);
	}
	
	public static <WINDOW extends Window> VkSwapchain<WINDOW> wrap(long address, @NotNull VkDevice device, @NotNull VkSurface<WINDOW> surface, int imageFormat, @NotNull Object[] parents) {
		return new VkSwapchain<>(address, device, surface, imageFormat, Freeable::createDummy, parents);
	}
	
	//const
	public VkSwapchain(long address, @NotNull VkDevice device, @NotNull VkSurface<WINDOW> surface, int imageFormat, @NotNull BiFunction<VkSwapchain, Object[], Freeable> storageCreator, @NotNull Object[] parents) {
		this.device = device;
		this.surface = surface;
		this.address = address;
		this.storage = storageCreator.apply(this, addIfNotContained(parents, device));
		
		//images
		try (AllocatorFrame frame = Allocator.frame()) {
			PointerBufferInt count = PointerBufferInt.malloc(frame);
			ArrayBufferPointer imagesBuffer;
			while (true) {
				assertVk(nvkGetSwapchainImagesKHR(device, address, count.address(), 0));
				imagesBuffer = ArrayBufferPointer.malloc(Allocator.heap(), count.getInt(), new Object[] {frame});
				if (assertVk(nvkGetSwapchainImagesKHR(device, address, count.address(), imagesBuffer.address())) == VK_SUCCESS)
					break;
				Freeable.freeObject(imagesBuffer);
			}
			
			this.images = imagesBuffer.stream().mapToObj(ptr -> VkImage.wrap(ptr, device, new Object[] {this})).toArray(VkImage[]::new);
			this.imageViews = Arrays.stream(images).map(image -> VkImageView.alloc(
					mallocStruct(frame, VkImageViewCreateInfo::create, VkImageViewCreateInfo.SIZEOF).set(
							VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO,
							0,
							0,
							image.address(),
							VK_IMAGE_VIEW_TYPE_2D,
							imageFormat,
							VkImageView.SWIZZLE_MASK_IDENTITY,
							mallocStruct(frame, VkImageSubresourceRange::create, VkImageSubresourceRange.SIZEOF).set(
									VK_IMAGE_ASPECT_COLOR_BIT,
									0, 1,
									0, 1
							)
					),
					image,
					new Object[] {this}
			)).toArray(VkImageView[]::new);
		}
	}
	
	//parents
	private final @NotNull VkDevice device;
	private final @NotNull VkSurface<WINDOW> surface;
	
	public @NotNull VkDevice device() {
		return device;
	}
	
	public @NotNull VkSurface<WINDOW> surface() {
		return surface;
	}
	
	//storage
	private final @NotNull Freeable storage;
	
	@Override
	public @NotNull Freeable getStorage() {
		return storage;
	}
	
	//address
	private final long address;
	
	public long address() {
		return address;
	}
	
	public static class Storage extends FreeableStorage {
		
		private final @NotNull VkDevice device;
		private final long address;
		
		public Storage(@NotNull VkSwapchain swapChain, @NotNull Object[] parents) {
			super(swapChain, parents);
			this.device = swapChain.device();
			this.address = swapChain.address();
		}
		
		@Override
		protected @NotNull Barrier handleFree() {
			nvkDestroySwapchainKHR(device, address, 0);
			return Barrier.ALWAYS_TRIGGERED_BARRIER;
		}
	}
	
	//images
	private final @NotNull VkImage[] images;
	private final @NotNull VkImageView[] imageViews;
	
	public @NotNull VkImage[] images() {
		return images;
	}
	
	public @NotNull VkImageView[] imageViews() {
		return imageViews;
	}
}
