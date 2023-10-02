package xyz.kbws.aop.aspect;

import xyz.kbws.aop.intercept.MethodInterceptor;
import xyz.kbws.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @Author kbws
 * @Date 2023/10/2
 * @Description: 后置通知
 */
public class AfterReturningAdvice extends AbstractAspectJAdvice implements Advice, MethodInterceptor {
    public AfterReturningAdvice(Method aspectMethod, Object aspectTarget){
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Exception {
        Object returnValue = mi.proceed();
        invokeAdviceMethod(mi, returnValue, null);
        return returnValue;
    }
}
