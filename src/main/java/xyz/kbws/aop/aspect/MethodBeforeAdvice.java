package xyz.kbws.aop.aspect;

import xyz.kbws.aop.intercept.MethodInterceptor;
import xyz.kbws.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @Author kbws
 * @Date 2023/10/2
 * @Description: 前置通知
 */
public class MethodBeforeAdvice extends AbstractAspectJAdvice implements Advice, MethodInterceptor {
    public MethodBeforeAdvice(Method aspectMethod, Object aspectTarget){
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Exception {
        super.invokeAdviceMethod(mi, null, null);
        return mi.proceed();
    }
}
