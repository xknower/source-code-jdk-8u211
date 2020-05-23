package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * To get a string that represents HTML of loaded web page use Browser.getHTML() method.
 * You must call this method only when web page is loaded completely.
 * Otherwise you might receive incomplete HTML or empty string.
 * The following code demonstrates how to wait until web page is loaded completely and get its HTML:
 * <p>
 * This sample demonstrates how to load custom HTML string into
 * Browser component and display it.
 */
public class GettingHTML {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    System.out.println("HTML = " + event.getBrowser().getHTML());
                }
            }
        });
        browser.loadURL("http://www.teamdev.com");
    }
}
