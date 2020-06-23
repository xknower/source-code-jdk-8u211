package com.xknower.chromium.dom.event;

import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;

/**
 * DOMEventType
 *
 * @author xknower
 */
public enum JxDOMEventType {

    //
    OnClick("click"),
    //
    OnDoubleClick("dbclick"),
    //
    OnMouseDown("mousedown"),
    //
    OnMouseUp("mouseup"),
    //
    OnMouseOver("mouseover"),
    //
    OnMouseMove("mousemove"),
    //
    OnMouseOut("mouseout"),
    //
    OnKeyDown("keydown"),
    //
    OnKeyUp("keyup"),
    //
    OnKeyPress("keypress"),
    //
    OnLoad("load"),
    //
    OnUnload("unload"),
    //
    OnAbort("abort"),
    //
    OnError("error"),
    //
    OnResize("resize"),
    //
    OnScroll("scroll"),
    //
    OnSelect("select"),
    //
    OnChange("change"),
    //
    OnSubmit("submit"),
    //
    OnReset("reset"),
    //
    OnFocus("focus"),
    //
    OnBlur("blur");

    private final String value;

    JxDOMEventType(String value) {
        this.value = value;
    }

    public static JxDOMEventType Value(String value) {
        for (JxDOMEventType v : values()) {
            if (v.value.equalsIgnoreCase(value)) {
                return v;
            }
        }
        return null;
    }

    public String Value() {
        return this.value;
    }

    public DOMEventType keyEventType() {
        JxDOMEventType v = JxDOMEventType.Value(this.value);
        switch (v) {
            case OnClick:
                return DOMEventType.OnClick;
            case OnDoubleClick:
                return DOMEventType.OnDoubleClick;
            case OnMouseDown:
                return DOMEventType.OnMouseDown;
            case OnMouseUp:
                return DOMEventType.OnMouseUp;
            case OnMouseOver:
                return DOMEventType.OnMouseOver;
            case OnMouseMove:
                return DOMEventType.OnMouseMove;
            case OnMouseOut:
                return DOMEventType.OnMouseOut;
            case OnKeyDown:
                return DOMEventType.OnKeyDown;
            case OnKeyUp:
                return DOMEventType.OnKeyUp;
            case OnKeyPress:
                return DOMEventType.OnKeyPress;
            case OnLoad:
                return DOMEventType.OnLoad;
            case OnUnload:
                return DOMEventType.OnUnload;
            case OnAbort:
                return DOMEventType.OnAbort;
            case OnError:
                return DOMEventType.OnError;
            case OnResize:
                return DOMEventType.OnResize;
            case OnScroll:
                return DOMEventType.OnScroll;
            case OnSelect:
                return DOMEventType.OnSelect;
            case OnChange:
                return DOMEventType.OnChange;
            case OnSubmit:
                return DOMEventType.OnSubmit;
            case OnReset:
                return DOMEventType.OnReset;
            case OnFocus:
                return DOMEventType.OnFocus;
            case OnBlur:
                return DOMEventType.OnBlur;
            default:
                throw new IllegalArgumentException("Unsupported value: " + value);
        }
    }
}
