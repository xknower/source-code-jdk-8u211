package com.xknower.web.jx.thirteen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.Cookie;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Using Network API you can control all incoming and outgoing cookies.
 * You can enable/disable saving/sending cookies.
 * Using the NetworkDelegate.onCanSetCookies(String url, List<Cookie> cookies) method you can decide whether cookies should be saved or not.
 * To disable sending some cookies to a web server you can use the NetworkDelegate.onCanGetCookies(String url, List<Cookie> cookies) method.
 * <p>
 * The sample demonstrates how to suppress/filter incoming and outgoing cookies.
 */
public class AcceptingRejectingCookies {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        // Suppress/filter all incoming and outgoing cookies.
        browser.getContext().getNetworkService().setNetworkDelegate(new DefaultNetworkDelegate() {
            @Override
            public boolean onCanSetCookies(String url, List<Cookie> cookies) {
                return false;
            }

            @Override
            public boolean onCanGetCookies(String url, List<Cookie> cookies) {
                return false;
            }
        });

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
