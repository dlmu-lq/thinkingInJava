package top.itlq.thinkingInJava.annotations_20.databaseTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInteger {
    public String name() default "";
    public Constraints constraints() default @Constraints;
}
