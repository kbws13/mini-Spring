package xyz.kbws.beans.factory.config;

/**
 * @Author kbws
 * @Date 2023/9/28
 * @Description: 保存bean定义相关的信息
 */
public class BeanDefinition {
    /**
     * bean对应的全类名
     */
    private String beanClassName;
    /**
     * 是否懒加载
     */
    private boolean lazyInit = false;
    /**
     * 保存在IOC容器时的key值
     */
    private String factoryBeanName;

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }
}
