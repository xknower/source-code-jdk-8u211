package com.xknower.chromium.event;

import com.teamdev.jxbrowser.chromium.InputEventsHandler;

/**
 * 输入事件处理器
 *
 * @param <T> | KeyEvent 、MouseEvent
 * @author xknower
 */
public interface JxInputEventsHandler<T> extends InputEventsHandler<T> {
    /**
     * 处理器
     *
     * @param event
     * @return true 拦截该输入输入事件, false 不拦截, 该事件未处理
     */
    @Override
    boolean handle(T event);
}
