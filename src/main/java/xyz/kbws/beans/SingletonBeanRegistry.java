package xyz.kbws.beans;

/**
 * @author kbws
 * @date 2024/6/12
 * @description: 单例 Bean 注册接口
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    String[] getSingletonNames();
}
