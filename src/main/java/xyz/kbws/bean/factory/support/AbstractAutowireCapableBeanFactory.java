package xyz.kbws.bean.factory.support;

import xyz.kbws.bean.BeanException;
import xyz.kbws.bean.factory.config.BeanDefinition;

/**
 * @author kbws
 * @date 2024/6/19
 * @description: 实例化 Bean 类
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanException {
        Object bean = null;
        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeanException("Instantiation of bean failed");
        }
        addSingleton(beanName, bean);
        return bean;
    }
}
