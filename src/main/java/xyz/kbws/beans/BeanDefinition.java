package xyz.kbws.beans;

/**
 * @author kbws
 * @date 2024/6/10
 * @description: Bean 定义
 */
public class BeanDefinition {

    private String id;

    private String className;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
