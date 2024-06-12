package xyz.kbws.beans;

/**
 * @author kbws
 * @date 2024/6/12
 * @description: setter 注入属性类
 */
public class PropertyValue {

    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
