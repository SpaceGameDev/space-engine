package space.engine.key.attribute;

import org.jetbrains.annotations.NotNull;
import space.engine.key.KeyGenerator;

public class AttributeListCreator<TYPE> extends KeyGenerator<AttributeKey<?>> {
	
	public static final Object DEFAULT = new Object() {
		@Override
		public String toString() {
			return "DEF";
		}
	};
	public static final Object UNCHANGED = new Object() {
		@Override
		public String toString() {
			return "UNCH";
		}
	};
	
	//create lists
	public @NotNull AttributeList<TYPE> create() {
		return new AttributeList<>(this);
	}
	
	public @NotNull AttributeListModify<TYPE> createModify() {
		return new AttributeListModify<>(new AttributeList<>(this));
	}
	
	//create keys
	public <V> AttributeKey<V> createKey() {
		return new AttributeKey<>(this);
	}
}
