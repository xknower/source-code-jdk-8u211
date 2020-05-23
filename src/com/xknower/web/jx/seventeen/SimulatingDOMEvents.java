package com.xknower.web.jx.seventeen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventParams;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * Starting from JxBrowser version 6.13, JxBrowser DOM Events API got extended with new methods and classes,
 * which allow creating and triggering DOM events at the specified HTML element.
 * JxBrowser supports the Event, UIEvent, MouseEvent, and KeyEvent DOM events.
 * To dispatch an event use the DOMEventTarget.dispatchEvent(DOMEvent event) method.
 * <p>
 * The following sample demonstrates how to simulate a custom event for a DOM node and handle it in JxBrowser.
 * <p>
 * The sample demonstrates how to programatically simulate a custom event for a DOM node.
 */
public class SimulatingDOMEvents {

    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    DOMDocument document = event.getBrowser().getDocument();
                    DOMEventType eventType = new DOMEventType("MyEvent");
                    DOMEvent myEvent = document.createEvent(eventType, new DOMEventParams());
                    DOMNode root = document.findElement(By.id("root"));

                    root.addEventListener(eventType, new DOMEventListener() {
                        @Override
                        public void handleEvent(DOMEvent event) {
                            if (event.getType().equals(eventType)) {
                                System.out.println("MyEvent received successfully");
                            }
                        }
                    }, false);

                    root.dispatchEvent(myEvent);
                }
            }
        });

        browser.loadHTML("<html><body><div id='root'>Some text</div></body></html>");
    }
}