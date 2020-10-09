package com.xknower.chromium.dom;

import com.teamdev.jxbrowser.chromium.dom.DOMElement;

import java.awt.*;
import java.util.Map;

/**
 * DOMElement -> DOMNode -> SearchContext, DOMEventTarget
 *
 * @author xknower
 */
public class JxDOMElement extends JxDOMNode {

    private DOMElement domElement;

    private JxDOMElement(DOMElement domElement) {
        super(domElement);
        this.domElement = domElement;
    }

    public static JxDOMElement instance(DOMElement domElement) {
        return new JxDOMElement(domElement);
    }

    public DOMElement domElement() {
        return domElement;
    }

    //

    public String getAttribute(String attribute) {
        return domElement.getAttribute(attribute);
    }

    public boolean setAttribute(String attributeKey, String attributeValue) {
        return domElement.setAttribute(attributeKey, attributeValue);
    }

    public void removeAttribute(String attribute) {
        domElement.removeAttribute(attribute);
    }

    public boolean hasAttribute(String attribute) {
        return domElement.hasAttribute(attribute);
    }

    public Map<String, String> getAttributes() {
        return domElement.getAttributes();
    }

    /**
     * 获取 (元素) InnerHTML
     *
     * @return HTML内部文本 (不包含本节点文本)
     */
    public String getInnerHTML() {
        return domElement.getInnerHTML();
    }

    public boolean setInnerHTML(String innerHTML) {
        return domElement.setInnerHTML(innerHTML);
    }

    /**
     * 获取 (元素) InnerText
     *
     * @return HTML 非HTML标记的纯文本信息
     */
    public String getInnerText() {
        return domElement.getInnerText();
    }

    public boolean setInnerText(String innerText) {
        return domElement.setInnerText(innerText);
    }

    /**
     * 获取元素 Rectangle
     *
     * @return 元素的位置信息
     */
    public Rectangle getBoundingClientRect() {
        return domElement.getBoundingClientRect();
    }
}
