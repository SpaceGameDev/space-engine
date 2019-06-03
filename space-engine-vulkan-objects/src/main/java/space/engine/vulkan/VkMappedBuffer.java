package space.engine.vulkan;

import space.engine.buffer.Buffer;

public interface VkMappedBuffer extends VkBuffer {
	
	//mapping
	Buffer mapMemory(Object[] parents);
}
