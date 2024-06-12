package xyz.kbws.beans;

/**
 * @author kbws
 * @date 2024/6/10
 * @description:
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeanException;

    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);

}
