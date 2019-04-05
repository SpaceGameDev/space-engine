package space.engine.lwjgl;

import org.junit.Test;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import space.engine.freeableStorage.Freeable;

import static org.junit.Assert.*;

public class StructAllocatorTest {
	
	@Test
	public void testStructAlloc() {
		VkInstanceCreateInfo vkInstanceCreateInfo = StructAllocator.allocStruct(VkInstanceCreateInfo::create, VkInstanceCreateInfo.SIZEOF);
		assertNotNull(Freeable.getFreeable(vkInstanceCreateInfo));
	}
	
	@Test
	public void testBufferAlloc() {
		VkInstanceCreateInfo.Buffer buffer = StructAllocator.allocBuffer(VkInstanceCreateInfo::create, VkInstanceCreateInfo.SIZEOF, 5);
		assertEquals(buffer.capacity(), 5);
		assertNotNull(Freeable.getFreeable(buffer));
	}
}
