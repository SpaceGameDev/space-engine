package space.util.dependency;

import space.util.baseobject.BaseObject;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class NoDepDependency implements BaseObject, IDependency {
	
	static {
		BaseObject.initClass(NoDepDependency.class, NoDepDependency::new, d -> new NoDepDependency(d.uuid));
	}
	
	public String uuid;
	
	public NoDepDependency() {
	}
	
	public NoDepDependency(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public String uuid() {
		return uuid;
	}
	
	@Override
	public String[] requires() {
		return null;
	}
	
	@Override
	public String[] requiredBy() {
		return null;
	}
	
	@Override
	public int defaultPriority() {
		return 0;
	}
	
	@Override
	public boolean hasDependency() {
		return false;
	}
	
	@Override
	public int hashCode() {
		return uuid.hashCode();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("uuid", this.uuid);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
