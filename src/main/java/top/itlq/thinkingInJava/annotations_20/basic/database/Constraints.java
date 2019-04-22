package top.itlq.thinkingInJava.annotations_20.basic.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraints {
    public boolean primaryKey() default false;
    public boolean notNull() default true;
    public boolean unique() default false;
}
