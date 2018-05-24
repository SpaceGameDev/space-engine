package space.util.dependency;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.baseobject.Setable;
import space.util.baseobject.ToString;
import space.util.baseobject.exceptions.InvalidSetException;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class NoDepDependency implements Setable, ToString, Dependency {
	
	@Nullable
	public String uuid;
	
	public NoDepDependency() {
	}
	
	public NoDepDependency(@Nullable String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	@Nullable
	public String uuid() {
		return uuid;
	}
	
	@Override
	@Nullable
	public String[] requires() {
		return null;
	}
	
	@Override
	@Nullable
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
	public void set(@NotNull Object obj) throws InvalidSetException {
		if (!(obj instanceof Dependency))
			throw new InvalidSetException(obj.getClass());
		
		Dependency dep = (Dependency) obj;
		uuid = dep.uuid();
	}
	
	@Override
	public int hashCode() {
		return uuid != null ? uuid.hashCode() : 0;
	}
	
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("uuid", (Object) this.uuid);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
