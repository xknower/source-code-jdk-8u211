package com.xknower.web.jx.two;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;

/**
 * JxBrowser provides functionality that allows you to enable or disable various features,
 * such as images, JavaScript, videos, etc., for each Browser instance.
 * Use BrowserPreferences class to work with Browser features/preferences.
 * To modify some features/preferences you must obtain BrowserPreferences instance using the Browser.getPreferences() method,
 * modify preferences, and save them using the Browser.setPreferences() method.
 * <p>
 * The following sample demonstrates how to disable loading images and JavaScript execution on www.google.com web page:
 */
public class BrowserPreference {

    public static void main(String[] args) {
        Browser browser = new Browser();

        // Gets the current Browser's preferences
        BrowserPreferences preferences = browser.getPreferences();
        preferences.setImagesEnabled(false);
        preferences.setJavaScriptEnabled(false);

        // Updates Browser's preferences
        browser.setPreferences(preferences);

        // Images and JavaScript will be disabled
        browser.loadURL("http://www.google.com/");
    }
}
