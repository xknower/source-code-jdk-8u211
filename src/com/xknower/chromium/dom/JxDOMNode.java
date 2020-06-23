package com.xknower.chromium.dom;

import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.dom.DOMNodeType;
import com.teamdev.jxbrowser.chromium.dom.DOMTextAreaElement;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.dom.internal.Element;
import com.teamdev.jxbrowser.chromium.dom.internal.Node;
import com.xknower.chromium.dom.event.JxDOMEventTarget;
import com.xknower.chromium.dom.internal.JxSearchContext;

import java.util.ArrayList;
import java.util.List;

/**
 * DOM 结点
 * <p>
 * DOMNode -> SearchContext, DOMEventTarget
 *
 * @author xknower
 */
public class JxDOMNode extends JxSearchContext {

    private DOMNode domNode;

    private JxDOMEventTarget jxDOMEventTarget;

    protected JxDOMNode(DOMNode domNode) {
        super(domNode);
        this.domNode = domNode;
        //
        this.jxDOMEventTarget = JxDOMEventTarget.instance(domNode);
    }

    public static JxDOMNode instance(DOMNode domNode) {
        if (domNode == null) {
            return null;
        }
        return new JxDOMNode(domNode);
    }

    public DOMNode domNode() {
        return domNode;
    }

    //

    /**
     * 结点名称 (HTML 元素名)
     *
     * @return HTML 元素名称
     */
    public String getNodeName() {
        return domNode.getNodeName();
    }

    /**
     * 结点的值 (元素没有值的返回空)
     */
    public String getNodeValue() {
        String a = domNode.getNodeValue();
        return a;
    }

    public void setNodeValue(String nodeValue) {
        domNode.setNodeValue(nodeValue);
    }

    /**
     * 结点 类型 (DOMNodeType)
     */
    public DOMNodeType getNodeType() {
        return domNode.getNodeType();
    }

    public JxDOMNode getParent() {
        return instance(domNode.getParent());
    }

    /**
     * 获取子结点
     */
    public List<JxDOMNode> getChildren() {
        List<JxDOMNode> result = new ArrayList<>();
        List<DOMNode> list = domNode.getChildren();
        if (list != null) {
            list.forEach(domNode -> result.add(instance(domNode)));
        }
        return result;
    }

    /**
     * 获取下一个兄弟结点
     */
    public JxDOMNode getNextSibling() {
        return instance(domNode.getNextSibling());
    }

    /**
     * 获取前一个兄弟结点
     */
    public JxDOMNode getPreviousSibling() {
        return instance(domNode.getPreviousSibling());
    }

    public void click() {
        domNode.click();
    }

    public boolean insertChild(JxDOMNode jxDOMNode, JxDOMNode jxDomNodeNew) {
        return domNode.insertChild(jxDOMNode.domNode, jxDomNodeNew.domNode);
    }

    public boolean replaceChild(JxDOMNode jxDOMNode, JxDOMNode jxDomNodeNew) {
        return domNode.replaceChild(jxDOMNode.domNode, jxDomNodeNew.domNode);
    }

    public boolean removeChild(JxDOMNode domNode) {
        return domNode.removeChild(domNode);
    }

    public boolean appendChild(JxDOMNode domNode) {
        return domNode.appendChild(domNode);
    }

    /**
     * 获取结点 (去除HTML文本内容) 文本内容
     */
    public String getTextContent() {
        return domNode.getTextContent();
    }

    public void setTextContent(String textContent) {
        domNode.setTextContent(textContent);
    }

    // JxDOMEventTarget

    public void addEventListener(DOMEventType domEventType, DOMEventListener domEventListener, boolean flag) {
        jxDOMEventTarget.addEventListener(domEventType, domEventListener, flag);
    }

    public void removeEventListener(DOMEventType domEventType, DOMEventListener domEventListener, boolean flag) {
        jxDOMEventTarget.removeEventListener(domEventType, domEventListener, flag);
    }

    public List<DOMEventListener> getEventListeners(DOMEventType domEventType) {
        return jxDOMEventTarget.getEventListeners(domEventType);
    }

    public boolean dispatchEvent(DOMEvent domEvent) {
        return jxDOMEventTarget.dispatchEvent(domEvent);
    }

    // 获取该结点下的所有结点

    public List<JxDOMNode> getAllChildren() {
        return getAllChildren(this, new ArrayList<>());
    }

    private List<JxDOMNode> getAllChildren(JxDOMNode nowDOMNode, List<JxDOMNode> result) {
        if (nowDOMNode != null) {
            nowDOMNode.getChildren().forEach(a -> {
                result.add(a);
                a.getAllChildren(a, result);
            });
        }
        return result;
    }

    @Override
    public String toString() {
        switch (JxDOMNodeType.domNodeType(getNodeType())) {
            case ElementNode:
                DOMElement element = ((DOMElement) this.domNode);
//                element.getAttributes().forEach((k, v) -> System.out.println("\t\t" + k + " -> " + v));
                break;
            case TextNode:
                JxDOMNode a = JxDOMNode.instance(domNode.getNextSibling());
                String b = "\t|" + (a != null ? a.toString() : "");
                System.out.println(b);
                break;
            case AttributeNode:
                System.out.println();
                break;
            default:
                return "";
        }

        return "JxDomNode: {\"Name\": " + getNodeName() + ", \"Value\":" +
                ("".equalsIgnoreCase(getNodeValue().trim()) ? "" : getNodeValue())
                + ",\"Type\": " + getNodeType() + ", \"Content\":" + "" + "}";
    }
}
