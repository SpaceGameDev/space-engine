package space.engine.key.attribute;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.ToString;
import space.engine.key.IllegalKeyException;
import space.engine.key.Key;
import space.engine.key.KeyGenerator;
import space.engine.key.impl.DisposableKeyGenerator;
import space.engine.string.toStringHelper.ToStringHelper;
import space.engine.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.function.Supplier;

public class AttributeListCreator<TYPE> implements ToString, KeyGenerator {
	
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
	
	/**
	 * <p><b>NO NULLABLE!</b></p>
	 * It can be null if the Default Value is null, but it's not possible to define that.
	 * We are expecting the Developer to know whether the default can be null or not.
	 */
	@SuppressWarnings("ConstantConditions")
	protected static <V> V correctDefault(V v, Key<V> key) {
		return v != DEFAULT ? v : key.getDefaultValue();
	}
	
	public final KeyGenerator gen;
	
	public AttributeListCreator() {
		this(new DisposableKeyGenerator(false));
	}
	
	public AttributeListCreator(KeyGenerator gen) {
		this.gen = gen;
	}
	
	public void check(Key<?> key) {
		if (!gen.isKeyOf(key))
			throw new IllegalKeyException(key);
	}
	
	protected int getInitialIndexMapCapacity() {
		int i = gen.estimateKeyPoolMax();
		return i < 16 ? 16 : i;
	}
	
	//delegate to gen
	
	@Override
	public <T> @NotNull Key<T> generateKey() {
		return gen.generateKey();
	}
	
	@Override
	public <T> @NotNull Key<T> generateKey(@NotNull Supplier<T> def) {
		return gen.generateKey(def);
	}
	
	@Override
	public boolean isKeyOf(@NotNull Key<?> key) {
		return gen.isKeyOf(key);
	}
	
	@Override
	public <T> @NotNull Key<T> generateKey(T def) {
		return gen.generateKey(def);
	}
	
	@Override
	public Key<?> getKey(int id) {
		return gen.getKey(id);
	}
	
	@Override
	public @NotNull Collection<Key<?>> getKeys() {
		return gen.getKeys();
	}
	
	@Override
	public int estimateKeyPoolMax() {
		return gen.estimateKeyPoolMax();
	}
	
	//create
	public @NotNull AttributeList<TYPE> create() {
		return new AttributeList<>(this);
	}
	
	public @NotNull AttributeListModification<TYPE> createModify() {
		return new AttributeListModification<>(this);
	}
	
	//toString
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("gen", this.gen);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
