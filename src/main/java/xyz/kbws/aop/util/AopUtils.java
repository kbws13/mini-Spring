package xyz.kbws.aop.util;

import xyz.kbws.annotation.aop.*;
import xyz.kbws.aop.AopConfig;
import xyz.kbws.aop.AopProxy;
import xyz.kbws.aop.CglibAopProxy;
import xyz.kbws.aop.JdkDynamicAopProxy;
import xyz.kbws.aop.support.AdvisedSupport;
import xyz.kbws.beans.factory.config.BeanDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author kbws
 * @Date 2023/10/2
 * @Description: Aop工具类
 */
public class AopUtils {
    /**
     * 存储切面配置信息
     */
    public static final List<AdvisedSupport> CONFIGS = new ArrayList<>();

    /**
     * 初始化AOP配置类
     */
    public static void instantiationAopConfig(List<BeanDefinition> beanDefinitions) throws Exception {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
            // 如果该类不是切面Aspect则跳过
            if (!clazz.isAnnotationPresent(Aspect.class)) {
                continue;
            }
            AopConfig config = new AopConfig();

            Method[] methods = clazz.getMethods();

            // 设置切点和回调方法
            for (Method method : methods) {
                if (method.isAnnotationPresent(Pointcut.class)) {
                    // 设置切点
                    config.setPointCut(method.getAnnotation(Pointcut.class).value());
                }
                else if (method.isAnnotationPresent(Before.class)) {
                    // 前后方法
                    config.setBefore(method.getName());
                }
                else if (method.isAnnotationPresent(AfterReturning.class)) {
                    // 后置方法
                    config.setAfterReturn(method.getName());
                }
                else if (method.isAnnotationPresent(AfterThrowing.class)) {
                    // 异常方法
                    config.setAfterThrow(method.getName());
                    config.setAfterThrowClass("java.lang.Exception");
                }
            }
            // 没有设置切点，跳过
            if (config.getPointCut() == null) {
                continue;
            }
            config.setAspectClass(beanDefinition.getBeanClassName());
            CONFIGS.add(new AdvisedSupport(config));
        }
    }

    /**
     * 创建代理类
     */
    public static AopProxy createProxy(AdvisedSupport config) {
        Class targetClass = config.getTargetClass();
        // 如果实现了接口则使用jdk动态代理，否则使用Cglib代理
        if (targetClass.getInterfaces().length > 0) {
            return new JdkDynamicAopProxy(config);
        }
        // 使用CGLIB代理
        return new CglibAopProxy(config);
    }

    /**
     * 判断是否是代理类
     */
    public static boolean isAopProxy(Object object) {
        return object.getClass().getSimpleName().contains("$");
    }

    /**
     * 获取被代理的对象
     */
    public static Object getTarget(Object proxy) throws Exception {

        if(proxy.getClass().getSuperclass() == Proxy.class) {
            return getJdkDynamicProxyTargetObject(proxy);
        } else {
            return getCglibProxyTargetObject(proxy);
        }
    }

    /**
     * 获取CGLIB被代理的对象
     */
    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field config = dynamicAdvisedInterceptor.getClass().getDeclaredField("config");
        config.setAccessible(true);

        return ((AdvisedSupport) config.get(dynamicAdvisedInterceptor)).getTarget();
    }

    /**
     * 获取jdk代理被代理对象
     */
    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        Object aopProxy = h.get(proxy);

        Field config = aopProxy.getClass().getDeclaredField("config");
        config.setAccessible(true);

        return ((AdvisedSupport) config.get(aopProxy)).getTarget();
    }
}
