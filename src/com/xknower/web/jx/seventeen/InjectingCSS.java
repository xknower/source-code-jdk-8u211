package com.xknower.web.jx.seventeen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * Since 6.14 version JxBrowser provides API that allows injecting custom style sheet (CSS) into every web page loaded
 * in the Browser instance. The following example demonstrates how to use this functionality:
 * <p>
 * Note: the injected CSS won't override already defined CSS properties on the loaded web page.
 * <p>
 * To reset injected CSS call the browser.setCustomStyleSheet("") method.
 */
public class InjectingCSS {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.setCustomStyleSheet("body { background-color: orange; }");
        browser.loadURL("about:blank");
    }
}
