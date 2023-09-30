package xyz.kbws.web.servlet;

import java.util.Map;

/**
 * @Author kbws
 * @Date 2023/9/30
 * @Description: 存储视图名称和数据
 */
public class ModelAndView {
    /**
     * 视图名称
     */
    private String viewName;
    /**
     * 参数
     */
    private Map<String, ?> model;

    public ModelAndView(String viewName){
        this.viewName = viewName;
    }

    public ModelAndView(String viewName, Map<String, ?> model){
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void setModel(Map<String, ?> model) {
        this.model = model;
    }
}
