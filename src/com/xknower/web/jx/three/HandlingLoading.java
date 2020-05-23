package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.DefaultLoadHandler;
import com.teamdev.jxbrowser.chromium.LoadParams;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * JxBrowser API provides functionality that you can use to handle loading
 * and decide whether specified URL should be loaded in Chromium engine or not.
 * The following example demonstrates how to register LoadHandler
 * and cancel navigation to all URL that starts with http://www.google:
 * <p>
 * If you run the example above, you should see a white screen
 * that indicates that the requested URL loading was suppressed because of our LoadHandler implementation.
 * <p>
 * This sample demonstrates how to cancel loading of a specific URL.
 */
public class HandlingLoading {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.setLoadHandler(new DefaultLoadHandler() {
            public boolean onLoad(LoadParams params) {
                // Cancel loading URL that starts with http://www.google
                return params.getURL().startsWith("http://www.google");
            }
        });
        browser.loadURL("http://www.google.com");
    }
}
