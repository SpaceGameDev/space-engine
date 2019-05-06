package space.engine.vulkan.managed.device;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import space.engine.vulkan.VkDevice;
import space.engine.vulkan.VkPhysicalDevice;
import space.engine.vulkan.VkQueue;
import space.engine.vulkan.VkQueueFamilyProperties;

/**
 * Manages Device with it's queues.
 * You should assume that
 */
@SuppressWarnings("unused")
public abstract class ManagedDevice extends VkDevice {
	
	protected ManagedDevice(long handle, @NotNull VkPhysicalDevice physicalDevice, @NotNull VkDeviceCreateInfo ci, @NotNull Object[] parents) {
		super(handle, physicalDevice, ci, VkDevice.Storage::new, parents);
	}
	
	//queue
	public static final int QUEUE_TYPE_GRAPHICS = 0x0;
	public static final int QUEUE_TYPE_COMPUTE = 0x1;
	public static final int QUEUE_TYPE_TRANSFER = 0x2;
	
	public static final int QUEUE_FLAG_REALTIME_BIT = 0x1;
	public static final int QUEUE_FLAG_TRANSFER_HOST_TO_DEVICE_BIT = 0x2;
	
	public abstract @NotNull VkQueueFamilyProperties getQueueFamily(int type) throws QueueNotAvailableException;
	
	public abstract @NotNull VkQueue getQueue(int type, int flags) throws QueueNotAvailableException;
	
	public static class QueueNotAvailableException extends IllegalArgumentException {
		
		public QueueNotAvailableException(int type, int flags) {
			super("Queue type " + type + " with flags " + flags + " not available!");
		}
		
		public QueueNotAvailableException(int type) {
			super("Queue type " + type + " not available!");
		}
		
		public QueueNotAvailableException() {
		}
		
		public QueueNotAvailableException(String s) {
			super(s);
		}
		
		public QueueNotAvailableException(String message, Throwable cause) {
			super(message, cause);
		}
		
		public QueueNotAvailableException(Throwable cause) {
			super(cause);
		}
	}
}
