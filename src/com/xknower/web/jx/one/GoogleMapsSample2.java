package com.xknower.web.jx.one;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoogleMapsSample2 {

    public static final int MIN_ZOOM = 0;
    public static final int MAX_ZOOM = 21;

    /**
     * In map.html file default zoom value is set to 4.
     */
    private static int zoomValue = 4;

    public static void main(String[] args) {
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JButton zoomInButton = new JButton("Zoom In");
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (zoomValue < MAX_ZOOM) {
                    browser.executeJavaScript("map.setZoom(" + ++zoomValue + ")");
                }
            }
        });

        // To zoom in/out the map we can use map.setZoom() function.
        // This function can be invoked from Java side using JxBrowser API. For example:
        JButton zoomOutButton = new JButton("Zoom Out");
        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (zoomValue > MIN_ZOOM) {
                    browser.executeJavaScript("map.setZoom(" + --zoomValue + ")");
                }
            }
        });

        // To create and set a new marker on the map the google.maps.Marker object must be used.
        // The following code demonstrates how to set marker from Java code on the map:
        JButton setMarkerButton = new JButton("Set Marker");
        setMarkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.executeJavaScript("var myLatlng = new google.maps.LatLng(48.4431727,23.0488126);\n" +
                        "var marker = new google.maps.Marker({\n" +
                        "    position: myLatlng,\n" +
                        "    map: map,\n" +
                        "    title: 'Hello World!'\n" +
                        "});");
            }
        });

        JPanel toolBar = new JPanel();
        toolBar.add(zoomInButton);
        toolBar.add(zoomOutButton);
        toolBar.add(setMarkerButton);

        JFrame frame = new JFrame("map.html");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(toolBar, BorderLayout.SOUTH);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("D://code//multi-thread//src//com//xknower//web//jx//one//map.html");
    }
}
