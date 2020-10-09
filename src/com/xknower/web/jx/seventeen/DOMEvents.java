package com.xknower.web.jx.seventeen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;

public class DOMEvents {
    public static void main(String[] args) {
        Browser browser = new Browser();
        DOMDocument document = browser.getDocument();

        // Each DOMNode implements DOMEventTarget interface that provides methods for registering DOM events.
        // You can register DOM listener to receive DOM events such as click, mousedown, mouseup, keydown, load, error etc.
        // The following sample demonstrates how to register click event listener for a document HTML element:
        DOMElement element = document.getDocumentElement();
        element.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
            @Override
            public void handleEvent(DOMEvent event) {
                // user clicked document element
            }
        }, false);

        // You can register DOM event listener only for the document of the loaded web page. After reloading the web page,
        // all the registered DOM event listeners will not work anymore, so you need to register the required DOM event listeners again.
        // Custom DOM Event Types
        // JxBrowser allows you to listen to the custom DOM Event types as well. The following code demonstrates
        // how to listen to the MyEvent DOM Events:
        DOMElement element1 = document.getDocumentElement();
        element1.addEventListener(new DOMEventType("MyEvent"), new DOMEventListener() {
            @Override
            public void handleEvent(DOMEvent event) {
            }
        }, false);
    }
}
