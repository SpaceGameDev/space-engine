package space.util.dependency;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Objects;

public class NoDepDependency implements ToString, Dependency {
	
	@Nullable
	public final String uuid;
	
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
	public int hashCode() {
		return Objects.hashCode(uuid);
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
