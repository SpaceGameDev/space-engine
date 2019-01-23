package space.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * marks a class which caches its variables<br>
 * only one instance may be used per thread<br>
 * a Threadlocal Cache of the class is advised
 */
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.CLASS)
public @interface Caching {

}
