package com.xknower.web.jx.fifteen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.ZoomService;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.ZoomEvent;
import com.teamdev.jxbrowser.chromium.events.ZoomListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * The sample demonstrates how to modify zoom level for a currently loaded web page.
 * Zoom level will be applied to the currently loaded web page only.
 * If you navigate to a different domain, its zoom level will be 100% until you modify it.
 */
public class ZoomingWebPage {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Listen to zoom changed events
        BrowserContext context = browser.getContext();
        ZoomService zoomService = context.getZoomService();
        zoomService.addZoomListener(new ZoomListener() {
            @Override
            public void onZoomChanged(ZoomEvent event) {
                System.out.println("event.getURL() = " + event.getURL());
                System.out.println("event.getZoomLevel() = " + event.getZoomLevel());
            }
        });

        // The zoom level is configured for each domain separately, so if you set zoom level for the a.com web page,
        // it will not be applied for the b.com web page.
        //
        // With JxBrowser you can zoom the web page content
        // using Browser.setZoomLevel(double zoomLevel), Browser.zoomIn(), Browser.zoomOut(), Browser.zoomReset() methods.
        // Please note that zoom level can be applied only for the completely loaded web page.
        // You need to wait until the web page is loaded completely and only then you can zoom it:
        //
        // Modify zoom level every time when main frame is loaded.
        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    event.getBrowser().setZoomLevel(2.0);
                }
            }
        });

        browser.loadURL("http://www.teamdev.com");
    }
}
