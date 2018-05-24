package space.util.delegate.indexmap.entry;

import org.jetbrains.annotations.NotNull;
import space.util.indexmap.IndexMap.Entry;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class ModificationAwareEntry<VALUE> extends DelegatingEntry<VALUE> {
	
	public Entry<VALUE> entry;
	public Runnable onModification;
	
	public ModificationAwareEntry(Entry<VALUE> entry, Runnable onModification) {
		super(entry);
		this.onModification = onModification;
	}
	
	@Override
	public void setValue(VALUE v) {
		entry.setValue(v);
		onModification.run();
	}
	
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
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
