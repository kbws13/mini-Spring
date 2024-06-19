package xyz.kbws.bean;

/**
 * @author kbws
 * @date 2024/6/19
 * @description: 定义 Bean 异常
 */
public class BeanException extends RuntimeException{

    public BeanException(String message) {
        super(message);
    }

    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }
}
