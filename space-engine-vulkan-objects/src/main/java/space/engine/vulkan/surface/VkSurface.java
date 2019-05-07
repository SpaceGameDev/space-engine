package space.engine.vulkan.surface;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack.AllocatorFrame;
import space.engine.buffer.array.ArrayBufferInt;
import space.engine.buffer.pointer.PointerBufferInt;
import space.engine.freeableStorage.Freeable;
import space.engine.freeableStorage.Freeable.FreeableWrapper;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.sync.barrier.Barrier;
import space.engine.vulkan.VkInstance;
import space.engine.vulkan.VkPhysicalDevice;
import space.engine.window.Window;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.lwjgl.vulkan.KHRSurface.*;
import static org.lwjgl.vulkan.VK10.*;
import static space.engine.freeableStorage.Freeable.addIfNotContained;
import static space.engine.lwjgl.LwjglStructAllocator.*;
import static space.engine.vulkan.VkException.assertVk;

public class VkSurface<WINDOW extends Window> implements FreeableWrapper {
	
	//create
	public static <WINDOW extends Window> VkSurface<WINDOW> create(long address, @NotNull VkPhysicalDevice physicalDevice, @NotNull WINDOW window, @NotNull Object[] parents) {
		return new VkSurface<>(address, physicalDevice, window, Storage::new, parents);
	}
	
	public static <WINDOW extends Window> VkSurface<WINDOW> wrap(long address, @NotNull VkPhysicalDevice physicalDevice, @NotNull WINDOW window, @NotNull Object[] parents) {
		return new VkSurface<>(address, physicalDevice, window, Freeable::createDummy, parents);
	}
	
	//struct
	public VkSurface(long address, @NotNull VkPhysicalDevice physicalDevice, @NotNull WINDOW window, @NotNull BiFunction<VkSurface, Object[], Freeable> storageCreator, @NotNull Object[] parents) {
		this.physicalDevice = physicalDevice;
		this.window = window;
		this.address = address;
		this.storage = storageCreator.apply(this, addIfNotContained(parents, physicalDevice, window));
		
		try (AllocatorFrame frame = Allocator.frame()) {
			//capabilities
			VkSurfaceCapabilitiesKHR capabilities = mallocStruct(Allocator.heap(), VkSurfaceCapabilitiesKHR::create, VkSurfaceCapabilitiesKHR.SIZEOF, new Object[] {storage});
			assertVk(vkGetPhysicalDeviceSurfaceCapabilitiesKHR(physicalDevice, address, capabilities));
			this.capabilities = capabilities;
			
			//formats
			PointerBufferInt count = PointerBufferInt.malloc(frame);
			VkSurfaceFormatKHR.Buffer formatBuffer;
			while (true) {
				assertVk(nvkGetPhysicalDeviceSurfaceFormatsKHR(physicalDevice, address, count.address(), 0));
				formatBuffer = mallocBuffer(Allocator.heap(), VkSurfaceFormatKHR::create, VkSurfaceFormatKHR.SIZEOF, count.getInt(), new Object[] {storage});
				if (assertVk(nvkGetPhysicalDeviceSurfaceFormatsKHR(physicalDevice, address, count.address(), 0)) == VK_SUCCESS)
					break;
				Freeable.freeObject(formatBuffer);
			}
			this.formatBuffer = formatBuffer;
			this.formats = formatBuffer.stream().collect(Collectors.toList());
			this.formatUndefined = formats.stream().anyMatch(format -> format.format() == VK_FORMAT_UNDEFINED);
			
			//presentModes
			ArrayBufferInt surfaceModeBuffer;
			while (true) {
				assertVk(nvkGetPhysicalDeviceSurfacePresentModesKHR(physicalDevice, address, count.address(), 0));
				surfaceModeBuffer = ArrayBufferInt.malloc(Allocator.heap(), count.getInt(), new Object[] {frame});
				if (assertVk(nvkGetPhysicalDeviceSurfacePresentModesKHR(physicalDevice, address, count.address(), surfaceModeBuffer.address())) == VK_SUCCESS)
					break;
				Freeable.freeObject(surfaceModeBuffer);
			}
			this.presentModes = surfaceModeBuffer.stream().boxed().collect(Collectors.toList());
		}
	}
	
	//parents
	private final @NotNull VkPhysicalDevice physicalDevice;
	
	public @NotNull VkInstance instance() {
		return physicalDevice.instance();
	}
	
	public VkPhysicalDevice physicalDevice() {
		return physicalDevice;
	}
	
	private final @NotNull WINDOW window;
	
	public @NotNull WINDOW window() {
		return window;
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
		
		private final @NotNull VkInstance instance;
		private final long address;
		
		public Storage(@NotNull VkSurface surface, Object[] parents) {
			super(surface, parents);
			this.instance = surface.instance();
			this.address = surface.address;
		}
		
		@Override
		protected @NotNull Barrier handleFree() {
			nvkDestroySurfaceKHR(instance, address, 0);
			return Barrier.ALWAYS_TRIGGERED_BARRIER;
		}
	}
	
	//capabilities
	private final @NotNull VkSurfaceCapabilitiesKHR capabilities;
	
	public @NotNull VkSurfaceCapabilitiesKHR capabilities() {
		return capabilities;
	}
	
	//formats
	@SuppressWarnings({"FieldCanBeLocal", "unused"})
	private final @NotNull VkSurfaceFormatKHR.Buffer formatBuffer;
	private final @NotNull Collection<VkSurfaceFormatKHR> formats;
	private final boolean formatUndefined;
	
	public @NotNull Collection<VkSurfaceFormatKHR> formats() {
		return formats;
	}
	
	public boolean isFormatUndefined() {
		return formatUndefined;
	}
	
	public boolean isSurfaceFormatSupported(int format, int colorSpace) {
		if (formatUndefined)
			return true;
		
		for (VkSurfaceFormatKHR o : formats)
			if (o.format() == format && o.colorSpace() == colorSpace)
				return true;
		return false;
	}
	
	public int[] getBestSurfaceFormat(int[][] formatsToChooseFrom) {
		if (formatUndefined && formatsToChooseFrom.length > 0)
			return formatsToChooseFrom[0];
		
		for (int[] format : formatsToChooseFrom)
			if (isSurfaceFormatSupported(format[0], format[1]))
				return format;
		throw new RuntimeException("No best SurfaceFormat found!");
	}
	
	public VkSurfaceFormatKHR getBestSurfaceFormat(VkSurfaceFormatKHR[] formatsToChooseFrom) {
		if (formatUndefined && formatsToChooseFrom.length > 0)
			return formatsToChooseFrom[0];
		
		for (VkSurfaceFormatKHR format : formatsToChooseFrom)
			if (isSurfaceFormatSupported(format.format(), format.colorSpace()))
				return format;
		throw new RuntimeException("No best SurfaceFormat found!");
	}
	
	//presentModes
	private final @NotNull Collection<Integer> presentModes;
	
	public @NotNull Collection<Integer> presentModes() {
		return presentModes;
	}
	
	public boolean isPresentModeSupported(int presentMode) {
		return presentModes.contains(presentMode);
	}
	
	public int getBestPresentMode(int[] presentModesToChooseFrom) {
		for (int presentMode : presentModesToChooseFrom)
			if (isPresentModeSupported(presentMode))
				return presentMode;
		throw new RuntimeException("No best PresentMode found!");
	}
}
