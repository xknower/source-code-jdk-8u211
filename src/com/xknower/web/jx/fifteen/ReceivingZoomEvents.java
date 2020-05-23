package com.xknower.web.jx.fifteen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.ZoomService;
import com.teamdev.jxbrowser.chromium.events.ZoomEvent;
import com.teamdev.jxbrowser.chromium.events.ZoomListener;

/**
 * ZoomService allows you to register ZoomListener for receiving Zoom change events.
 * Every time when zoom level for a specified web page has been changed, the onZoomChanged event will be fired:
 */
public class ReceivingZoomEvents {
    public static void main(String[] args) {
        Browser browser = new Browser();

        // Listen to zoom changed events
        ZoomService zoomService = browser.getContext().getZoomService();
        zoomService.addZoomListener(new ZoomListener() {
            @Override
            public void onZoomChanged(ZoomEvent event) {
                System.out.println("event.getURL() = " + event.getURL());
                System.out.println("event.getZoomLevel() = " + event.getZoomLevel());
            }
        });
    }
}
