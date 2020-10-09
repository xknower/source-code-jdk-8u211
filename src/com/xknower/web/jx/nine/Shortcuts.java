package com.xknower.web.jx.nine;

import static java.awt.event.KeyEvent.KEY_RELEASED;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.InputEventsHandler;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * In Chromium, there are two types of shortcuts. The shortcuts that are supported by the Chromium application
 * and shortcuts that are supported by the Blink engine. JxBrowser supports only the second type of shortcuts
 * because it does not integrate the Chromium application UI.
 * <p>
 * For example, JxBrowser supports shortcuts related to the editor like Ctrl+A/⌘+A, Ctrl+C/⌘+C, and Ctrl+X/⌘+X.
 * JxBrowser does not support Ctrl+P/⌘+P and Ctrl+F/⌘+F shortcuts as they are provided by the Chromium application UI.
 * <p>
 * Processing unsupported shortcuts
 * If you need to process an unsupported shortcut such as Ctrl+P/⌘+P,
 * you can do it manually by registering the input listener like in the following sample:
 */
public class Shortcuts {
    public static void main(String[] args) {
        final Browser browser = new Browser();
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
                if (event.isControlDown() && event.getKeyCode() == KeyEvent.VK_P
                        && event.getID() == KEY_RELEASED) {
                    browser.print();
                }
                return false;
            }
        });

        browser.loadURL("http://www.google.com");
    }
}
