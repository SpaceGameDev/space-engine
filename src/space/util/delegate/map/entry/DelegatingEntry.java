package space.util.delegate.map.entry;

import java.util.Map;
import java.util.Objects;

public class DelegatingEntry<K, V> implements Map.Entry<K, V> {
	
	public Map.Entry<K, V> entry;
	
	public DelegatingEntry(Map.Entry<K, V> entry) {
		this.entry = entry;
	}
	
	@Override
	public K getKey() {
		return entry.getKey();
	}
	
	@Override
	public V getValue() {
		return entry.getValue();
	}
	
	@Override
	public V setValue(V value) {
		return entry.setValue(value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Map.Entry))
			return false;
		Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
		return Objects.equals(getKey(), e.getKey()) && Objects.equals(getValue(), e.getValue());
	}
}
