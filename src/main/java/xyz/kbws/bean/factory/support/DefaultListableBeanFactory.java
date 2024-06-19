package xyz.kbws.bean.factory.support;

import xyz.kbws.bean.BeanException;
import xyz.kbws.bean.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kbws
 * @date 2024/6/19
 * @description: 核心实现类
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();


    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeanException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeanException("No bean named " + beanName + " is defined");
        }
        return beanDefinition;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }
}
