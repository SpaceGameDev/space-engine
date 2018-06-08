package space.util.buffer.direct;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.freeableStorage.FreeableStorage;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class SubDirectBuffer extends UnsafeNonFreeDirectBuffer {
	
	@Nullable
	public Object parent;
	
	public SubDirectBuffer(long address, long capacity, @Nullable Object parent, FreeableStorage... lists) {
		super(address, capacity, lists);
		this.parent = parent;
	}
	
	@Override
	public synchronized void free() {
		super.free();
		parent = null;
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
