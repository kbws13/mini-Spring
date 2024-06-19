package xyz.kbws;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kbws
 * @date 2024/6/19
 * @description: Bean 工厂
 */
public class BeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public Object getBean(String name) {
        return beanDefinitionMap.get(name).getBean();
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }
}
