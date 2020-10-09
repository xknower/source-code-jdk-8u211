package com.xknower.web.jx.ten;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.ConsoleListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * Now, if JavaScript code on the loaded web page prints message
 * to Web Console via the console.log(), console.error(), console.info(), or console.warn() function,
 * then the ConsoleListener.onMessage() methods will be invoked.
 * <p>
 * The sample demonstrates how to listen to console messages including
 * JavaScript errors.
 */
public class ConsoleMessages {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // JxBrowser API allows receiving all output messages sent to the Web Console via console.log() JavaScript function.
        // You can listen to JavaScript Web Console messages with different levels: DEBUG, LOG, WARNING and ERROR.
        // To start receiving Web Console messages you must register ConsoleListener for a specified Browser instance. For example:
        browser.addConsoleListener(new ConsoleListener() {
            @Override
            public void onMessage(ConsoleEvent event) {
                System.out.println("Level: " + event.getLevel());
                System.out.println("Message: " + event.getMessage());
            }
        });
        browser.executeJavaScript("console.error(\"Error message\");");
    }
}
