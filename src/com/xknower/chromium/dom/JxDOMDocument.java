package com.xknower.chromium.dom;

import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.dom.events.*;
import com.xknower.chromium.dom.internal.JxSearchContext;

/**
 * DOMDocument -> SearchContext
 * <p>
 * HTML DOM 树
 *
 * @author xknower
 */
public class JxDOMDocument extends JxSearchContext {

    private DOMDocument domDocument;

    private JxDOMDocument(DOMDocument domDocument) {
        super(domDocument);
        this.domDocument = domDocument;
    }

    /**
     * 创建 DOMDocument
     *
     * @param domDocument DOM文档
     * @return JxDOMDocument
     */
    public static JxDOMDocument instance(DOMDocument domDocument) {
        return new JxDOMDocument(domDocument);
    }

    //

    /**
     * 获取 DOMElement
     *
     * @return DOMElement
     */
    public DOMElement getDocumentElement() {
        return domDocument.getDocumentElement();
    }

    /**
     * 创建 DOMElement
     *
     * @param element DOMElement
     * @return
     */
    public DOMElement createElement(String element) {
        return domDocument.createElement(element);
    }

    public DOMNode createTextNode() {
        return domDocument.createTextNode();
    }

    public DOMNode createTextNode(String textNode) {
        return domDocument.createTextNode(textNode);
    }

    public DOMEvent createEvent(DOMEventType domEventType, DOMEventParams domEventParams) {
        return domDocument.createEvent(domEventType, domEventParams);
    }

    public DOMUIEvent createUIEvent(DOMEventType domEventType, DOMUIEventParams domEventParams) {
        return domDocument.createUIEvent(domEventType, domEventParams);
    }

    public DOMMouseEvent createMouseEvent(DOMEventType domEventType, DOMMouseEventParams domEventParams) {
        return domDocument.createMouseEvent(domEventType, domEventParams);
    }

    public DOMKeyEvent createKeyEvent(DOMEventType domEventType, DOMKeyEventParams domEventParams) {
        return domDocument.createKeyEvent(domEventType, domEventParams);
    }

}
