package xyz.kbws.test;

import xyz.kbws.context.ApplicationContext;
import xyz.kbws.context.annotation.AnnotationConfigApplicationContext;
import xyz.kbws.test.config.ApplicationConfig;
import xyz.kbws.test.service.TestService;

/**
 * 测试启动类
 */
public class ApplicationTest {
    public static void main(String[] args) throws Exception{
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        TestService testService = (TestService) applicationContext.getBean("testService");
        testService.echo();
    }
}
