package xyz.kbws.context;

import java.util.EventObject;

/**
 * @author kbws
 * @date 2024/6/12
 * @description: 用于监听事件
 */
public class ApplicationEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    public ApplicationEvent(Object arg0) {
        super(arg0);
    }

}
