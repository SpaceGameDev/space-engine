package space.util.indexmap;

import space.util.indexmap.IndexMap.Entry;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * An IndexMap which ensures threadsafety and <i>should</i> be Concurrent.
 *
 * @implSpec Currently it just synchronizes all methods required to ensure threadsafety, but isn't actually concurrent.<br>
 * I am also not sure if making it concurrent will give any benefit, as all it's functions are very simple and fast and introducing anything like locks on an index may already be too much overhead.
 */
public class ConcurrentIndexMapArray<VALUE> extends IndexMapArray<VALUE> {
	
	public ConcurrentIndexMapArray() {
	}
	
	public ConcurrentIndexMapArray(int initCapacity) {
		super(initCapacity);
	}
	
	public ConcurrentIndexMapArray(VALUE[] array) {
		super(array);
	}
	
	@Override
	public synchronized boolean ensureCapacityAvailable(int index) {
		return super.ensureCapacityAvailable(index);
	}
	
	@Override
	public synchronized boolean ensureCapacity(int capa) {
		return super.ensureCapacity(capa);
	}
	
	@Override
	public synchronized boolean isExpandable() {
		return super.isExpandable();
	}
	
	@Override
	public synchronized int size() {
		return super.size();
	}
	
	@Override
	protected synchronized VALUE putAndExpand(int index, VALUE v) {
		return super.putAndExpand(index, v);
	}
	
	@Override
	protected synchronized VALUE getDefault() {
		return super.getDefault();
	}
	
	@Override
	public synchronized VALUE get(int index) {
		return super.get(index);
	}
	
	//not synchronized
	@Override
	public Entry<VALUE> getEntry(int index) {
		return super.getEntry(index);
	}
	
	@Override
	public synchronized VALUE put(int index, VALUE value) {
		return super.put(index, value);
	}
	
	@Override
	public synchronized VALUE remove(int index) {
		return super.remove(index);
	}
	
	@Override
	public synchronized VALUE[] toArray() {
		return super.toArray();
	}
	
	@Override
	public synchronized VALUE[] toArray(VALUE[] array) {
		return super.toArray(array);
	}
	
	@Override
	public synchronized void addAll(Collection<? extends VALUE> coll) {
		super.addAll(coll);
	}
	
	@Override
	public synchronized void putAll(IndexMap<? extends VALUE> indexMap) {
		super.putAll(indexMap);
	}
	
	@Override
	public synchronized void putAllIfAbsent(IndexMap<? extends VALUE> indexMap) {
		super.putAllIfAbsent(indexMap);
	}
	
	@Override
	public synchronized VALUE getOrDefault(int index, VALUE def) {
		return super.getOrDefault(index, def);
	}
	
	@Override
	public synchronized VALUE putIfAbsent(int index, VALUE value) {
		return super.putIfAbsent(index, value);
	}
	
	@Override
	public synchronized VALUE putIfPresent(int index, VALUE value) {
		return super.putIfPresent(index, value);
	}
	
	@Override
	public synchronized boolean replace(int index, VALUE oldValue, VALUE newValue) {
		return super.replace(index, oldValue, newValue);
	}
	
	@Override
	public synchronized boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		return super.replace(index, oldValue, newValue);
	}
	
	@Override
	public synchronized boolean remove(int index, VALUE value) {
		return super.remove(index, value);
	}
	
	@Override
	public synchronized VALUE compute(int index, ComputeFunction<? super VALUE, ? extends VALUE> function) {
		return super.compute(index, function);
	}
	
	@Override
	public synchronized VALUE computeIfAbsent(int index, Supplier<? extends VALUE> supplier) {
		return super.computeIfAbsent(index, supplier);
	}
	
	@Override
	public synchronized VALUE computeIfPresent(int index, Supplier<? extends VALUE> supplier) {
		return super.computeIfPresent(index, supplier);
	}
	
	@Override
	public synchronized void clear() {
		super.clear();
	}
	
	//not synchronized
	
	/**
	 * the Collection is weakly consistent
	 */
	@Override
	public Collection<VALUE> values() {
		return super.values();
	}
	
	//not synchronized
	
	/**
	 * the Collection is weakly consistent
	 */
	@Override
	public Collection<Entry<VALUE>> table() {
		return super.table();
	}
	
	@Override
	public synchronized <T> T toTSH(ToStringHelper<T> api) {
		return super.toTSH(api);
	}
	
	@Override
	public synchronized boolean isEmpty() {
		return super.isEmpty();
	}
	
	@Override
	public synchronized boolean contains(int index) {
		return super.contains(index);
	}
	
	@Override
	public synchronized void add(VALUE value) {
		super.add(value);
	}
}
