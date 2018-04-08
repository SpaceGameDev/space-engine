package space.util.delegate.indexmap.entry;

import space.util.indexmap.IndexMap.Entry;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.function.Supplier;

public class UnmodifiableEntry<VALUE> extends DelegatingEntry<VALUE> {
	
	public UnmodifiableEntry(Entry<VALUE> entry) {
		super(entry);
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
		return api.createModifier("unmodifiable", entry);
	}
}
