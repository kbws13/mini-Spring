package xyz.kbws.test.aspect;

import xyz.kbws.annotation.aop.*;

/**
 * @Author kbws
 * @Date 2023/10/2
 * @Description:
 */
@Aspect
public class LogAspect {
    /**
     * 配置切面
     */
    @Pointcut("public * xyz.kbws.test.service.*.*(..)")
    public void logPointCut(){};

    /**
     * 前置通知
     */
    @Before
    public void logBefore(){
        System.out.println("This is LogAspect before");
    }

    /**
     * 后置返回通知
     */
    @AfterReturning
    public void logAfter(){
        System.out.println("This is LogAspect After");
    }

    /**
     * 异常通知
     */
    @AfterThrowing
    public void logAfterThrowing(){
        System.out.println("This is LogAspect AfterThrowing");
    }
}
