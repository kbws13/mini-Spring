package xyz.kbws.beans.factory.support;

import xyz.kbws.beans.factory.BeanFactory;
import xyz.kbws.beans.factory.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author kbws
 * @Date 2023/9/28
 * @Description: bean工厂的实现类
 */
public class DefaultListableBeanFactory implements BeanFactory {

    public final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
