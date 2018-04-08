package space.util.delegate.indexmap.entry;

import space.util.baseobject.ToString;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMap.Entry;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Objects;
import java.util.function.Supplier;

public class DelegatingEntry<VALUE> implements Entry<VALUE>, ToString {
	
	public Entry<VALUE> entry;
	
	public DelegatingEntry(Entry<VALUE> entry) {
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
	public int hashCode() {
		return Integer.hashCode(this.getIndex()) ^ Objects.hashCode(this.getValue());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IndexMap.Entry))
			return false;
		IndexMap.Entry other = (IndexMap.Entry) obj;
		return (this == obj) || (this.getIndex() == other.getIndex() && Objects.equals(this.getValue(), other.getValue()));
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
