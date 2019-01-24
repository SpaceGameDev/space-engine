package space.engine.dependency;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.baseobject.ToString;
import space.engine.string.toStringHelper.ToStringHelper;
import space.engine.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class SimpleDependency implements ToString, Dependency {
	
	@Nullable
	public final String uuid;
	@Nullable
	public final String[] requires;
	@Nullable
	public final String[] requiredBy;
	public final int defaultPriority;
	
	public SimpleDependency(@Nullable String uuid, int defaultPriority) {
		this(uuid, null, null, defaultPriority);
	}
	
	public SimpleDependency(@Nullable String uuid, @Nullable String[] requires) {
		this(uuid, requires, null, 0);
	}
	
	public SimpleDependency(@Nullable String uuid, @Nullable String[] requires, int defaultPriority) {
		this(uuid, requires, null, defaultPriority);
	}
	
	public SimpleDependency(@Nullable String uuid, @Nullable String[] requires, @Nullable String[] requiredBy) {
		this(uuid, requires, requiredBy, 0);
	}
	
	public SimpleDependency(@Nullable String uuid, @Nullable String[] requires, @Nullable String[] requiredBy, int defaultPriority) {
		this.uuid = uuid;
		this.requires = requires;
		this.requiredBy = requiredBy;
		this.defaultPriority = defaultPriority;
	}
	
	@Nullable
	@Override
	public String uuid() {
		return uuid;
	}
	
	@Nullable
	@Override
	public String[] requires() {
		return requires;
	}
	
	@Nullable
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
		return uuid == null ? 0 : uuid.hashCode();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("uuid", (Object) uuid);
		tsh.add("requires", requires);
		tsh.add("requiredBy", requiredBy);
		tsh.add("defaultPriority", defaultPriority);
		return tsh.build();
	}
}
