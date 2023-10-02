package xyz.kbws.aop.aspect;

import java.lang.reflect.Method;

/**
 * @Author kbws
 * @Date 2023/10/2
 * @Description: 定义回调通知的通用逻辑
 */
public abstract class AbstractAspectJAdvice {
    private Method aspectMethod;
    private Object aspectTarget;

    public AbstractAspectJAdvice(Method aspectMethod, Object aspectTarget){
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    protected Object invokeAdviceMethod(JoinPoint joinPoint, Object returnValue, Throwable ex) throws Exception{
        Class<?>[] paramsTypes = this.aspectMethod.getParameterTypes();
        if (paramsTypes.length == 0){
            return this.aspectMethod.invoke(aspectTarget);
        }
        Object[] args = new Object[paramsTypes.length];
        for (int i = 0; i < paramsTypes.length; i++){
            if (paramsTypes[i] == JoinPoint.class){
                args[i] = joinPoint;
            }else if (paramsTypes[i] == Throwable.class){
                args[i] = ex;
            }else if (paramsTypes[i] == Object.class){
                args[i] = returnValue;
            }
        }
        return this.aspectMethod.invoke(aspectTarget, args);
    }

    public Method getAspectMethod() {
        return aspectMethod;
    }

    public void setAspectMethod(Method aspectMethod) {
        this.aspectMethod = aspectMethod;
    }

    public Object getAspectTarget() {
        return aspectTarget;
    }

    public void setAspectTarget(Object aspectTarget) {
        this.aspectTarget = aspectTarget;
    }
}
