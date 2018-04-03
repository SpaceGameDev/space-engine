package space.util.delegate.indexmap.entry;

import space.util.baseobject.ToString;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.function.Supplier;

public class DelegatingEntry<VALUE> implements IndexMapEntry<VALUE>, ToString {
	
	public IndexMapEntry<VALUE> entry;
	
	public DelegatingEntry(IndexMapEntry<VALUE> entry) {
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
		entry.setValue(v);
	}
	
	@Override
	public VALUE setIfAbsent(Supplier<VALUE> v) {
		return entry.setIfAbsent(v);
	}
	
	@Override
	public void remove() {
		entry.remove();
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		return api.createModifier("delegate", entry);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
