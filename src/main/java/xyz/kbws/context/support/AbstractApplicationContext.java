package xyz.kbws.context.support;

import xyz.kbws.beans.BeanWrapper;
import xyz.kbws.beans.factory.config.BeanDefinition;
import xyz.kbws.beans.factory.config.BeanDefinitionReader;
import xyz.kbws.beans.factory.support.DefaultListableBeanFactory;
import xyz.kbws.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author kbws
 * @Date 2023/9/29
 * @Description: 容器抽象类(暂时只实现IOC功能)
 */
public abstract class AbstractApplicationContext extends DefaultListableBeanFactory implements ApplicationContext {

    protected BeanDefinitionReader reader;

    /**
     * 保存单例对象
     */
    private Map<String, Object> factoryBeanObjectCache = new HashMap<>();

    /**
     * 保存包装类对象
     */
    private Map<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    @Override
    public void refresh() throws Exception {
        // 扫描需要的包，把相关的类转化为beanDefinition
        List<BeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        // 注册，将beanDefinition放入IOC容器中存储
        doRegisterBeanDefinition(beanDefinitions);
        // 将非懒加载的类初始化
        doAutowired();
    }

    /**
     * 将beanDefinition放入IOC容器中存储
     */
    private void doRegisterBeanDefinition(List<BeanDefinition> beanDefinitions) throws Exception{
        for (BeanDefinition beanDefinition : beanDefinitions){
            if (super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception(beanDefinition.getFactoryBeanName() + "已经存在");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
    }

    /**
     * 将非懒加载的类初始化
     */
    private void doAutowired(){
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()){
            String beanName = beanDefinitionEntry.getKey();
            if (beanDefinitionEntry.getValue().isLazyInit()){
                getBean(beanName);
            }
        }
    }

    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
