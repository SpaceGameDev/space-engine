package space.util.string.toStringHelper;

import space.util.string.toStringHelper.array.TSHArray;
import space.util.string.toStringHelper.modifier.TSHModifier;
import space.util.string.toStringHelper.nativeType.TSHNativeType;
import space.util.string.toStringHelper.objects.TSHObjects;

public class ToStringHelperCollectionImpl implements ToStringHelperCollection {
	
	protected TSHNativeType nativeType;
	protected TSHArray array;
	protected TSHModifier modifier;
	protected TSHObjects normal;
	
	public ToStringHelperCollectionImpl() {
	}
	
	public ToStringHelperCollectionImpl(TSHNativeType nativeType, TSHArray array, TSHModifier modifier, TSHObjects normal) {
		setNativeType(nativeType);
		setArray(array);
		setModifier(modifier);
		setNormal(normal);
	}
	
	@Override
	public TSHNativeType getNativeType() {
		return nativeType;
	}
	
	@Override
	public TSHArray getArray() {
		return array;
	}
	
	@Override
	public TSHModifier getModifier() {
		return modifier;
	}
	
	@Override
	public TSHObjects getObjectPhaser() {
		return normal;
	}
	
	public void setNativeType(TSHNativeType nativeType) {
		this.nativeType = nativeType;
		nativeType.setToStringHelperCollection(this);
	}
	
	public void setArray(TSHArray array) {
		this.array = array;
		array.setToStringHelperCollection(this);
	}
	
	public void setModifier(TSHModifier modifier) {
		this.modifier = modifier;
		modifier.setToStringHelperCollection(this);
	}
	
	public void setNormal(TSHObjects normal) {
		this.normal = normal;
		normal.setToStringHelperCollection(this);
	}
}
