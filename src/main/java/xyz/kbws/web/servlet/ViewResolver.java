package xyz.kbws.web.servlet;

import java.io.File;

/**
 * @Author kbws
 * @Date 2023/9/30
 * @Description: 模板名称和模板解析引擎的匹配
 */
public class ViewResolver {
    /**
     * 默认解析的文件后缀
     */
    private final String DEFAULT_TEMPLATE_SUFFIX = ".html";

    /**
     * 模板文件的目录
     */
    private File templateRootDir;

    /**
     * 视图名称
     */
    private String viewName;

    public ViewResolver(String templateRoot){
        String templateRootPath = this.getClass().getResource(templateRoot).getFile();
        this.templateRootDir = new File(templateRootPath);
    }

    /**
     * 解析视图名称
     */
    public View resolveViewName(String viewName){
        this.viewName = viewName;
        if (viewName == null || "".equals(viewName.trim())){
            return null;
        }
        // 如果viewName没有加上后缀则加上
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX) ? viewName : viewName + DEFAULT_TEMPLATE_SUFFIX;
        // 获取对应的模板文件
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+", "/"));
        // 通过模板文件返回视图对象
        return new View(templateFile);
    }
}
