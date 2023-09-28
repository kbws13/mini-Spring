package xyz.kbws.beans.factory.config;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author kbws
 * @Date 2023/9/28
 * @Description: 用于扫描bean
 */
public class BeanDefinitionReader {
    /**
     * 扫描出来的全类名
     */
    private List<String> registryBeanClasses = new ArrayList<>();

    public BeanDefinitionReader(String scanPackage) throws Exception{
        doScan(scanPackage);
    }

    /**
     * 扫描包下的类
     */
    public void doScan(String scanPackage) throws Exception{
        // 将包名转换成文件路径
        URL url = this.getClass().getResource("/"+scanPackage.replaceAll("\\.","/"));
        if (url == null){
            throw new Exception("包"+scanPackage+"不存在!");
        }
        File classPath = new File(url.getFile());
        for(File file : classPath.listFiles()){
            if (file.isDirectory()){
                doScan(scanPackage+"."+file.getName());
            }else {
                if (!file.getName().endsWith(".class")){
                    // 如果不是class文件就跳过
                    continue;
                }
                String className = scanPackage + "." + file.getName().replace(".class", "");
                registryBeanClasses.add(className);
            }
        }
    }

    /**
     * 将扫描到的类信息转化为BeanDefinition对象
     */
    public List<BeanDefinition> loadBeanDefinitions(){
        List<BeanDefinition> result = new ArrayList<>();
        try {
            for (String className : registryBeanClasses){
                Class<?> beanClass = Class.forName(className);
                if (beanClass.isInterface()){
                    // 如果是接口就跳过
                    continue;
                }
                result.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()), beanClass.getName()));

                Class<?>[] interfaces = beanClass.getInterfaces();
                for (Class<?> anInterface : interfaces){
                    result.add(doCreateBeanDefinition(anInterface.getName(), beanClass.getName()));
                }
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将类的信息转换为BeanDefinition
     */
    public BeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName){
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
    }

    /**
     * 将类名首字母小写
     */
    private String toLowerFirstCase(String simpleName){
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
