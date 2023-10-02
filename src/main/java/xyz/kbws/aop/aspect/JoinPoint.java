package xyz.kbws.aop.aspect;

import java.lang.reflect.Method;

/**
 * @Author kbws
 * @Date 2023/10/2
 * @Description: 连接点接口，定义切点的抽象
 */
public interface JoinPoint {
    /**
     * 业务方法本身
     */
    Method getMethod();

    /**
     * 该方法的参数列表
     */
    Object[] getArguments();

    /**
     * 该方法对应的对象
     */
    Object getThis();

    /**
     * 在joinPoint中添加自定义熟悉
     */
    void setUserAttribute(String key, Object value);

    /**
     * 获取自定义属性
     */
    Object getUserAttribute(String key);
}
