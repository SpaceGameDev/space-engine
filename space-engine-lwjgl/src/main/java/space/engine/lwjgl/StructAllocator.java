package space.engine.lwjgl;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.Struct;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.sync.barrier.Barrier;
import space.engine.unsafe.UnsafeInstance;
import sun.misc.Unsafe;

import static space.engine.sync.barrier.Barrier.ALWAYS_TRIGGERED_BARRIER;

public class StructAllocator {
	
	private static final Unsafe UNSAFE = UnsafeInstance.getUnsafe();
	
	//struct
	public static <T extends Struct> T allocStruct(StructCreator<T> create, long sizeOf, Object... parents) {
		long address = UNSAFE.allocateMemory(sizeOf);
		T t = create.create(address);
		Attachment.setAttachment(t, new StorageUnsafeFree(t, address, parents));
		return t;
	}
	
	public static <T extends Struct> T wrapStruct(StructCreator<T> create, long address, Object... parents) {
		T t = create.create(address);
		Attachment.setAttachment(t, new StorageNoFree(t, parents));
		return t;
	}
	
	@FunctionalInterface
	public interface StructCreator<T> {
		
		T create(long address);
	}
	
	//buffer
	public static <T extends CustomBuffer<?>> T allocBuffer(BufferCreator<T> create, long sizeOf, int length, Object... parents) {
		long address = UNSAFE.allocateMemory(sizeOf * length);
		T t = create.create(address, length);
		Attachment.setAttachment(t, new StorageUnsafeFree(t, address, parents));
		return t;
	}
	
	public static <T extends CustomBuffer<?>> T wrapBuffer(BufferCreator<T> create, long address, int length, Object... parents) {
		T t = create.create(address, length);
		Attachment.setAttachment(t, new StorageNoFree(t, parents));
		return t;
	}
	
	@FunctionalInterface
	public interface BufferCreator<T> {
		
		T create(long address, int length);
	}
	
	//storage
	private static class StorageUnsafeFree extends FreeableStorage {
		
		private final long address;
		
		public StorageUnsafeFree(@NotNull Object referent, long address, @NotNull Object[] parents) {
			super(referent, parents);
			this.address = address;
		}
		
		@Override
		protected @NotNull Barrier handleFree() {
			UNSAFE.freeMemory(address);
			return ALWAYS_TRIGGERED_BARRIER;
		}
	}
	
	private static class StorageNoFree extends FreeableStorage {
		
		public StorageNoFree(@NotNull Object referent, @NotNull Object[] parents) {
			super(referent, parents);
		}
		
		@Override
		protected @NotNull Barrier handleFree() {
			return ALWAYS_TRIGGERED_BARRIER;
		}
	}
}
