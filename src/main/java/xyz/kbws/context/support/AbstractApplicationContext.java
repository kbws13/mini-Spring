package xyz.kbws.context.support;

import xyz.kbws.annotation.*;
import xyz.kbws.beans.BeanWrapper;
import xyz.kbws.beans.factory.config.BeanDefinition;
import xyz.kbws.beans.factory.config.BeanDefinitionReader;
import xyz.kbws.beans.factory.support.DefaultListableBeanFactory;
import xyz.kbws.context.ApplicationContext;

import java.lang.reflect.Field;
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
       BeanDefinition beanDefinition = super.beanDefinitionMap.get(beanName);
       try {
           // 通过beanDefinition实例化bean
           Object instance = instantiateBean(beanDefinition);
           if (instance == null){
               return null;
           }
           // 将实例化后的bean使用BeanWrapper包装
            BeanWrapper beanWrapper = new BeanWrapper(instance);
           this.factoryBeanInstanceCache.putIfAbsent(beanDefinition.getBeanClassName(), beanWrapper);
           // 开始注入操作
           populateBean(instance);
       }catch (Exception e){
           e.printStackTrace();
       }
       return null;
    }

    /**
     * 通过beanDefinition实例化bean
     */
    private Object instantiateBean(BeanDefinition beanDefinition){
        Object instance = null;
        String className = beanDefinition.getBeanClassName();
        try {
            // 先判断单例池中是否存在该类的实例
            if (this.factoryBeanInstanceCache.containsKey(className)){
                instance = this.factoryBeanObjectCache.get(className);
            }else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.factoryBeanObjectCache.put(beanDefinition.getFactoryBeanName(), instance);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 开始注入操作
     */
    public void populateBean(Object instance){
        Class clazz = instance.getClass();
        // 判断是否有Controller、Service、Component、Repository等注解标记
        if (!(clazz.isAnnotationPresent(Component.class) ||
                clazz.isAnnotationPresent(Controller.class) ||
                clazz.isAnnotationPresent(Service.class) ||
                clazz.isAnnotationPresent(Repository.class))){
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            // 如果没有属性被Autowired标记，则跳过
            if (!field.isAnnotationPresent(Autowired.class)){
                continue;
            }

            String autowiredBeanName = field.getType().getName();

            field.setAccessible(true);

            try {
                field.set(instance, this.factoryBeanInstanceCache.get(autowiredBeanName).getWrapperInstance());
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }
}
