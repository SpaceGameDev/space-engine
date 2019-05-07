package space.engine.vulkan.vma;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.util.vma.VmaAllocationCreateInfo;
import org.lwjgl.vulkan.VkBufferCreateInfo;
import space.engine.buffer.Allocator;
import space.engine.buffer.AllocatorStack.AllocatorFrame;
import space.engine.buffer.Buffer;
import space.engine.buffer.pointer.PointerBufferPointer;
import space.engine.freeableStorage.Freeable;
import space.engine.freeableStorage.Freeable.FreeableWrapper;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.freeableStorage.stack.FreeableStack.Frame;
import space.engine.sync.barrier.Barrier;
import space.engine.vulkan.VkDevice;
import space.engine.vulkan.VkInstance;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import static org.lwjgl.util.vma.Vma.*;
import static space.engine.freeableStorage.Freeable.addIfNotContained;
import static space.engine.vulkan.VkException.assertVk;

public class VkBuffer implements FreeableWrapper {
	
	//alloc
	public static @NotNull VkBuffer alloc(VkBufferCreateInfo info, VmaAllocationCreateInfo allocInfo, @NotNull VmaAllocator allocator, Buffer src, @NotNull Object[] parents) {
		return alloc(info, allocInfo, allocator, src, 0, 0, src.sizeOf(), parents);
	}
	
	public static @NotNull VkBuffer alloc(VkBufferCreateInfo info, VmaAllocationCreateInfo allocInfo, @NotNull VmaAllocator allocator, Buffer src, long srcOffset, long destOffset, long length, @NotNull Object[] parents) {
		return alloc(info, allocInfo, allocator, dest -> Buffer.copyMemory(src, srcOffset, dest, destOffset, length), parents);
	}
	
	public static @NotNull VkBuffer alloc(VkBufferCreateInfo info, VmaAllocationCreateInfo allocInfo, @NotNull VmaAllocator allocator, Consumer<MappedBuffer> uploadData, @NotNull Object[] parents) {
		VkBuffer buffer = alloc(info, allocInfo, allocator, parents);
		try (Frame frame = Freeable.frame()) {
			uploadData.accept(buffer.mapMemory(new Object[] {frame}));
		}
		return buffer;
	}
	
	public static @NotNull VkBuffer alloc(VkBufferCreateInfo info, VmaAllocationCreateInfo allocInfo, @NotNull VmaAllocator allocator, @NotNull Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			PointerBufferPointer ptrBuffer = PointerBufferPointer.malloc(frame);
			PointerBufferPointer ptrAllocation = PointerBufferPointer.malloc(frame);
			assertVk(nvmaCreateBuffer(allocator.address(), info.address(), allocInfo.address(), ptrBuffer.address(), ptrAllocation.address(), 0));
			return create(ptrBuffer.getPointer(), ptrAllocation.getPointer(), info.size(), allocator, parents);
		}
	}
	
	//create
	public static @NotNull VkBuffer create(long address, long vmaAllocation, long sizeOf, @NotNull VmaAllocator allocator, @NotNull Object[] parents) {
		return new VkBuffer(address, vmaAllocation, sizeOf, allocator, Storage::new, parents);
	}
	
	public static @NotNull VkBuffer wrap(long address, long vmaAllocation, long sizeOf, @NotNull VmaAllocator allocator, @NotNull Object[] parents) {
		return new VkBuffer(address, vmaAllocation, sizeOf, allocator, Freeable::createDummy, parents);
	}
	
	//const
	public VkBuffer(long address, long vmaAllocation, long sizeOf, @NotNull VmaAllocator allocator, @NotNull BiFunction<VkBuffer, Object[], Freeable> storageCreator, @NotNull Object[] parents) {
		this.address = address;
		this.vmaAllocation = vmaAllocation;
		this.sizeOf = sizeOf;
		this.allocator = allocator;
		this.storage = storageCreator.apply(this, addIfNotContained(parents, allocator));
	}
	
	//parents
	private final @NotNull VmaAllocator allocator;
	
	public VmaAllocator allocator() {
		return allocator;
	}
	
	public VkDevice device() {
		return allocator.device();
	}
	
	public VkInstance instance() {
		return allocator.instance();
	}
	
	//address
	private final long address;
	private final long vmaAllocation;
	private final long sizeOf;
	
	public long address() {
		return address;
	}
	
	public long vmaAllocation() {
		return vmaAllocation;
	}
	
	public long sizeOf() {
		return sizeOf;
	}
	
	//storage
	private final @NotNull Freeable storage;
	
	@Override
	public @NotNull Freeable getStorage() {
		return storage;
	}
	
	public static class Storage extends FreeableStorage {
		
		private final @NotNull VmaAllocator allocator;
		private final long address;
		private final long allocationAddress;
		
		public Storage(@NotNull VkBuffer o, @NotNull Object[] parents) {
			super(o, parents);
			this.allocator = o.allocator;
			this.address = o.address;
			this.allocationAddress = o.vmaAllocation;
		}
		
		@Override
		protected @NotNull Barrier handleFree() {
			vmaDestroyBuffer(allocator.address(), address, allocationAddress);
			return Barrier.ALWAYS_TRIGGERED_BARRIER;
		}
	}
	
	//mapping
	public MappedBuffer mapMemory(Object[] parents) {
		try (AllocatorFrame frame = Allocator.frame()) {
			PointerBufferPointer ptr = PointerBufferPointer.malloc(frame);
			assertVk(nvmaMapMemory(allocator.address(), vmaAllocation, ptr.address()));
			return new MappedBuffer(ptr.getPointer(), parents);
		}
	}
	
	public class MappedBuffer extends Buffer implements FreeableWrapper {
		
		private final long address;
		
		public MappedBuffer(long address, Object[] parents) {
			this.address = address;
			this.storage = new MappedBufferStorage(this, addIfNotContained(parents, VkBuffer.this));
		}
		
		@Override
		public long address() {
			return address;
		}
		
		@Override
		public long sizeOf() {
			return sizeOf;
		}
		
		//storage
		private final MappedBufferStorage storage;
		
		@Override
		public @NotNull Freeable getStorage() {
			return storage;
		}
		
		//getter
		public VkBuffer vkBuffer() {
			return VkBuffer.this;
		}
	}
	
	public static class MappedBufferStorage extends FreeableStorage {
		
		private final VkBuffer vkBuffer;
		
		public MappedBufferStorage(@NotNull MappedBuffer mappedBuffer, @NotNull Object[] parents) {
			super(mappedBuffer, parents);
			this.vkBuffer = mappedBuffer.vkBuffer();
		}
		
		@Override
		protected @NotNull Barrier handleFree() {
			vmaUnmapMemory(vkBuffer.allocator.address(), vkBuffer.vmaAllocation);
			return Barrier.ALWAYS_TRIGGERED_BARRIER;
		}
	}
}
