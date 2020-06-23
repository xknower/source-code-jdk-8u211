package com.xknower.chromium.dom.internal;

/**
 * invoked by callback processing code.
 * 由回调处理代码调用。
 *
 * @author xknower
 */
public class JavaObject {
    /**
     * JS 调用
     *
     * @param innerHtml
     */
    public void onDomChanged(String innerHtml) {
        System.out.println("DOM node changed: " + innerHtml);
    }
}