package space.engine.vulkan.exception;

import space.engine.vulkan.VkPhysicalDevice;

public class UnsupportedDeviceException extends Exception {
	
	public UnsupportedDeviceException(VkPhysicalDevice physicalDevice) {
		super("Unsupported physical device " + physicalDevice.properties().deviceNameString());
	}
	
	public UnsupportedDeviceException() {
	}
	
	public UnsupportedDeviceException(String message) {
		super(message);
	}
	
	public UnsupportedDeviceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UnsupportedDeviceException(Throwable cause) {
		super(cause);
	}
	
	public UnsupportedDeviceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
