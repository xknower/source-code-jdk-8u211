package com.xknower.web.jx.one;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * Google Maps
 * <p>
 * With JxBrowser you can embed and use Google Maps in your Java Desktop application.
 * In this case JxBrowser represents kind of layer between your Java application and Google Maps.
 * To embed Google Maps in your Java application you need to create map.html file that initializes and displays the map,
 * create and embed Browser component,
 * load the map.html file and communicate with loaded map using JxBrowser API and Google Maps JavaScript API.
 * <p>
 * The following steps describes in more details how to embed Google Maps,
 * display some location, zoom in/out the map and set a new marker from Java code.
 * <p>
 * First you need to create HTML file where you embed Google Maps into HTML document.
 * Follow the instruction in Google Maps Tutorial to find out how to embed Google Maps into HTML document.
 * <p>
 * To use this file replace API_KEY with your Google API key.
 * See the instruction about how to obtain API key.
 * Now let’s create a simple Java application,
 * configure it to use JxBrowser library and embed Browser component to display this map.html file:
 * <p>
 * The code of our map.html file is the following:
 */
public class GoogleMapsSample {
    public static void main(String[] args) {
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame("map.html");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("D://code//multi-thread//src//com//xknower//web//jx//one//map.html");
    }
}
