package space.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tyte Parameters, Arguments and Return Types or others marked with @{@link Self} will be/return themselves
 * e.g. (always "return this;" when a Return Type)
 */
@Target(value = {ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_PARAMETER})
@Retention(value = RetentionPolicy.CLASS)
public @interface Self {
	
}
