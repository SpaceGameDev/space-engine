package space.util.dependency;

import space.util.baseobject.Setable;
import space.util.baseobject.ToString;
import space.util.baseobject.exceptions.InvalidSetException;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class SimpleDependency implements Setable, ToString, Dependency {
	
	public String uuid;
	public String[] requires;
	public String[] requiredBy;
	public int defaultPriority;
	
	public SimpleDependency() {
	}
	
	public SimpleDependency(String uuid) {
		this(uuid, null, null, 0);
	}
	
	public SimpleDependency(String uuid, int defaultPriority) {
		this(uuid, null, null, defaultPriority);
	}
	
	public SimpleDependency(String uuid, String[] requires) {
		this(uuid, requires, null, 0);
	}
	
	public SimpleDependency(String uuid, String[] requires, int defaultPriority) {
		this(uuid, requires, null, defaultPriority);
	}
	
	public SimpleDependency(String uuid, String[] requires, String[] requiredBy) {
		this(uuid, requires, requiredBy, 0);
	}
	
	public SimpleDependency(String uuid, String[] requires, String[] requiredBy, int defaultPriority) {
		this.uuid = uuid;
		this.requires = requires;
		this.requiredBy = requiredBy;
		this.defaultPriority = defaultPriority;
	}
	
	@Override
	public String uuid() {
		return uuid;
	}
	
	@Override
	public String[] requires() {
		return requires;
	}
	
	@Override
	public String[] requiredBy() {
		return requiredBy;
	}
	
	@Override
	public int defaultPriority() {
		return defaultPriority;
	}
	
	@Override
	public int hashCode() {
		return uuid.hashCode();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	@Override
	public void set(Object obj) throws InvalidSetException {
		if (!(obj instanceof Dependency))
			throw new InvalidSetException(obj.getClass());
		
		Dependency dep = (Dependency) obj;
		uuid = dep.uuid();
		requires = dep.requires();
		requiredBy = dep.requiredBy();
		defaultPriority = dep.defaultPriority();
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("uuid", uuid);
		tsh.add("requires", requires);
		tsh.add("requiredBy", requiredBy);
		tsh.add("defaultPriority", defaultPriority);
		return tsh.build();
	}
}
