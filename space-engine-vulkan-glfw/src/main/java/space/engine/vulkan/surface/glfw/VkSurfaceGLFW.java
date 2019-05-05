package space.engine.vulkan.surface.glfw;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.vulkan.VkExtensionProperties;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack.AllocatorFrame;
import space.engine.buffer.StringConverter;
import space.engine.buffer.pointer.PointerBufferPointer;
import space.engine.vulkan.VkExtensions;
import space.engine.vulkan.VkInstance;
import space.engine.vulkan.surface.VkSurface;
import space.engine.window.glfw.GLFWWindow;
import space.engine.window.glfw.GLFWWindowFramework;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static space.engine.lwjgl.PointerBufferWrapper.streamPointerBuffer;
import static space.engine.vulkan.VkException.assertVk;

public class VkSurfaceGLFW {
	
	//supported
	
	/**
	 * the parameter #windowFramework is unused by this method and just ensures GLFW is initialized before this is called
	 */
	public static boolean supported(@SuppressWarnings("unused") GLFWWindowFramework windowFramework) {
		return GLFWVulkan.glfwVulkanSupported();
	}
	
	/**
	 * the parameter #windowFramework is unused by this method and just ensures GLFW is initialized before this is called
	 */
	public static void assertSupported(GLFWWindowFramework windowFramework) {
		if (!supported(windowFramework))
			throw new RuntimeException("GLFW Vulkan not supported!");
	}
	
	//extensions
	public static @NotNull Collection<VkExtensionProperties> getRequiredInstanceExtensions(GLFWWindowFramework windowFramework) {
		PointerBuffer pointerBuffer = GLFWVulkan.glfwGetRequiredInstanceExtensions();
		if (pointerBuffer == null)
			throw new RuntimeException("Required extensions unavailable!");
		
		return streamPointerBuffer(pointerBuffer)
				.mapToObj(StringConverter::UTF8ToString)
				.map(name -> Objects.requireNonNull(VkExtensions.extensionNameMap().get(name), "Required extensions not found!"))
				.collect(Collectors.toList());
	}
	
	//surface creation
	public static @NotNull VkSurface<GLFWWindow> createSurfaceFromGlfwWindow(@NotNull VkInstance instance, @NotNull GLFWWindow window, @NotNull Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			PointerBufferPointer surfacePtr = PointerBufferPointer.malloc(frame);
			assertVk(GLFWVulkan.nglfwCreateWindowSurface(instance.address(), window.getWindowPointer(), 0, surfacePtr.address()));
			return VkSurface.create(surfacePtr.getPointer(), instance, window, parents);
		}
	}
}
