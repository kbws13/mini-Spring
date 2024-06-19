package xyz.kbws;

/**
 * @author kbws
 * @date 2024/6/19
 * @description: Bean 定义
 */
public class BeanDefinition {
    private Object bean;

    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }
}
