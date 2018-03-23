package space.util.delegate.indexmap;

import space.util.delegate.collection.ConvertingCollection;
import space.util.delegate.collection.ModificationAwareCollection;
import space.util.delegate.indexmap.entry.ModificationAwareEntry;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * The {@link ModificationAwareIndexMap} will call the {@link ModificationAwareIndexMap#onModification} {@link Runnable} when the {@link IndexMap} is modified.
 */
public class ModificationAwareIndexMap<VALUE> extends DelegatingIndexMap<VALUE> {
	
	public Runnable onModification;
	
	public ModificationAwareIndexMap(IndexMap<VALUE> indexMap, Runnable onModification) {
		super(indexMap);
		this.onModification = onModification;
	}
	
	@Override
	public void add(VALUE v) {
		super.add(v);
		onModification.run();
	}
	
	@Override
	public VALUE put(int index, VALUE v) {
		VALUE ret = super.put(index, v);
		onModification.run();
		return ret;
	}
	
	@Override
	public VALUE remove(int index) {
		VALUE ret = super.remove(index);
		onModification.run();
		return ret;
	}
	
	@Override
	public void addAll(Collection<VALUE> coll) {
		super.addAll(coll);
		if (!coll.isEmpty())
			onModification.run();
	}
	
	@Override
	public void putAll(IndexMap<VALUE> indexMap) {
		super.putAll(indexMap);
		if (!indexMap.isEmpty())
			onModification.run();
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<VALUE> indexMap) {
		super.putAllIfAbsent(indexMap);
		if (!indexMap.isEmpty())
			onModification.run();
	}
	
	@Override
	public VALUE putIfAbsent(int index, VALUE v) {
		VALUE ret = super.putIfAbsent(index, v);
		onModification.run();
		return ret;
	}
	
	@Override
	public VALUE putIfAbsent(int index, Supplier<? extends VALUE> v) {
		VALUE ret = super.putIfAbsent(index, v);
		onModification.run();
		return ret;
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, VALUE newValue) {
		boolean ret = super.replace(index, oldValue, newValue);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		boolean ret = super.replace(index, oldValue, newValue);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public boolean remove(int index, VALUE v) {
		boolean ret = super.remove(index, v);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public void clear() {
		super.clear();
		onModification.run();
	}
	
	@Override
	public IndexMapEntry<VALUE> getEntry(int index) {
		return new ModificationAwareEntry<>(super.getEntry(index), onModification);
	}
	
	@Override
	public boolean remove(VALUE v) {
		boolean ret = super.remove(v);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public Collection<VALUE> values() {
		return new ModificationAwareCollection<>(super.values(), onModification);
	}
	
	@Override
	public Collection<IndexMapEntry<VALUE>> table() {
		return new ModificationAwareCollection<>(ConvertingCollection.<IndexMapEntry<VALUE>, IndexMapEntry<VALUE>>createConvertingBiDirectional(super.table(), entry -> new ModificationAwareEntry<>(entry, onModification), modEntry -> modEntry instanceof ModificationAwareEntry ? ((ModificationAwareEntry) modEntry).entry : null), onModification);
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("indexMap", this.indexMap);
		tsh.add("onModification", this.onModification);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
