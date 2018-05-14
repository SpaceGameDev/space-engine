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
	public void add(VALUE value) {
		super.add(value);
		onModification.run();
	}
	
	@Override
	public VALUE put(int index, VALUE value) {
		VALUE ret = super.put(index, value);
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
	public void addAll(Collection<? extends VALUE> coll) {
		super.addAll(coll);
		if (!coll.isEmpty())
			onModification.run();
	}
	
	@Override
	public void putAll(IndexMap<? extends VALUE> indexMap) {
		super.putAll(indexMap);
		if (!indexMap.isEmpty())
			onModification.run();
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<? extends VALUE> indexMap) {
		super.putAllIfAbsent(indexMap);
		if (!indexMap.isEmpty())
			onModification.run();
	}
	
	@Override
	public VALUE putIfAbsent(int index, VALUE value) {
		VALUE ret = super.putIfAbsent(index, value);
		if (ret != value)
			onModification.run();
		return ret;
	}
	
	@Override
	public VALUE putIfPresent(int index, VALUE value) {
		VALUE ret = super.putIfPresent(index, value);
		if (ret != value)
			onModification.run();
		return ret;
	}
	
	@Override
	public boolean remove(int index, VALUE value) {
		boolean ret = super.remove(index, value);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public VALUE compute(int index, ComputeFunction<? super VALUE, ? extends VALUE> function) {
		VALUE ret = super.compute(index, function);
		onModification.run();
		return ret;
	}
	
	@Override
	public VALUE computeIfAbsent(int index, Supplier<? extends VALUE> supplier) {
		boolean[] mod = new boolean[1];
		VALUE ret = super.computeIfAbsent(index, () -> {
			mod[0] = true;
			return supplier.get();
		});
		if (mod[0])
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
	public VALUE computeIfPresent(int index, Supplier<? extends VALUE> supplier) {
		boolean[] mod = new boolean[1];
		VALUE ret = super.computeIfAbsent(index, () -> {
			mod[0] = true;
			return supplier.get();
		});
		if (mod[0])
			onModification.run();
		return ret;
	}
	
	@Override
	public void clear() {
		super.clear();
		onModification.run();
	}
	
	@Override
	public Entry<VALUE> getEntry(int index) {
		return new ModificationAwareEntry<>(super.getEntry(index), onModification);
	}
	
	@Override
	public Collection<VALUE> values() {
		return new ModificationAwareCollection<>(super.values(), onModification);
	}
	
	@Override
	public Collection<Entry<VALUE>> table() {
		return new ModificationAwareCollection<>(new ConvertingCollection.BiDirectional<>(super.table(), entry -> new ModificationAwareEntry<>(entry, onModification), modEntry -> modEntry instanceof ModificationAwareEntry ? ((ModificationAwareEntry<VALUE>) modEntry).entry : null), onModification);
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
