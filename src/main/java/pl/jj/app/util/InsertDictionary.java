package pl.jj.app.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author JNartowicz
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InsertDictionary {
    /**
     * Dictionary names.
     * @return
     */
    String[] value() default "";
}
