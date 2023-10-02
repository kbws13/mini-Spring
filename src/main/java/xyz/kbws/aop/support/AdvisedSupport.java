package xyz.kbws.aop.support;

import xyz.kbws.aop.AopConfig;
import xyz.kbws.aop.aspect.AfterReturningAdvice;
import xyz.kbws.aop.aspect.AfterThrowingAdvice;
import xyz.kbws.aop.aspect.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author kbws
 * @Date 2023/10/2
 * @Description: 解析AOP配置
 */
public class AdvisedSupport {
    private Class targetClass;
    private Object target;
    private Pattern pointCutClassPattern;

    private transient Map<Method, List<Object>> methodCache = new HashMap<>();

    private AopConfig config;

    public AdvisedSupport(AopConfig config){
        this.config = config;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public List<Object> getInterceptorAndDynamicInterceptionAdvice(Method method, Class<?> targetClass)throws Exception{
        List<Object> cached = this.methodCache.get(method);
        // 缓存未命中
        if (cached == null){
            Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            cached = this.methodCache.get(m);
            this.methodCache.put(m, cached);
        }
        return cached;
    }

    public boolean pointCutMatch(){
        return this.pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }

    private void parse(){
        String pointcut = config.getPointCut()
                .replaceAll("\\.", "\\\\.")
                .replaceAll("\\*", "\\.\\*")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");

        String pointCutForClass = pointcut.substring(0, pointcut.lastIndexOf("\\(") - 4);
        pointCutClassPattern = Pattern.compile("class " + pointCutForClass.substring(pointCutForClass.lastIndexOf(" ") + 1));

        Pattern pattern = Pattern.compile(pointCutForClass);

        try {
            // 获取切面类
            Class aspectClass = Class.forName(config.getAspectClass());
            Map<String, Method> aspectMethods = new HashMap<>();
            // 获取切面类的通知方法
            for (Method method : aspectClass.getMethods()) {
                aspectMethods.put(method.getName(), method);
            }

            // 获取当前对象的方法
            for (Method method : targetClass.getMethods()) {
                String methodString = method.toString();
                // 去掉方法头中throws及其往后的字符
                if (methodString.contains("throws")) {
                    methodString = methodString.substring(0,
                            methodString.lastIndexOf("throws")).trim();
                }
                Matcher matcher = pattern.matcher(methodString);
                if (matcher.matches()) {
                    // 匹配成功
                    List<Object> advices = new LinkedList<>();
                    // 前置通知
                    if (!(config.getBefore() == null || "".equals(config.getBefore()))) {
                        advices.add(new MethodBeforeAdvice(aspectMethods.get(config.getBefore()), aspectClass.newInstance()));
                    }
                    // 后置返回通知
                    if (!(config.getAfterReturn() == null || "".equals(config.getAfterReturn()))) {
                        advices.add(new AfterReturningAdvice(aspectMethods.get(config.getAfterReturn()), aspectClass.newInstance()));
                    }
                    // 异常通知
                    if (!(config.getAfterThrow() == null || "".equals(config.getAfterThrow()))) {
                        AfterThrowingAdvice afterThrowingAdvice =
                                new AfterThrowingAdvice(aspectMethods.get(config.getAfterThrow()), aspectClass.newInstance());
                        afterThrowingAdvice.setThrowingName(config.getAfterThrowClass());
                        advices.add(afterThrowingAdvice);
                    }
                    this.methodCache.put(method, advices);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
