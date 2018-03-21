package space.util.delegate.list;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.List;

public abstract class ConvertingList<F, T> implements List<T>, ToString {
	
	public List<F> list;
	
	public ConvertingList(List<F> list) {
		this.list = list;
	}
	
	@SuppressWarnings("TypeParameterHidesVisibleType")
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("list", this.list);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
