package xyz.kbws.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author kbws
 * @Date 2023/9/30
 * @Description: 视图对象
 */
public class View {
    /**
     * 内容类型
     */
    public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";

    /**
     * 模板文件
     */
    private File viewFile;

    public View(File viewFile){
        this.viewFile = viewFile;
    }

    /**
     * 解析模板文件，将model注入到view中，并response给用户
     */
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws IOException{
        RandomAccessFile ra = new RandomAccessFile(this.viewFile, "r");
        StringBuffer sb = new StringBuffer();

        // 对模板文件逐行读取，当有符合${}包裹的变量时，将其替换掉
        try {
            String line = null;
            while ((line = ra.readLine()) != null){
                // 避免出现乱码
                line = new String(line.getBytes("ISO-8859-1"), "utf-8");
                // 使用正则表达式查找每一行中由${}包裹的变量
                Pattern pattern = Pattern.compile("\\$\\{[^\\}]+\\}", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()){
                    // 获取变量名
                    String paramName = matcher.group();
                    paramName = paramName.replaceAll("\\$\\{|\\}","");
                    // 查找model中对应的变量
                    Object paramValue = model.get(paramName);
                    if (paramValue == null){
                        continue;
                    }
                    line = matcher.replaceFirst(makeStringForRegExp(paramValue.toString()));
                    matcher = pattern.matcher(line);
                }
                sb.append(line);
            }
        }finally {
            ra.close();
        }
        // 通过response响应给用户
        response.setContentType(DEFAULT_CONTENT_TYPE);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(sb.toString());
    }

    /**
     * 处理特殊字符串
     */
    public static String makeStringForRegExp(String string) {
        return string
                .replace("\\", "\\\\")
                .replace("*", "\\")
                .replace("+", "\\+")
                .replace("|", "\\|")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("^", "\\^")
                .replace("$", "\\$")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("?", "\\?")
                .replace(",", "\\,")
                .replace(".", "\\.")
                .replace("&", "\\&");
    }
}
