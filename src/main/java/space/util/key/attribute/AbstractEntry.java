package space.util.key.attribute;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.key.Key;

public interface AbstractEntry<V> {
	
	@NotNull Key<V> getKey();
	
	//value
	@Nullable Object getValueDirect();
}
