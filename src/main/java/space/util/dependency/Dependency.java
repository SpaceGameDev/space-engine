package space.util.dependency;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.dependency.exception.CircleDependencyException;

import java.util.Comparator;

/**
 * A Dependency Utility Object.
 * It can require something to be put before (require), it can put itself to be put before something else (requiredBy())
 * and it can have a global priority to be executed sooner or later even without direct dependency (defaultPriority()).<br>
 * <br>
 * <h3>UUID Strings:</h3>
 * It is <b>strongly recommended</b> to add a uuid to all Dependency-Objects and put these UUIDs into public-static-final-Fields.
 * This way any object can be easily identified. The usage of '*' in UUIDs is not permitted.
 * If no Dependency is required but the uuid should be set, use the {@link NoDepDependency}-Object.
 * UUIDs can be validated by the isUuidValid()-Method.
 * <br>
 * <h3>Adding Dependency</h3>
 * The Methods require() and requiredBy() are used to define Dependency. They can return null if it dows not have any special Dependency on anything.
 * If an actual String[]-Array-Object is returned, each entry contains either a uuid or a pattern. Pattern are recognized by having a '*'-Character at the end of the String.
 * A Pattern can be detected by the isPartialMatchingPattern()-Method and the pattern to be searched for can be extracted by the getPartialMatchingPattern()-Method.
 * If the String[]-Array contains a <b>uuid</b> (a String with no '*'-Character at the end), it will require/requireBy a Dependency having the <b>exact</b> uuid.
 * If it contains a <b>pattern</b> ('*' at the end), it will require/requireBy a Dependency with it's uuid <b>containing</b> the String <b>without the '*'-Character</b> at the end.<br>
 * <br>
 * <h3>Sorting Dependencies:</h3>
 * <ul>
 * <li>if the {@link Dependency} requires something (requires()-Method), something should be put in front of the {@link Dependency} object</li>
 * <li>if the {@link Dependency} is required by something (requiredBy()-Method), something should be put after the {@link Dependency} object</li>
 * <li>if none of these apply, the Object with the <b>lower</b> defaultPriority should be <b>executed first</b> (defaultPriority()-Method)</li>
 * <li>if the defaultPriority if equal, the order of those two objects does not matter</li>
 * </ul>
 *
 * @see SimpleDependency
 * @see NoDepDependency
 */
public interface Dependency extends Comparable<Dependency> {
	
	//static
	//static uuid
	char STAR = '*';
	/**
	 * comp < 1  ->  o1 before o2<br>
	 * comp > 1  ->  o2 before o1
	 */
	Comparator<@NotNull Dependency> COMPARATOR = (o1, o2) -> {
		boolean o1_before_o2 = find(o1, o2.requires()) || find(o2, o1.requiredBy());
		boolean o2_before_o1 = find(o2, o1.requires()) || find(o1, o2.requiredBy());
		
		if (o1_before_o2 && o2_before_o1)
			throw new CircleDependencyException(o1, o2);
		if (o1_before_o2)
			return -1;
		if (o2_before_o1)
			return 1;
		
		return o1.defaultPriority() - o2.defaultPriority();
	};
	
	/**
	 * a String on which Identification of the Function in possible
	 */
	@Nullable String uuid();
	
	/**
	 * anything in this array has be executed before this is executed
	 */
	@Nullable String[] requires();
	
	/**
	 * anything in this array will be executed after this is executed
	 */
	@Nullable String[] requiredBy();
	
	/**
	 * the default priority if no dependency is given
	 */
	int defaultPriority();
	
	/**
	 * something has dependency, if any condition is met:
	 * <ul>
	 * <li>requires() something (not null)</li>
	 * <li>requiredBy() something (not null)</li>
	 * <li>defaultPriority() is not 0</li>
	 * </ul>
	 *
	 * @return if something has any dependency
	 */
	default boolean hasDependency() {
		return requires() != null || requiredBy() != null || defaultPriority() != 0;
	}
	
	@Override
	default int compareTo(@NotNull Dependency o) {
		return COMPARATOR.compare(this, o);
	}
	
	//static
	static boolean find(@NotNull Dependency func, @Nullable String[] array) {
		if (array == null)
			return false;
		
		String uuid = func.uuid();
		if (uuid == null)
			return false;
		
		for (String str : array) {
			if (str != null && (Dependency.isPartialMatchingPattern(str) && uuid.contains(getPartialMatchingPattern(str)) || uuid.equals(str)))
				return true;
		}
		return false;
	}
	
	static boolean isUuidValid(@NotNull String uuid) {
		return uuid.indexOf(STAR) == -1;
	}
	
	static boolean isPartialMatchingPattern(@NotNull String str) {
		return str.charAt(str.length() - 1) == STAR;
	}
	
	@NotNull
	static String getPartialMatchingPattern(@NotNull String str) {
		return str.substring(0, str.length() - 1);
	}
}
