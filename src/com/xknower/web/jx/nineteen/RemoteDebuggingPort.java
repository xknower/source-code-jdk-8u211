package com.xknower.web.jx.nineteen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * JxBrowser provides functionality that allows you to use the Chrome Developer Tools remote debugging feature.
 * To enable this feature you must define the --remote-debugging-port Chromium switch by calling
 * the BrowserPreferences.setChromiumSwitches(String...) method before creating any Browser instance.
 * <p>
 * Once you configured JxBrowser to use a specified remote debugging port,
 * you can get a remote DevTools URL by calling the Browser.getRemoteDebuggingURL() method:
 */

/**
 * JxBrowser provides functionality that allows you to use the Chrome Developer Tools remote debugging feature.
 * To enable this feature you must define the --remote-debugging-port Chromium switch
 * by calling the BrowserPreferences.setChromiumSwitches(String...) method before creating any Browser instance.
 * <p>
 * Once you configured JxBrowser to use a specified remote debugging port,
 * you can get a remote DevTools URL by calling the Browser.getRemoteDebuggingURL() method:
 * <p>
 * BrowserPreferences.setChromiumSwitches("--remote-debugging-port=9222");
 * <p>
 * Browser browser = new Browser();
 * browser.loadURL("http://www.google.com");
 * String remoteDebuggingURL = browser.getRemoteDebuggingURL();
 * <p>
 * The remoteDebuggingURL you can load in another Browser instance to open Chrome Developer Tools page
 * that allows inspecting HTML, debugging JavaScript etc.
 * <p>
 * <p>
 * <p>
 * Note: Do not open the remote debugging URL in other web browser applications such as Mozilla FireFox, MS IE, etc.
 * This will lead to a native crash in Chromium DevTools web server.
 * The remote debugging feature is compatible only with the Chromium version that equals to the version used by JxBrowser library.
 * For example, if you use JxBrowser 6.21 based on Chromium 69.0.3497.12,
 * then you can open remote debugging URL in Google Chrome/Chromium 69.0.3497.12 only.
 * We recommend that you always load the remote debugging URL in another Browser instance instead of Google Chrome.
 * <p>
 * If you run the example above you will see two windows. One window displays Google web page, the other one displays Developer Tools:
 * <p>
 * This sample demonstrates how to use Chromium remote debugging feature.
 */
public class RemoteDebuggingPort {

    public static void main(String[] args) {


        // Specifies remote debugging port for remote Chrome Developer Tools.
        BrowserPreferences.setChromiumSwitches("--remote-debugging-port=9222");

        Browser browser1 = new Browser();
        BrowserView browserView1 = new BrowserView(browser1);

        // Gets URL of the remote Developer Tools web page for browser1 instance.
        String remoteDebuggingURL = browser1.getRemoteDebuggingURL();

        JFrame frame1 = new JFrame();
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame1.add(browserView1, BorderLayout.CENTER);
        frame1.setSize(700, 500);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);

        browser1.loadURL("http://www.google.com");

        // Creates another Browser instance and loads the remote Developer
        // Tools URL to access HTML inspector.
        Browser browser2 = new Browser();
        BrowserView browserView2 = new BrowserView(browser2);

        JFrame frame2 = new JFrame();
        frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame2.add(browserView2, BorderLayout.CENTER);
        frame2.setSize(700, 500);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);

        browser2.loadURL(remoteDebuggingURL);
    }
}
