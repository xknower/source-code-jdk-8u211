package com.xknower.web.jx.nine;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.InputEventsHandler;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Using the BrowserView.setKeyEventsHandler(InputEventsHandler<KeyEvent> handler) method you can decide
 * what keyboard events should be suppressed before they will be sent to Chromium engine.
 * The following example demonstrates how to disable the Ctrl+A shortcut:
 * <p>
 * This sample demonstrates how to register key events handler to handle/suppress
 * Ctrl+A key events (e.g. to prevent text selection).
 */
public class HandlingKeyboardEvents {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        view.setKeyEventsHandler(new InputEventsHandler<KeyEvent>() {
            @Override
            public boolean handle(KeyEvent event) {
                System.out.println(event);
                return event.isControlDown() && event.getKeyCode() == KeyEvent.VK_A;
            }
        });

        browser.loadURL("http://www.google.com");
    }
}
