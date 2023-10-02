package xyz.kbws.aop.aspect;

import xyz.kbws.aop.intercept.MethodInterceptor;
import xyz.kbws.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @Author kbws
 * @Date 2023/10/2
 * @Description: 异常通知
 */
public class AfterThrowingAdvice extends AbstractAspectJAdvice implements Advice, MethodInterceptor {
    private String throwingName;
    private MethodInvocation mi;

    public AfterThrowingAdvice(Method aspectMethod, Object aspectTarget){
        super(aspectMethod, aspectTarget);
    }

    public void setThrowingName(String name){
        this.throwingName = name;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Exception {
        try {
            return mi.proceed();
        }catch (Throwable ex){
            super.invokeAdviceMethod(mi, null, ex.getCause());
            throw ex;
        }
    }
}
