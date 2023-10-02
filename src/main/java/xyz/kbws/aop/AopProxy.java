package xyz.kbws.aop;

/**
 * @Author kbws
 * @Date 2023/10/2
 * @Description: 代理工厂接口
 */
public interface AopProxy {
    Object getProxy();
    Object getProxy(ClassLoader classLoader);
}
