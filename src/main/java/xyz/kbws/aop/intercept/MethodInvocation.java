package xyz.kbws.aop.intercept;

import xyz.kbws.aop.aspect.JoinPoint;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author kbws
 * @Date 2023/10/2
 * @Description: 执行拦截器链
 */
public class MethodInvocation implements JoinPoint {
    /**
     * 代理对象
     */
    private Object proxy;

    /**
     * 代理的目标方法
     */
    private Method method;

    /**
     * 代理的目标对象
     */
    private Object target;

    /**
     * 代理的目标类
     */
    private Class<?> targetClass;

    /**
     * 代理的方法的参数列表
     */
    private Object[] arguments;

    /**
     * 回调方法链
     */
    private List<Object> interceptorsAndDynamicMethodMatchers;

    /**
     * 保存自定义属性
     */
    private Map<String, Object> userAttributes;

    private int currentInterceptor = -1;

    public MethodInvocation(Object proxy, Method method, Object target, Class<?> targetClass, Object[] arguments,
                            List<Object> interceptorAndDynamicMethodMatchers){
        this.proxy = proxy;
        this.method = method;
        this.target = target;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorAndDynamicMethodMatchers;
    }

    public Object proceed() throws Exception{
        // 如果执行链执行完后，执行joinPoint自己的业务逻辑方法
        if (this.currentInterceptor == this.interceptorsAndDynamicMethodMatchers.size() -1) {
            return this.method.invoke(this.target, this.arguments);
        }

        Object interceptorOrInterceptionAdvice =
                this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptor);

        // 如果该对象属于拦截器
        if (interceptorOrInterceptionAdvice instanceof MethodInterceptor) {
            MethodInterceptor mi = (MethodInterceptor) interceptorOrInterceptionAdvice;
            return mi.invoke(this);
        }

        return proceed();
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public void setUserAttribute(String key, Object value) {
        if (value != null) {
            if (this.userAttributes == null) {
                this.userAttributes = new HashMap<>();
            }
            this.userAttributes.put(key, value);
        } else {
            if (this.userAttributes != null) {
                this.userAttributes.remove(key);
            }
        }
    }

    @Override
    public Object getUserAttribute(String key) {
        return this.userAttributes != null ? this.userAttributes.get(key) : null;
    }
}
