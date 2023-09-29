package xyz.kbws.test;

import xyz.kbws.context.ApplicationContext;
import xyz.kbws.context.annotation.AnnotationConfigApplicationContext;
import xyz.kbws.test.config.ApplicationConfig;

/**
 * 启动测试类
 */
public class ApplicationTest {
    public static void main(String[] args) throws Exception{
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        applicationContext.getBean("");
    }
}
