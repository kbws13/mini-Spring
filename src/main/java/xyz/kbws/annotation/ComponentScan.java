package xyz.kbws.annotation;

import java.lang.annotation.*;

/**
 * @Author kbws
 * @Date 2023/9/28
 * @Description: 配置需要扫描的包
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ComponentScan {
    String value() default "";
}
