package space.util.keygen.attribute;

import space.util.baseobject.Copyable;
import space.util.baseobject.ToString;
import space.util.indexmap.IndexMap;
import space.util.keygen.IKey;
import space.util.keygen.IKeyGenerator;
import space.util.keygen.impl.DisposableKeyGenerator;
import space.util.keygen.map.KeyMapKeyGeneric;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AttributeListCreator implements IAttributeListCreator, ToString {
	
	static {
		Copyable.manualEntry(AttributeList.class, AttributeList::copy);
	}
	
	public final IKeyGenerator gen;
	public List<DefaultEntry<?>> defList = new ArrayList<>();
	
	public AttributeListCreator() {
		this(new DisposableKeyGenerator());
	}
	
	public AttributeListCreator(IKeyGenerator gen) {
		this.gen = gen;
	}
	
	//key
	@Override
	public <T> IKey<T> generateKey() {
		return gen.generateKey();
	}
	
	@Override
	public <T> IKey<T> generateKey(Supplier<T> defaultValue) {
		IKey<T> key = generateKey();
		defList.add(new DefaultEntry<>(key, defaultValue));
		return key;
	}
	
	@Override
	public boolean isKeyOf(IKey<?> key) {
		return gen.isKeyOf(key);
	}
	
	//create
	@Override
	public IAttributeList create() {
		return new AttributeList();
	}
	
	public class AttributeList extends KeyMapKeyGeneric<Object> implements IAttributeList, ToString {
		
		protected AttributeList() {
			super(AttributeListCreator.this.gen);
			defList.forEach(entry -> entry.apply(this));
		}
		
		//copy
		private AttributeList copy() {
			return new AttributeList(Copyable.copy(map));
		}
		
		private AttributeList(IndexMap<Object> map) {
			super(map, AttributeListCreator.this.gen);
		}
		
		//toString
		@Override
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("map", this.map);
			tsh.add("creator", AttributeListCreator.this);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	//defEntry
	private static class DefaultEntry<T> implements ToString {
		
		IKey<T> key;
		Supplier<T> def;
		
		public DefaultEntry(IKey<T> key, Supplier<T> def) {
			this.key = key;
			this.def = def;
		}
		
		public void apply(KeyMapKeyGeneric<? super T> map) {
			map.put(key, def.get());
		}
		
		@Override
		@SuppressWarnings("TypeParameterHidesVisibleType")
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("key", this.key.getID());
			tsh.add("def", this.def);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("gen", this.gen);
		tsh.add("defList", this.defList);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
