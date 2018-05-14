package space.engine.classcollection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoLoading {
	
	String[] requires();
	
	String[] requiredBy();
	
	int defaultPriority();
}
