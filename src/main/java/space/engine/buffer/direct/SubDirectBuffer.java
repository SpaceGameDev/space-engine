package space.engine.buffer.direct;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.string.toStringHelper.ToStringHelper;
import space.engine.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;
import space.engine.sync.barrier.Barrier;

public class SubDirectBuffer extends UnsafeNonFreeDirectBuffer {
	
	@Nullable
	public Object parent;
	
	public SubDirectBuffer(long address, long capacity, @Nullable Object parent, Object[] parents) {
		super(address, capacity, parents);
		this.parent = parent;
	}
	
	@Override
	public synchronized @NotNull Barrier free() {
		parent = null;
		return super.free();
	}
	
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("isFreed", this.storage.isFreed());
		tsh.add("parent", this.parent);
		tsh.add("address", this.storage.address);
		tsh.add("capacity", this.storage.capacity);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
