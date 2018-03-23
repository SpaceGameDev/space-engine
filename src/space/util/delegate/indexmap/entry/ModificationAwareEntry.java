package space.util.delegate.indexmap.entry;

import space.util.baseobject.ToString;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class ModificationAwareEntry<VALUE> implements IndexMapEntry<VALUE>, ToString {
	
	public IndexMapEntry<VALUE> entry;
	public Runnable onModification;
	
	public ModificationAwareEntry(IndexMapEntry<VALUE> entry, Runnable onModification) {
		this.entry = entry;
		this.onModification = onModification;
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
		onModification.run();
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("entry", this.entry);
		tsh.add("onModification", this.onModification);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
