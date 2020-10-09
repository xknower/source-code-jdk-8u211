package com.xknower.web.jx.nine;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * The example below demonstrates how to embed Swing BrowserView into JTabbedPane.
 * The BrowserView can be embedded into JTabbedPane in both rendering modes: lightweight and heavyweight.
 * In this example the default (heavyweight) rendering mode is used.
 *
 * The sample demonstrates how to use Browser in JTabbedPane.
 */
public class JTabbedPaneSimple {
    public static void main(String[] args) {
        Browser browserOne = new Browser();
        Browser browserTwo = new Browser();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Browser One", new BrowserView(browserOne));
        tabbedPane.addTab("Browser Two", new BrowserView(browserTwo));

        JFrame frame = new JFrame();
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browserOne.loadURL("https://www.google.com");
        browserTwo.loadURL("https://www.teamdev.com");
    }
}
