package xyz.kbws.aop;

import java.io.PrintStream;

/**
 * @Author kbws
 * @Date 2023/10/2
 * @Description: AOP配置的封装对象
 */
public class AopConfig {
    /**
     * 切点
     */
    private String pointCut;

    /**
     * 前置通知
     */
    private String before;

    /**
     * 后置通知
     */
    private String afterReturn;

    /**
     * 异常通知
     */
    private String afterThrow;

    /**
     * 异常类型
     */
    private String afterThrowClass;

    /**
     * 切面类
     */
    private String aspectClass;

    public String getPointCut() {
        return pointCut;
    }

    public void setPointCut(String pointCut) {
        this.pointCut = pointCut;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfterReturn() {
        return afterReturn;
    }

    public void setAfterReturn(String afterReturn) {
        this.afterReturn = afterReturn;
    }

    public String getAfterThrow() {
        return afterThrow;
    }

    public void setAfterThrow(String afterThrow) {
        this.afterThrow = afterThrow;
    }

    public String getAfterThrowClass() {
        return afterThrowClass;
    }

    public void setAfterThrowClass(String afterThrowClass) {
        this.afterThrowClass = afterThrowClass;
    }

    public String getAspectClass() {
        return aspectClass;
    }

    public void setAspectClass(String aspectClass) {
        this.aspectClass = aspectClass;
    }
}
