package com.xknower.chromium.dom;

import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.dom.DOMNodeAtPoint;

import java.awt.*;

/**
 * @author xknower
 */
public class JxDOMNodeAtPoint {

    private DOMNodeAtPoint domNodeAtPoint;

    private JxDOMNodeAtPoint(DOMNodeAtPoint domNodeAtPoint) {
        this.domNodeAtPoint = domNodeAtPoint;
    }

    public static JxDOMNodeAtPoint instance(DOMNodeAtPoint domNodeAtPoint) {
        return new JxDOMNodeAtPoint(domNodeAtPoint);
    }

    //

    public DOMNode getNode() {
        return domNodeAtPoint.getNode();
    }

    public Point getLocalPoint() {
        return domNodeAtPoint.getLocalPoint();
    }

    public DOMElement getURLElement() {
        return domNodeAtPoint.getURLElement();
    }

    public String getAbsoluteImageURL() {
        return domNodeAtPoint.getAbsoluteImageURL();
    }

    public String getAbsoluteLinkURL() {
        return domNodeAtPoint.getAbsoluteLinkURL();
    }
}
