package space.engine.buffer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.baseobject.ToString;
import space.engine.freeableStorage.Freeable.FreeableWrapper;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.math.MathUtils;
import space.engine.string.String2D;
import space.engine.string.builder.CharBufferBuilder2D;
import space.engine.string.toStringHelper.ToStringHelper;
import space.engine.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;
import space.engine.sync.barrier.Barrier;

public abstract class AbstractBuffer extends Buffer implements FreeableWrapper, ToString {
	
	private final @NotNull Storage storage;
	
	protected AbstractBuffer(Allocator allocator, long address, @NotNull Object[] parents) {
		this.storage = new Storage(this, allocator, address, parents);
	}
	
	@Override
	public long address() {
		return storage.getAddress();
	}
	
	//storage
	@Override
	public @NotNull FreeableStorage getStorage() {
		return storage;
	}
	
	public static class Storage extends FreeableStorage {
		
		public final Allocator allocator;
		private final long address;
		
		public Storage(@Nullable Object referent, Allocator allocator, long address, @NotNull Object[] parents) {
			super(referent, parents);
			this.allocator = allocator;
			this.address = address;
		}
		
		public long getAddress() {
			throwIfFreed();
			return address;
		}
		
		@Override
		protected @NotNull Barrier handleFree() {
			allocator.free(address);
			return Barrier.ALWAYS_TRIGGERED_BARRIER;
		}
	}
	
	//toString
	@Override
	public @NotNull String2D dump() {
		long capacityLong = sizeOf();
		if (capacityLong > (1 << 30))
			return new String2D("Buffer too big!");
		int capacity = (int) capacityLong;
		long address = address();
		
		CharBufferBuilder2D<?> b = new CharBufferBuilder2D<>(2, capacity * 3);
		for (int i = 0; i < capacity; i++) {
			int pos = i * 3;
			byte d = UNSAFE.getByte(address + i);
			
			if (i % 8 == 0)
				b.setY(0).setX(pos).append(Integer.toHexString(i));
			b.setY(1).setX(pos).append(MathUtils.DIGITS[(d >>> 4) & 0xF]).append(MathUtils.DIGITS[d & 0xF]);
		}
		return b.toString2D();
	}
	
	@Override
	@NotNull
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		if (this.storage.isFreed()) {
			tsh.add("address", "freed");
		} else {
			tsh.add("address", this.storage.address);
			tsh.add("sizeOf", sizeOf());
		}
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
