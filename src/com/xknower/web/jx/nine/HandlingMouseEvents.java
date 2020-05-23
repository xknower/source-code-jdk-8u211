package com.xknower.web.jx.nine;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.InputEventsHandler;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Using the BrowserView.setMouseEventsHandler(InputEventsHandler<MouseEvent> handler) method you can decide
 * what mouse events should be suppressed before they will be sent to Chromium engine. The following example demonstrates
 * how to suppress mouse wheel events:
 * <p>
 * This sample demonstrates how to register mouse events handler
 * to handle/suppress mouse wheel events.
 */
public class HandlingMouseEvents {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        view.setMouseEventsHandler(new InputEventsHandler<MouseEvent>() {
            @Override
            public boolean handle(MouseEvent event) {
                return event.getID() == MouseEvent.MOUSE_WHEEL;
            }
        });

        browser.loadURL("http://www.google.com");
    }
}
