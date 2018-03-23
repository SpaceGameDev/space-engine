package space.util.dependency;

import space.util.baseobject.Setable;
import space.util.baseobject.ToString;
import space.util.baseobject.exceptions.InvalidSetException;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class NoDepDependency implements Setable, ToString, IDependency {
	
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
	public void set(Object obj) throws InvalidSetException {
		if (!(obj instanceof IDependency))
			throw new InvalidSetException(obj.getClass());
		
		IDependency dep = (IDependency) obj;
		uuid = dep.uuid();
	}
	
	@Override
	public int hashCode() {
		return uuid.hashCode();
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("uuid", this.uuid);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
