package xyz.kbws.annotation;

import java.lang.annotation.*;

/**
 * @Author kbws
 * @Date 2023/9/28
 * @Description: 数据仓库模式注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Repository {
    String value() default "";
}
