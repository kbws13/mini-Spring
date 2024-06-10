package xyz.kbws.test;

import xyz.kbws.context.ClassPathXmlApplicationContext;

/**
 * @author kbws
 * @date 2024/6/10
 * @description:
 */
public class Test1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService) ctx.getBean("aservice");
        aService.sayHello();
    }
}
