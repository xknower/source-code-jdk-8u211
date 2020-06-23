package com.xknower.chromium.dom.event;

import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventTarget;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;

import java.util.List;

/**
 * DOM 元素结点事件监听处理器
 *
 * @author xknower
 */
public class JxDOMEventTarget {

    private DOMEventTarget domEventTarget;

    protected JxDOMEventTarget(DOMEventTarget domEventTarget) {
        this.domEventTarget = domEventTarget;
    }

    public static JxDOMEventTarget instance(DOMEventTarget domEventTarget) {
        return new JxDOMEventTarget(domEventTarget);
    }

    //

    public void addEventListener(DOMEventType domEventType, DOMEventListener domEventListener, boolean flag) {
        domEventTarget.addEventListener(domEventType, domEventListener, flag);
    }

    public void removeEventListener(DOMEventType domEventType, DOMEventListener domEventListener, boolean flag) {
        domEventTarget.removeEventListener(domEventType, domEventListener, flag);
    }

    public List<DOMEventListener> getEventListeners(DOMEventType domEventType) {
        return domEventTarget.getEventListeners(domEventType);
    }

    public boolean dispatchEvent(DOMEvent domEvent) {
        return domEventTarget.dispatchEvent(domEvent);
    }
}
