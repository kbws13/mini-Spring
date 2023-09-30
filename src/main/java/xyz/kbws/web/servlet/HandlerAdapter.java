package xyz.kbws.web.servlet;

import xyz.kbws.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author kbws
 * @Date 2023/9/30
 * @Description: 完成列表参数与Method实参的对应关系
 */
public class HandlerAdapter {
    /**
     * 判断Handler是否属于HandlerMapping
     */
    public boolean supports(Object handler){
        return handler instanceof HandlerMapping;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws InvocationTargetException, IllegalAccessException{
        HandlerMapping handlerMapping = (HandlerMapping) handler;

        // 方法的形参列表
        Map<String, Integer> paramMapping = new HashMap<>();

        // 处理被@RquestParam标记的参数
        Annotation[][] paramsAnnotations = handlerMapping.getMethod().getParameterAnnotations();
        for (int i=0; i<paramsAnnotations.length; i++){
            for (Annotation paramAnnotation : paramsAnnotations[i]){
                if (paramAnnotation instanceof RequestParam){
                    String paramName = ((RequestParam) paramAnnotation).value();
                    if (!"".equals(paramName.trim())){
                        paramMapping.put(paramName, i);
                    }
                }
            }
        }

        // 处理HttpServletRequest和HttpServletResponse参数
        Class<?>[] paramTypes = handlerMapping.getMethod().getParameterTypes();
        for (int i=0;i < paramTypes.length; i++){
            Class<?> type = paramTypes[i];
            if (type == HttpServletRequest.class || type == HttpServletResponse.class){
                paramMapping.put(type.getName(), i);
            }
        }

        // 用户通过URL传递过来的参数列表
        Map<String, String[]> requestParamMap = request.getParameterMap();

        // 实参列表
        Object[] paramsValues = new Object[paramTypes.length];

        // 设置由@RequestParam标记的参数
        for (Map.Entry<String, String[]> param : requestParamMap.entrySet()){
            String value = Arrays.toString(param.getValue()).replace("\\[|\\]","");
            // 如果方法的形参列表中不存在该参数则跳过
            if (!paramMapping.containsKey(param.getKey())){
                continue;
            }
            // 获取该参数在形参列表中的下标
            int index = paramMapping.get(param.getKey());
            // 参数类型转换
            paramsValues[index] = caseStringValue(value, paramTypes[index]);
        }

        // 设置request参数
        if (paramMapping.containsKey(HttpServletRequest.class.getName())){
            paramsValues[paramMapping.get(HttpServletRequest.class.getName())] = response;
        }

        // 执行目标方法
        Object result = handlerMapping.getMethod().invoke(handlerMapping.getController());

        if (handlerMapping.getMethod().getReturnType() == ModelAndView.class){
            return (ModelAndView) result;
        }

        return null;
    }

    /**
     * 参数类型转换
     */
    private Object caseStringValue(String value, Class<?> clazz){
        if (clazz == String.class){
            return value;
        }
        if (clazz == Integer.class || clazz == int.class){
            return Integer.valueOf(value);
        }
        return null;
    }
}
