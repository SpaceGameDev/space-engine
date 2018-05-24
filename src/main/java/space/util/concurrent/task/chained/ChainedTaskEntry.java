package space.util.concurrent.task.chained;

import org.jetbrains.annotations.NotNull;
import space.util.baseobject.ToString;
import space.util.dependency.Dependency;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Comparator;

public class ChainedTaskEntry<FUNCTION> implements ToString, Comparable<ChainedTaskEntry<FUNCTION>> {
	
	public static final Comparator<ChainedTaskEntry<?>> COMPARATOR = (o1, o2) -> o1 == o2 ? 0 : Dependency.COMPARATOR.compare(o1.dependency, o2.dependency);
	
	public Dependency dependency;
	public FUNCTION function;
	
	public ChainedTaskEntry(Dependency dependency, FUNCTION function) {
		this.dependency = dependency;
		this.function = function;
	}
	
	@Override
	public int compareTo(@NotNull ChainedTaskEntry<FUNCTION> o) {
		return COMPARATOR.compare(this, o);
	}
	
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
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
