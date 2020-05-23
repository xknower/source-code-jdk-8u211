package com.xknower.web.jx.nineteen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * JxBrowser is based on Chromium engine. There are lots of command lines (switches) which can be used with Chromium.
 * Some change behavior of features, others are for debugging or experimenting.
 * <p>
 * <p>
 * JxBrowser provides API that allows configuring Chromium with command line switches.
 * The list of command line switches you can provide before you create any Browser instance. The following code demonstrates how to do it:
 * <p>
 * BrowserPreferences.setChromiumSwitches(
 * "--disable-web-security",
 * "--allow-file-access-from-files");
 * <p>
 * The following sample demonstrates how to disable web security and allow file access from HTML via appropriate Chromium switches:
 */

/**
 * The sample demonstrates how to enable access to local files from a web page.
 */
public class ChromiumSwitches {
    public static void main(String[] args) {
        BrowserPreferences.setChromiumSwitches("--disable-web-security", "--allow-file-access-from-files");

        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadHTML("<html><body>Image:<img src='file:///C:\\image.jpg'></body></html>");
    }
}
