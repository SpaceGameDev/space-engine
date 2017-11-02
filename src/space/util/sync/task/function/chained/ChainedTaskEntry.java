package space.util.sync.task.function.chained;

import space.util.dependency.IDependency;

import java.util.Comparator;

public class ChainedTaskEntry<FUNCTION> implements Comparable<ChainedTaskEntry<FUNCTION>> {
	
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
}
