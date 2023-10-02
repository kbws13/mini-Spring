package xyz.kbws.annotation.web;

import java.lang.annotation.*;

/**
 * @Author kbws
 * @Date 2023/9/30
 * @Description: 参数注解
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
    String value() default "";
}
