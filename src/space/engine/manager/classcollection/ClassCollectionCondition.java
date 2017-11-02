package space.engine.manager.classcollection;

import space.util.ArrayUtils;
import space.util.annotation.Self;

import java.lang.annotation.Annotation;

public class ClassCollectionCondition<CLS> implements IClassCollectionCondition<CLS> {
	
	public Class<? extends CLS>[] assignable;
	public ClassModifier modifier;
	public Class<? extends Annotation>[] annotations;
	
	public ClassCollectionCondition() {
		
	}
	
	public ClassCollectionCondition(IClassCollectionCondition<CLS> ccc) {
		this(ccc.getAssignable().clone(), ccc.getModifier(), ccc.getAnnotations().clone());
	}
	
	public ClassCollectionCondition(Class<? extends CLS>[] assignable, ClassModifier modifier, Class<? extends Annotation>[] annotations) {
		this.assignable = assignable;
		this.modifier = modifier;
		this.annotations = annotations;
	}
	
	@Self
	public ClassCollectionCondition<CLS> setModifier(ClassModifier... modifier) {
		return setModifier(new ClassModifier(modifier));
	}
	
	@Override
	public ClassCollectionCondition<CLS> copy() {
		return new ClassCollectionCondition<>(assignable, modifier, annotations);
	}
	
	@Override
	public Class<? extends CLS>[] getAssignable() {
		return assignable;
	}
	
	@Self
	@SafeVarargs
	@SuppressWarnings("unchecked")
	public final <NCLS> ClassCollectionCondition<NCLS> setAssignable(Class<? extends NCLS>... assignable) {
		ClassCollectionCondition<NCLS> th = (ClassCollectionCondition<NCLS>) this;
		th.assignable = assignable;
		return th;
	}
	
	@Override
	public ClassModifier getModifier() {
		return modifier;
	}
	
	@Self
	public ClassCollectionCondition<CLS> setModifier(ClassModifier modifier) {
		this.modifier = modifier;
		return this;
	}
	
	@Override
	public Class<? extends Annotation>[] getAnnotations() {
		return annotations;
	}
	
	@Self
	@SafeVarargs
	public final ClassCollectionCondition<CLS> setAnnotations(Class<? extends Annotation>... annotations) {
		this.annotations = annotations;
		return this;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ClassCollectionCondition))
			return false;
		
		ClassCollectionCondition<?> that = (ClassCollectionCondition<?>) o;
		
		if (!ArrayUtils.equalsIgnoreSequenceBinary(assignable, that.assignable))
			return false;
		if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null)
			return false;
		return ArrayUtils.equalsIgnoreSequenceBinary(annotations, that.annotations);
	}
	
	@Override
	public int hashCode() {
		int result = ArrayUtils.hashCodeIgnoreSequence(assignable);
		result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
		result = 31 * result + ArrayUtils.hashCodeIgnoreSequence(annotations);
		return result;
	}
}
