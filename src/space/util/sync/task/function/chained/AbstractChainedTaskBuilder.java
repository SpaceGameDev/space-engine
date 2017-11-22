package space.util.sync.task.function.chained;

import space.util.baseobject.BaseObject;
import space.util.delegate.list.ModificationAwareList;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;
import space.util.sync.task.basic.ITask;
import space.util.sync.task.function.typehandler.ITypeHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractChainedTaskBuilder<FUNCTION> implements BaseObject, IChainedTaskBuilder<FUNCTION> {
	
	public List<ChainedTaskEntry<FUNCTION>> list = new ModificationAwareList<>(new ArrayList<>(), this::onModification);
	
	@Override
	public void addTask(ChainedTaskEntry<FUNCTION> task) {
		list.add(task);
	}
	
	@Override
	public boolean removeTask(ChainedTaskEntry<FUNCTION> task) {
		return list.remove(task);
	}
	
	@Override
	public boolean removeTaskIf(Predicate<? super ChainedTaskEntry<FUNCTION>> filter) {
		return list.removeIf(filter);
	}
	
	@Override
	public abstract ITask create(ITypeHandler<FUNCTION> handler);
	
	public abstract void onModification();
	
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
