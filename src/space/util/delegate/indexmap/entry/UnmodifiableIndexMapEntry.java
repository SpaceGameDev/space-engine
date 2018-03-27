package space.util.delegate.indexmap.entry;

import space.util.baseobject.ToString;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.function.Supplier;

public class UnmodifiableIndexMapEntry<VALUE> implements IndexMapEntry<VALUE>, ToString {
	
	public IndexMapEntry<VALUE> entry;
	
	public UnmodifiableIndexMapEntry(IndexMapEntry<VALUE> entry) {
		this.entry = entry;
	}
	
	@Override
	public int getIndex() {
		return entry.getIndex();
	}
	
	@Override
	public VALUE getValue() {
		return entry.getValue();
	}
	
	@Override
	public void setValue(VALUE v) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public VALUE setIfAbsent(Supplier<VALUE> v) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("entry", this.entry);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
