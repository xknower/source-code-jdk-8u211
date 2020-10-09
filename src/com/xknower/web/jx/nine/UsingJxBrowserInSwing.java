package com.xknower.web.jx.nine;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * JxBrowser supports Java Swing. To embed component that displays web pages
 * you need to create Browser instance and wrap it into com.teamdev.jxbrowser.chromium.swing.BrowserView component.
 * The BrowserView is a lightweight component that inherits javax.swing.JComponent.
 * It can be embedded into any Swing container such as JPanel, JWindow, JFrame, etc.
 * <p>
 * This sample demonstrates how to create Browser instance,
 * embed it into Swing BrowserView container, display it in JFrame and
 * navigate to the "www.google.com" web site.
 */
public class UsingJxBrowserInSwing {
    // Important for macOS: On macOS, Chromium engine is initialized in Java process
    // and it will not be uninitialized when last Browser instance is disposed.
    // Chromium engine must be disposed manually after all Browser instances are disposed
    // by using the public static void shutdown()method. This method is invoked asynchronously.
    // Once you shutdown Chromium engine, you cannot use it anymore.
    // Invoke this method only when Java application exits.
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://www.google.com");
    }
}
