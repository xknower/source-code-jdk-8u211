package com.xknower.web.jx.two;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.TerminationStatus;
import com.teamdev.jxbrowser.chromium.events.RenderAdapter;
import com.teamdev.jxbrowser.chromium.events.RenderEvent;

/**
 * 渲染过程事件
 * <p>
 * Each Browser instance is running in a separate native process where the web page is rendered.
 * Sometimes this process can exit unexpectedly because of the crash in plugin.
 * To receive notifications about unexpected render process termination you can use RenderListener.
 * When you receive notification about render process termination you can display a "sad" icon like Google Chrome does,
 * for example, to inform the user that this particular Browser component has crashed.
 */
public class RenderProcessEvents {

    public static void main(String[] args) {
        Browser browser = new Browser();
        browser.addRenderListener(new RenderAdapter() {
            @Override
            public void onRenderCreated(RenderEvent event) {
                System.out.println("Render process is created.");
            }

            @Override
            public void onRenderGone(RenderEvent event) {
                System.out.println("Render process is gone:");
                TerminationStatus terminationStatus = event.getTerminationStatus();
                System.out.println("Termination Status: " + terminationStatus);
            }
        });

        // If you refresh or load the same or another URL, the render process and Browser instance will be restored. Example:
        browser.addRenderListener(new RenderAdapter() {
            @Override
            public void onRenderGone(RenderEvent event) {
                Browser browser = event.getBrowser();
                // Restore Browser instance by loading the same URL
                browser.loadURL(browser.getURL());
            }
        });

    }
}
