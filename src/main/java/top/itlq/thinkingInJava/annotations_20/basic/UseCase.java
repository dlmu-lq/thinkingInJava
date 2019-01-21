package top.itlq.thinkingInJava.annotations_20.basic;

import java.lang.annotation.*;

// 元注解，应用位置，方法/域
@Target(ElementType.METHOD)
// 元注解，应用级别，源代码/类文件/运行时
@Retention(RetentionPolicy.RUNTIME)
// 允许继承
//@Inherited
public @interface UseCase {
    // 使用注解时可以赋的元数据
    public int id();
    // 可以有默认值
    public String description() default "no description";
}
