package space.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods marked with this are supposed to be used only for debug and testing
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.CLASS)
public @interface DebugUseOnly {

}
