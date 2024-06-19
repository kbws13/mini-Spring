package xyz.kbws.bean.factory.config;

/**
 * @author kbws
 * @date 2024/6/19
 * @description: Bean 定义
 */
public class BeanDefinition {

    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
