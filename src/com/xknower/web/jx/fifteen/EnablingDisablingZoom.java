package com.xknower.web.jx.fifteen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * By default, zoom functionality is enabled in JxBrowser.
 * You can zoom the web page using the Browser.zoomIn(), Browser.zoomOut(), Browser.zoomReset(), Browser.setZoomLevel() methods
 * or using touch gestures in the environment with touch screen. If you would like to disable zoom for specific Browser instance
 * and don't let the end user to zoom the web page loaded in this Browser instance, then you can use the Browser.setZoomEnabled() method.
 * <p>
 * Once you call the browser.setZoomEnabled(false) method in the example above,
 * the Chromium engine will ignore all attempts to change zoom level programmatically via JxBrowser Zoom API
 * and using touch gestures on a touch screen device. If zoom level of the loaded the web page has been already changed,
 * the Browser.setZoomEnabled(false) function call will reset zoom to 100% and disable zoom functionality.
 */
public class EnablingDisablingZoom {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Disabling zoom for the browser instance.
        browser.setZoomEnabled(false);

        browser.loadURL("https://google.com");
    }
}
