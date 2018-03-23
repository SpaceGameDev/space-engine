package space.util.concurrent.task.chained;

import space.util.baseobject.ToString;
import space.util.dependency.IDependency;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Comparator;

public class ChainedTaskEntry<FUNCTION> implements ToString, Comparable<ChainedTaskEntry<FUNCTION>> {
	
	public static final Comparator<ChainedTaskEntry<?>> COMPARATOR = (o1, o2) -> o1 == o2 ? 0 : IDependency.COMPARATOR.compare(o1.dependency, o2.dependency);
	
	public IDependency dependency;
	public FUNCTION function;
	
	public ChainedTaskEntry(IDependency dependency, FUNCTION function) {
		this.dependency = dependency;
		this.function = function;
	}
	
	@Override
	public int compareTo(ChainedTaskEntry<FUNCTION> o) {
		return COMPARATOR.compare(this, o);
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("dependency", this.dependency);
		tsh.add("function", this.function);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
