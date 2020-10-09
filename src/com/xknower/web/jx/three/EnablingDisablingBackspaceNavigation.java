package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.DefaultLoadHandler;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * Note: Starting with Chromium 52, Backspace Navigation is disabled in the browser engine.
 * That means that backspace navigation is no longer available in JxBrowser 6.12 and higher.
 * <p>
 * By default navigation on Backspace and Shift+Backspace is enabled.
 * In order to disable navigation when user presses Backspace or Shift+Backspace use the following approach:
 * <p>
 * The sample demonstrates how to handle navigation on Backspace and
 * Shift+Backspace.
 */
public class EnablingDisablingBackspaceNavigation {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        browser.setLoadHandler(new DefaultLoadHandler() {
            @Override
            public boolean canNavigateOnBackspace() {
                return false;
            }
        });

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://www.baidu.com");
    }
}
