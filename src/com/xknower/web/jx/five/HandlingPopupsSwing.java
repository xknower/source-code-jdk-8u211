package com.xknower.web.jx.five;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.PopupContainer;
import com.teamdev.jxbrowser.chromium.PopupHandler;
import com.teamdev.jxbrowser.chromium.PopupParams;
import com.teamdev.jxbrowser.chromium.events.DisposeEvent;
import com.teamdev.jxbrowser.chromium.events.DisposeListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * To handle pop-up windows in your Swing application you have two options:
 * <p>
 * 1. Register default Swing pop-up handler implementation:
 * browser.setPopupHandler(new com.teamdev.jxbrowser.chromium.swing.DefaultPopupHandler());
 * <p>
 * 2. Register your own implementation where you decide how exactly you would like
 * to display a new pop-up window (e.g. in JFrame, JWindow, application tab):
 */
public class HandlingPopupsSwing {
    public static void main(String[] args) {
        Browser browser = new Browser();
        browser.setPopupHandler(new PopupHandler() {
            @Override
            public PopupContainer handlePopup(PopupParams params) {
                return new PopupContainer() {
                    @Override
                    public void insertBrowser(final Browser browser, final Rectangle initialBounds) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                BrowserView browserView = new BrowserView(browser);
                                browserView.setPreferredSize(initialBounds.getSize());

                                final JFrame frame = new JFrame("Popup");
                                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                                frame.add(browserView, BorderLayout.CENTER);
                                frame.pack();
                                frame.setLocation(initialBounds.getLocation());
                                frame.setVisible(true);
                                frame.addWindowListener(new WindowAdapter() {
                                    @Override
                                    public void windowClosing(WindowEvent e) {
                                        browser.dispose();
                                    }
                                });

                                browser.addDisposeListener(new DisposeListener<Browser>() {
                                    @Override
                                    public void onDisposed(DisposeEvent<Browser> event) {
                                        frame.setVisible(false);
                                    }
                                });
                            }
                        });
                    }
                };
            }
        });
    }
}
