package xyz.kbws.beans;

/**
 * @author kbws
 * @date 2024/6/12
 * @description: 集中存放 BeanDefinition
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition bd);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);
}
