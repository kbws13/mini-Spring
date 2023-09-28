package xyz.kbws.annotation;

import java.lang.annotation.*;

/**
 * @Author kbws
 * @Date 2023/9/28
 * @Description: 通用组件模式注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Component {

}
