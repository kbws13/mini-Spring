package xyz.kbws.bean.factory.support;

import xyz.kbws.bean.BeanException;
import xyz.kbws.bean.factory.BeanFactory;
import xyz.kbws.bean.factory.config.BeanDefinition;

/**
 * @author kbws
 * @date 2024/6/19
 * @description:
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) throws BeanException {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanException;
}
