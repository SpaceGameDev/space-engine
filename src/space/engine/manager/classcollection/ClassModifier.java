package space.engine.manager.classcollection;

import java.util.function.Predicate;

public class ClassModifier implements Predicate<Class<?>> {
	
	public static final ClassModifier EMPTY = new ClassModifier();
	
	public final int toHave;
	public final int notToHave;
	
	public ClassModifier(int toHave, int notToHave) {
		this.toHave = toHave;
		this.notToHave = notToHave;
	}
	
	public ClassModifier(ClassModifier... mods) {
		int toHave = 0;
		int notToHave = 0;
		
		for (ClassModifier mod : mods) {
			toHave |= mod.toHave;
			notToHave |= mod.notToHave;
		}
		
		this.toHave = toHave;
		this.notToHave = notToHave;
	}
	
	@Override
	public boolean test(Class<?> clazz) {
		int mod = clazz.getModifiers();
		return (mod & toHave) == toHave && (mod & notToHave) == 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ClassModifier))
			return false;
		
		ClassModifier that = (ClassModifier) o;
		
		if (toHave != that.toHave)
			return false;
		return notToHave == that.notToHave;
	}
	
	@Override
	public int hashCode() {
		int result = toHave;
		result = 31 * result + notToHave;
		return result;
	}
}
