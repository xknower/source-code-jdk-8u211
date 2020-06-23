package com.xknower.chromium;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSObject;
import com.teamdev.jxbrowser.chromium.dom.*;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.xknower.chromium.dom.JxBy;
import com.xknower.chromium.dom.JxDOMDocument;
import com.xknower.chromium.dom.internal.JavaObject;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * DOM 元素 资源处理器
 *
 * @author xknower
 */
public class DOMElementResourceManager {

    /**
     * 新建元素结点
     * 1、查询结点
     * 2、创建新元素结点
     * 3、appendChild 新节点到查找的结点
     *
     * @param jxDOMDocument
     */
    private void creatingElementTextNode(JxDOMDocument jxDOMDocument) {
        DOMNode root = jxDOMDocument.findElement(JxBy.tagName("div"));
        DOMNode textNode = jxDOMDocument.createTextNode("Some text");
        DOMElement paragraph = jxDOMDocument.createElement("p");
        paragraph.appendChild(textNode);
        root.appendChild(paragraph);
    }

    /**
     * 注册, DOM 事件 监听处理器
     *
     * @param jxDOMDocument
     */
    private void domEvents(JxDOMDocument jxDOMDocument) {
        // Each DOMNode implements DOMEventTarget interface that provides methods for registering DOM events.
        // 每个DOMNode实现DOMEventTarget接口，该接口提供注册DOM事件的方法。
        // You can register DOM listener to receive DOM events such as click, mousedown, mouseup, keydown, load, error etc.
        // 您可以注册DOM监听器来接收DOM事件，如click、mousedown、mouseup、keydown、load、error等
        // The following sample demonstrates how to register click event listener for a document HTML element:
        // The following sample demonstrates how to register click event listener for a document HTML element:
        DOMElement element = jxDOMDocument.getDocumentElement();
        element.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
            @Override
            public void handleEvent(DOMEvent event) {
                // user clicked document element // 用户单击的文档元素
                String domEventDesc = String.format("[UIEvent : %s , MouseEvent : %s, KeyboardEvent: %s, Cancelable: %s,Bubbles:  %s ]" +
                                "[ DOMEventType : %s, DOMEventTarget : %s, CurrentDOMEventTarget : %s, DOMEventPhase : %s",
                        event.isUIEvent(), event.isMouseEvent(), event.isKeyboardEvent(),
                        event.isCancelable(), event.isBubbles(),
                        event.getType(), event.getTarget(), event.getCurrentTarget(), event.getEventPhase());
                System.out.println("事件监听 : " + domEventDesc);
            }
        }, false);

        // 自定义事件, 监听处理器
        // You can register DOM event listener only for the document of the loaded web page. After reloading the web page,
        // all the registered DOM event listeners will not work anymore, so you need to register the required DOM event listeners again.
        // 您只能为加载的网页的文档注册DOM事件侦听器。重新加载网页后，所有注册的DOM事件侦听器将不再工作，因此需要再次注册所需的DOM事件侦听器。
        // Custom DOM Event Types // 自定义DOM事件类型
        // Allows you to listen to the custom DOM Event types as well.
        // 还允许您监听自定义的DOM事件类型。
        // The following code demonstrates how to listen to the MyEvent DOM Events:
        // 下面的代码演示如何侦听MyEvent DOM事件：
        jxDOMDocument.getDocumentElement().addEventListener(new DOMEventType("MyEvent"), new DOMEventListener() {
            @Override
            public void handleEvent(DOMEvent event) {
                String domEventDesc = String.format("[UIEvent : %s , MouseEvent : %s, KeyboardEvent: %s, Cancelable: %s,Bubbles:  %s ]" +
                                "[ DOMEventType: %s, DOMEventTarget : %s, CurrentDOMEventTarget  %s, DOMEventPhase: %s",
                        event.isUIEvent(), event.isMouseEvent(), event.isKeyboardEvent(),
                        event.isCancelable(), event.isBubbles(),
                        event.getType(), event.getTarget(), event.getCurrentTarget(), event.getEventPhase());
                System.out.println("MyEvent 事件监听 : " + domEventDesc);
            }
        }, false);
    }

    /**
     * 查找元素
     *
     * @param jxDOMDocument
     */
    private void domElementAttributes(JxDOMDocument jxDOMDocument) {
        List<DOMElement> domElements = jxDOMDocument.findElements(JxBy.tagName("div"));
        for (DOMElement domElement : domElements) {
            // 获取 元素的位置信息
            Rectangle rect = domElement.getBoundingClientRect();
            System.out.println("domElement Rectangle : " + rect);
            // 获取元素属性信息
            Map<String, String> attributes = domElement.getAttributes();
            for (String attrName : attributes.keySet()) {
                System.out.println(attrName + " = " + attributes.get(attrName));
            }
        }
    }

    /**
     * This sample demonstrates how to get DOM Node at a specific point on the web page.
     * <p>
     * 此示例演示如何在网页上的特定点获取DOM节点。
     *
     * @param jxBrowser JxBrowser
     */
    private void findingNodeAtPoint(JxBrowser jxBrowser) {
        DOMNodeAtPoint nodeAtPoint = jxBrowser.getNodeAtPoint(10, 10);
        System.out.println("nodeAtPoint = " + nodeAtPoint);
    }

    //

    /**
     * Allows injecting custom style sheet (CSS) into every web page loaded in the Browser instance.
     * 允许将自定义样式表（CSS）注入加载到浏览器实例中的每个网页。
     * <p>
     * Note: the injected CSS won't override already defined CSS properties on the loaded web page.
     * 注意：注入的CSS不会覆盖加载的网页上已经定义的CSS属性。 (必须在网页渲染之前注入, 加载完成后注入请重新加载)
     *
     * @param jxBrowser
     */
    private void setCustomStyleSheet(JxBrowser jxBrowser) {
        jxBrowser.setCustomStyleSheet("h1 { font-size: 4em; } body { background-color: orange; }");
        //
        jxBrowser.reload();
    }

    /**
     * JS 调用执行 JAVA 代码, 数据从 JS 端传递到 JAVA 端
     *
     * @param jxBrowser JxBrowser
     */
    private void addScriptContextListener(JxBrowser jxBrowser) {
        jxBrowser.addScriptContextListener(new ScriptContextAdapter() {
            @Override
            public void onScriptContextCreated(ScriptContextEvent event) {
                Browser browser = event.getBrowser();
                JSObject window = browser.executeJavaScriptAndReturnValue("window").asObject();
                // 01、注入 JAVA 代码
                window.setProperty("java", new JavaObject());
                // 02、JS 调用 JAVA 代码
                browser.executeJavaScript("window.onload = function() {"
                        + "const element = document.getElementById('counter');\n"
                        + "const observer = new MutationObserver(\n"
                        + "    function(mutations) {\n"
                        + "        window.java.onDomChanged(element.innerHTML);\n"
                        + "    });\n"
                        + "const config = {childList: true};\n"
                        + "observer.observe(element, config); };");
            }
        });
    }
}
