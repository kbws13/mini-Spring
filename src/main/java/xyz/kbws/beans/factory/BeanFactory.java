package xyz.kbws.beans.factory;

/**
 * @Author kbws
 * @Date 2023/9/28
 * @Description: Spring的顶层接口
 */
public interface BeanFactory {
    /**
     * 通过bean的名字获取bean实例
     * @param beanName bean的名字
     * @return
     */
    Object getBean(String beanName);
}
