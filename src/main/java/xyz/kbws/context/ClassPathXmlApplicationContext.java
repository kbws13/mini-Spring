package xyz.kbws.context;

import xyz.kbws.beans.BeanDefinition;
import xyz.kbws.beans.BeanException;
import xyz.kbws.beans.BeanFactory;
import xyz.kbws.beans.SimpleBeanFactory;
import xyz.kbws.beans.XmlBeanDefinitionReader;
import xyz.kbws.core.ClassPathXmlResource;
import xyz.kbws.core.Resource;

/**
 * @author kbws
 * @date 2024/6/10
 * @description: Context 负责整合容器的启动过程、读取外部配置、解析 Bean 定义、创建 BeanFactory
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

    BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        BeanFactory bf = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(bf);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = bf;
    }

    /**
     * 对外提供的获取 Bean 的方法
     * @param beanName
     * @return
     * @throws BeanException
     */
    @Override
    public Object getBean(String beanName) {
        try {
            return this.beanFactory.getBean(beanName);
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}
