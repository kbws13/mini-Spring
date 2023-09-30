package xyz.kbws.web.servlet;

import xyz.kbws.annotation.Controller;
import xyz.kbws.annotation.RequestMapping;
import xyz.kbws.context.annotation.AnnotationConfigApplicationContext;
import xyz.kbws.context.support.AbstractApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author kbws
 * @Date 2023/9/30
 * @Description: Spring MVC入口类
 */
public class DispatcherServlet extends HttpServlet {

    private final String LOCATION = "contextConfigLocation";

    private List<HandlerMapping> handlerMappings = new ArrayList<>();

    private Map<HandlerMapping, HandlerAdapter> handlerAdapters = new HashMap<>();

    private List<ViewResolver> viewResolvers = new ArrayList<>();

    private AbstractApplicationContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            // 初始化IOC容器
            context = new AnnotationConfigApplicationContext(Class.forName(config.getInitParameter(LOCATION)));
        }catch (Exception e){
            e.printStackTrace();
        }
        // 初始化Spring MVC九大组件
        initStrategies(context);
    }

    /**
     * 初始化Spring MVC九大组件
     */
    protected void initStrategies(AbstractApplicationContext context){

    }

    /**
     * 初始化九大组件的方法，由于是mini-spring，只对映射器、视图解析器和适配器做初始化
     */
    private void initMultipartResolver(AbstractApplicationContext context) {}
    private void initLocaleResolver(AbstractApplicationContext context) {}
    private void initThemeResolver(AbstractApplicationContext context) {}
    private void initHandlerExceptionResolvers(AbstractApplicationContext context) {}
    private void initRequestToViewNameTranslator(AbstractApplicationContext context) {}
    private void initFlashMapManager(AbstractApplicationContext context) {}

    /**
     * 初始化映射器
     */
    private void initHandlerMappings(AbstractApplicationContext context){
        // 从IOC容器中获取所有实例
        List<String> beanNames = new ArrayList<>(context.beanDefinitionMap.keySet());

        for (String beanName : beanNames){
            // 获取当前bean实例
            Object controller = context.getBean(beanName);
            Class<?> clazz = controller.getClass();

            // 如果没有被@Controller标记则跳过
            if (!clazz.isAnnotationPresent(Controller.class)){
                continue;
            }

            // 如果该类中被@RequestMapping标记，则需要获取请求地址的前缀
            String baseUrl = "";
            if (clazz.isAnnotationPresent(RequestMapping.class)){
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                baseUrl = requestMapping.value();
            }

            // 扫描所有public类型的方法
            Method[] methods = clazz.getMethods();
            for (Method method : methods){
                // 如果该方法没有被@RequestMapping标记则跳过
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                String regex = "/" + baseUrl + requestMapping.value()
                        .replaceAll("\\*", "*")
                        .replaceAll("/+", "+");
                Pattern pattern = Pattern.compile(regex);
                this.handlerMappings.add(new HandlerMapping(controller, method, pattern));
            }
        }
    }

    /**
     * 初始化适配器
     */
    private void initHandlerAdapters(AbstractApplicationContext context){
        for (HandlerMapping handlerMapping : handlerMappings){
            this.handlerAdapters.put(handlerMapping, new HandlerAdapter());
        }
    }

    /**
     * 初始化视图解析器
     */
    private void initViewResolvers(AbstractApplicationContext context){
        // 默认扫描"/templates"目录
        String templateRoot = "/templates";
        String templateRootPath = this.getClass().getResource(templateRoot).getFile();

        File templateRootDir = new File(templateRootPath);

        Arrays.asList(templateRootDir.listFiles()).forEach(
                iter -> this.viewResolvers.add(new ViewResolver(templateRoot))
        );
    }

    @Override
    protected void service(HttpServletRequest servletRequest, HttpServletResponse response) throws IOException{

    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response)
            throws IOException, InvocationTargetException, IllegalAccessException{
        // 根据用户请求获取映射器
        HandlerMapping handler = getHandler(request);
        if (handler == null){
            processDispatchResult(request,response, new ModelAndView("404"));
            return;
        }
        // 获取适配器
        HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
        // 调用对应的方法
        ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);
        // 渲染并响应给用户
        processDispatchResult(request, response, modelAndView);
    }

    /**
     * 获取映射器
     */
    private HandlerMapping getHandler(HttpServletRequest request){
        if (this.handlerMappings.isEmpty()){
            return null;
        }
        String url = request.getRequestURI();

        for (HandlerMapping handlerMapping : handlerMappings){
            Matcher matcher = handlerMapping.getPattern().matcher(url);
            if (matcher.matches()){
                return handlerMapping;
            }
        }
        return null;
    }

    /**
     * 获取适配器
     */
    private HandlerAdapter getHandlerAdapter(HandlerMapping handlerMapping){
        if (this.handlerAdapters.isEmpty()) {
            return null;
        }
        // 获取映射器相对应的适配器
        HandlerAdapter handlerAdapter = this.handlerAdapters.get(handlerMapping);
        if (handlerAdapter.supports(handlerMapping)) {
            return handlerAdapter;
        }
        return null;
    }

    /**
     * 渲染并响应给用户
     */
    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws IOException {
        if (modelAndView == null || this.viewResolvers.isEmpty()) {
            return;
        }
        for (ViewResolver viewResolver : viewResolvers) {
            View view = viewResolver.resolveViewName(modelAndView.getViewName());
            if (view != null) {
                view.render(modelAndView.getModel(), request, response);
                return;
            }
        }
    }

}
