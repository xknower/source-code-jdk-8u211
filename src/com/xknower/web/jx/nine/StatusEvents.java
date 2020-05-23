package com.xknower.web.jx.nine;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.events.StatusEvent;
import com.teamdev.jxbrowser.chromium.events.StatusListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

/**
 * When user moves cursor over a link, Chromium engine displays its URL in status bar.
 * JavaScript code can change text in status bar programmatically via window.status property as well.
 * JxBrowser API provides functionality that allows you to get notifications about status bar text changes.
 * The following sample demonstrates how to use this functionality:
 * <p>
 * The sample demonstrates how to listen to status change events.
 */
public class StatusEvents {
    public static void main(String[] args) {
        LoggerProvider.getIPCLogger().setLevel(Level.SEVERE);
        BrowserContext contextA = new BrowserContext(new BrowserContextParams("C:\\my-data1"));
        Browser browser = new Browser(contextA);
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addStatusListener(new StatusListener() {
            @Override
            public void onStatusChange(StatusEvent event) {
                System.out.println("Text = " + event.getText());
            }
        });

        browser.loadURL("http://www.google.com");
    }
}
