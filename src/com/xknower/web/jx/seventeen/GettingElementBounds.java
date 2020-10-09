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

/**
 * Sometimes it might be useful to get information about location and size of a specific HTML Element
 * on the loaded web page relative to the top-left of the viewport
 * of the current document (e.g. to highlight or capture it). The DOMElement.getBoundingClientRect() method returns
 * the size of an element and its position relative to the viewport.
 * It's equivalent of the Web API Element.getBoundingClientRect() JavaScript function.
 */
public class GettingElementBounds {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(view, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    Browser browser = event.getBrowser();
                    DOMDocument document = browser.getDocument();
                    DOMElement inputElement = document.findElement(By.id("lst-ib"));
                    Rectangle rect = inputElement.getBoundingClientRect();
                    System.out.println("rect = " + rect);
                }
            }
        });
        browser.loadURL("https://google.com");
    }
}
