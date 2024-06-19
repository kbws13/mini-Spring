package xyz.kbws.bean.factory.config;

/**
 * @author kbws
 * @date 2024/6/19
 * @description: 单例注册接口定义
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

}
