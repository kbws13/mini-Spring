package xyz.kbws.beans;

/**
 * @author kbws
 * @date 2024/6/10
 * @description:
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeanException;

    void registerBeanDefinition(BeanDefinition beanDefinition);
}
