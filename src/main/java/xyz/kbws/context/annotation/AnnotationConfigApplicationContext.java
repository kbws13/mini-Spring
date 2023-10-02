package xyz.kbws.context.annotation;

import xyz.kbws.annotation.core.ComponentScan;
import xyz.kbws.beans.factory.config.BeanDefinitionReader;
import xyz.kbws.context.support.AbstractApplicationContext;

/**
 * @Author kbws
 * @Date 2023/9/29
 * @Description: 基于注解作为配置的容器
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

    public AnnotationConfigApplicationContext(Class annotatedClass) throws Exception{
        //初始化父类
        super.reader = new BeanDefinitionReader(getScanPackage(annotatedClass));
        refresh();
    }

    @Override
    public void refresh() throws Exception {
        // 交给父类完成
        super.refresh();
    }

    /**
     * 获取@ComponentScan中的value值
     */
    public String getScanPackage(Class annotatedClass) throws Exception {
        // 判断是否有ComponentScan注解
        if (!annotatedClass.isAnnotationPresent(ComponentScan.class)) {
            throw new Exception("请为注解配置类加上@ComponentScan注解！");
        }
        ComponentScan componentScan = (ComponentScan) annotatedClass.getAnnotation(ComponentScan.class);
        return componentScan.value().trim();
    }
}
