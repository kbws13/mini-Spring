package xyz.kbws.aop.intercept;

/**
 * @Author kbws
 * @Date 2023/10/2
 * @Description: 方法拦截器接口
 */
public interface MethodInterceptor {
    Object invoke(MethodInvocation mi) throws Exception;
}
