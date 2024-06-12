package xyz.kbws.context;

/**
 * @author kbws
 * @date 2024/6/12
 * @description: 用于发布事件
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
