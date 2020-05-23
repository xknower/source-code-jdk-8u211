package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * To load a web page by its URL use the Browser.loadURL() method.
 * The following code demonstrates how to load http://www.google.com web page: browser.loadURL("http://www.google.com");
 * <p>
 * Same method can be used for loading a HTML file from a local file system.
 * Instead of URL you just need to provide absolute path to HTML file. For example: browser.loadURL("C:\\path\\index.html");
 * <p>
 * The web page will be loaded asynchronously, so there's no guarantee that Google web page will be loaded completely when the method returns.
 * <p>
 * This sample demonstrates how to create Browser instance, embed it into Swing BrowserView container,
 * display it in JFrame and navigate to the "www.google.com" web site.
 */
public class LoadingURL {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://www.baidu.com");
    }
}
