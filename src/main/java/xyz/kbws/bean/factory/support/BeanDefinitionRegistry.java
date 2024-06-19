package xyz.kbws.bean.factory.support;

import xyz.kbws.bean.factory.config.BeanDefinition;

/**
 * @author kbws
 * @date 2024/6/19
 * @description:
 */
public interface BeanDefinitionRegistry {
    /**
     * 向注册表中注册 BeanDefinition
     *
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
