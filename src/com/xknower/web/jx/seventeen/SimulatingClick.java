package com.xknower.web.jx.seventeen;

import com.teamdev.jxbrowser.chromium.Browser;

import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

// <span class="fr-marker"data-id="0"data-type="false"style="display: none; line-height: 0;"></span><span class="fr-marker"data-id="0"data-type="true"style="display: none; line-height: 0;"></span>
/**
 * JxBrowser DOM API provides functionality that allows you to simulate click on any HTML element on the loaded web page. To simulate click you need to make sure that web page is loaded completely and find the required HTML element in document:
 *
 * Java
 * DOMDocument document = browser.getDocument();
 * DOMElement link = document.findElement(By.tagName("button"));
 * Once you get the reference to the required HTML element, you can simulate click using the DOMNode.click() method.
 *
 * link.click();
 * Please note that this method works asynchronously. When this method returns, it doesn't mean that click simulation is finished.
 *
 * This functionality can be useful in automated testing when you need to simulate user actions including mouse clicks on specified HTML elements.
 */

/**
 * This sample demonstrates how to simulate click on a specific HTML element.
 */
public class SimulatingClick {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    Browser browser = event.getBrowser();
                    DOMDocument document = browser.getDocument();
                    DOMElement link = document.findElement(By.tagName("button"));
                    if (link != null) {
                        link.click();
                    }
                }
            }
        });
        browser.loadHTML("<html><body><button id='button' " +
                "onclick=\"alert('Button has been clicked!');\">Click Me!</button>" +
                "</body></html>");
    }
}