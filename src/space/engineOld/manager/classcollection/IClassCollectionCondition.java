package space.engineOld.manager.classcollection;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

public interface IClassCollectionCondition<CLS> extends Predicate<Class<?>> {
	
	Class<? extends CLS>[] getAssignable();
	
	ClassModifier getModifier();
	
	Class<? extends Annotation>[] getAnnotations();
	
	@Override
	default boolean test(Class<?> clazz) {
		Class<? extends CLS>[] assignable = getAssignable();
		if (assignable != null)
			for (Class<?> assign : assignable)
				if (!assign.isAssignableFrom(clazz))
					return false;
		
		ClassModifier modifier = getModifier();
		if (modifier != null)
			if (!modifier.test(clazz))
				return false;
		
		Class<? extends Annotation>[] annotations = getAnnotations();
		if (annotations != null) {
			Annotation[] hannot = clazz.getAnnotations();
			for (Class<? extends Annotation> ra : annotations)
				for (Annotation ha : hannot)
					if (ha.getClass() != ra)
						return false;
		}
		return true;
	}
	
	default ClassCollectionCondition<CLS> copy() {
		return new ClassCollectionCondition<>(this);
	}
}
