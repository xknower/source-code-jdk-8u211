package com.xknower.chromium.dom.event;

import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventPhase;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventTarget;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;

/**
 * DOMEvent
 *
 * @author xknower
 */
public class JxDOMEvent {

    private DOMEvent domEvent;

    private JxDOMEvent(DOMEvent domEvent) {
        this.domEvent = domEvent;
    }

    public static JxDOMEvent instance(DOMEvent domEvent) {
        return new JxDOMEvent(domEvent);
    }

    //

    public DOMEventType getType() {
        return domEvent.getType();
    }

    public DOMEventTarget getTarget() {
        return domEvent.getTarget();
    }

    public DOMEventTarget getCurrentTarget() {
        return domEvent.getCurrentTarget();
    }

    public DOMEventPhase getEventPhase() {
        return domEvent.getEventPhase();
    }

    public boolean isUIEvent() {
        return domEvent.isUIEvent();
    }

    public boolean isMouseEvent() {
        return domEvent.isMouseEvent();
    }

    public boolean isKeyboardEvent() {
        return domEvent.isKeyboardEvent();
    }

    public boolean isBubbles() {
        return domEvent.isBubbles();
    }

    public boolean isCancelable() {
        return domEvent.isCancelable();
    }

    public void preventDefault() {
        domEvent.preventDefault();
    }

    public void stopPropagation() {
        domEvent.stopPropagation();
    }
}
