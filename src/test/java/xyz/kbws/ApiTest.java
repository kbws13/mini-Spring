package xyz.kbws;

import org.junit.Test;
import xyz.kbws.bean.UserService;
import xyz.kbws.bean.factory.BeanFactory;
import xyz.kbws.bean.factory.config.BeanDefinition;
import xyz.kbws.bean.factory.support.DefaultListableBeanFactory;

/**
 * @author kbws
 * @date 2024/6/19
 * @description:
 */
public class ApiTest {

    @Test
    public void test_BeanFactory() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.注册 Bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 3.第一次获取 Bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();

        // 4.第二次获取 Bean
        UserService userService_singleton = (UserService) beanFactory.getBean("userService");
        userService_singleton.queryUserInfo();
    }
}
