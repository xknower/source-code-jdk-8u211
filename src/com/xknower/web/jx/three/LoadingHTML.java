package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * To load HTML content from a string use Browser.loadHTML() method. For example:
 * browser.loadHTML("<html><body><h1>Load HTML Sample</h1></body></html>");
 * <p>
 * The output will look like this:
 * <p>
 * This sample demonstrates how to load custom HTML string into
 * Browser component and display it.
 */
public class LoadingHTML {
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
                    System.out.println("HTML is loaded.");
                }
            }
        });
        browser.loadHTML("<html><body><h1>Load HTML Sample</h1></body></html>");
    }
}
