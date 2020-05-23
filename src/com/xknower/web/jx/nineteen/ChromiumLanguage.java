package com.xknower.web.jx.nineteen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * xBrowser allows configuring Chromium engine with specified language (two letter code from ISO-639 e.g. "en", "de", "it", etc).
 * The language will be used for UI text messages localization (e.g. text on the web page that's displayed when Chromium failed to load URL).
 * <p>
 * <p>
 * By default Chromium engine is configured to use Java application language that can be received from the user.language System Property.
 * To configure Chromium engine with Java application language, JxBrowser extracts the language from user.language System Property
 * and pass it to Chromium engine via the --lang Chromium switcher.
 * <p>
 * <p>
 * If you need to change this default behavior, then you can configure Chromium engine directly with specified language via Chromium --lang switcher.
 * Here's examples that show how Chromium engine will display localized text on the Navigation Failed web page:
 */
public class ChromiumLanguage {
    // Configure Chromium with English language (en)
    public static void main(String[] args) {
        BrowserPreferences.setChromiumSwitches("--lang=en");

        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setVisible(true);

        browser.loadURL("http://123.nonexisting.url");

        // Configure Chromium with German language (de):
//        BrowserPreferences.setChromiumSwitches("--lang=de");
//
//        Browser browser = new Browser();
//        BrowserView view = new BrowserView(browser);
//
//        JFrame frame = new JFrame();
//        frame.add(view, BorderLayout.CENTER);
//        frame.setSize(700, 500);
//        frame.setVisible(true);
//
//        browser.loadURL("http://123.nonexisting.url");
    }
}
