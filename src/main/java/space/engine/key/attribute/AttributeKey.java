package space.engine.key.attribute;

import org.jetbrains.annotations.Nullable;
import space.engine.key.Key;
import space.engine.key.KeyGenerator;

public class AttributeKey<V> extends Key<V> {
	
	/**
	 * Constructor for the Key. <b>DO NOT MAKE THIS CONSTRUCTOR PUBLIC.</b> Only call it internally inside your custom createKey()-Method inside your {@link KeyGenerator}.
	 *
	 * @param gen your generator
	 */
	protected AttributeKey(AttributeListCreator<?> gen) {
		super(gen);
	}
	
	public V attributeListGet(AbstractAttributeList<?> attributeList) {
		return correctDefault(attributeList.getDirect(this));
	}
	
	public void attributeListPut(AbstractAttributeList<?> attributeList, @Nullable V value) {
		attributeList.putDirect(this, value);
	}
	
	public V attributeListGetAndPut(AbstractAttributeList<?> attributeList, @Nullable V value) {
		return correctDefault(attributeList.putDirect(this, value));
	}
	
	@SuppressWarnings({"ConstantConditions", "unchecked"})
	protected V correctDefault(@Nullable Object direct) {
		return direct == AttributeListCreator.DEFAULT ? null : (V) direct;
	}
}
