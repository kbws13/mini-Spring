package xyz.kbws.annotation;

import java.lang.annotation.*;

/**
 * @Author kbws
 * @Date 2023/9/28
 * @Description: 自动注入注解
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
