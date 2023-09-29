package xyz.kbws.context;

import xyz.kbws.beans.factory.BeanFactory;

/**
 * @Author kbws
 * @Date 2023/9/29
 * @Description: 容器顶层接口
 */
public interface ApplicationContext extends BeanFactory {
    void refresh() throws Exception;
}
