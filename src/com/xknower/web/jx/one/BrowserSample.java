package com.xknower.web.jx.one;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * Cross-Desktop Apps of Swing/AWT
 */
public class BrowserSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame("JxBrowser");
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setVisible(true);

        browser.loadURL("http://www.google.com");
    }
}
