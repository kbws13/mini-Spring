package xyz.kbws.beans;

/**
 * @Author kbws
 * @Date 2023/9/28
 * @Description: bean的包装类
 */
public class BeanWrapper {

    private Object wrapperInstance;

    public BeanWrapper(Object wrapperInstance){
        this.wrapperInstance = wrapperInstance;
    }

    private Class<?> wrappedClass;

    public Object getWrapperInstance() {
        return wrapperInstance;
    }

    public void setWrapperInstance(Object wrapperInstance) {
        this.wrapperInstance = wrapperInstance;
    }

    public Class<?> getWrappedClass() {
        return wrappedClass;
    }

    public void setWrappedClass(Class<?> wrappedClass) {
        this.wrappedClass = wrappedClass;
    }
}
