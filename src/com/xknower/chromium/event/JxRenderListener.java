package com.xknower.chromium.event;

import com.teamdev.jxbrowser.chromium.events.BrowserListener;
import com.teamdev.jxbrowser.chromium.events.RenderAdapter;
import com.teamdev.jxbrowser.chromium.events.RenderEvent;
import com.teamdev.jxbrowser.chromium.events.RenderListener;

import java.util.EventListener;

/**
 * @author xknower
 */
public class JxRenderListener extends RenderAdapter implements RenderListener, BrowserListener, EventListener {

    @Override
    public void onRenderCreated(RenderEvent event) {
    }

    @Override
    public void onRenderGone(RenderEvent event) {
//        System.out.println("Render process is gone:");
//        TerminationStatus terminationStatus = event.getTerminationStatus();
//        System.out.println("Termination Status: " + terminationStatus);
//
//        // If you refresh or load the same or another URL, the render process and Browser instance will be restored.
//        Browser browser = event.getBrowser();
//        // Restore Browser instance by loading the same URL
//        browser.loadURL(browser.getURL());
    }

    @Override
    public void onRenderUnresponsive(RenderEvent event) {
    }

    @Override
    public void onRenderResponsive(RenderEvent event) {
    }
}
