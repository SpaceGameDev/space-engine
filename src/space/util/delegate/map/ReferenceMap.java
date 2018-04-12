package space.util.delegate.map;

import space.util.delegate.collection.ReferenceCollection;
import space.util.delegate.util.ReferenceUtil;

import java.lang.ref.Reference;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * A {@link ReferenceMap} remaps all Entries to a {@link Reference} of type E. These References are created by the Reference Creator supplied with the Constructor or directly set.
 */
public class ReferenceMap<K, V> extends ConvertingMap.BiDirectionalSparse<K, Reference<? extends V>, V> {
	
	public ReferenceMap(Map<K, Reference<? extends V>> map, Function<? super V, ? extends Reference<? extends V>> refCreator) {
		super(map, ReferenceUtil::getSafe, refCreator);
	}
	
	@Override
	public Collection<V> values() {
		return new ReferenceCollection<>(map.values(), reverseSparse);
	}
}
