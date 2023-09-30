package xyz.kbws.web.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @Author kbws
 * @Date 2023/9/30
 * @Description: 用于保存URL和Method的对应关系
 */
public class HandlerMapping {
    /**
     * 对应的Controller对象
     */
    private Object controller;
    /**
     * 对应的方法
     */
    private Method method;
    /**
     * URL的封装
     */
    private Pattern pattern;

    public HandlerMapping(Object controller, Method method, Pattern pattern){
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
