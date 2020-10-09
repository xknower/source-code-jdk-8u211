package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.*;

/**
 * The LoadListener provides methods for receiving browser load events.
 * You can use this listener to get notifications about web page loading events,
 * document availability events, loading failure events etc
 * . The following code demonstrates how to register LoadListener:
 */
public class LoadingEvents {
    public static void main(String[] args) {
        Browser browser = new Browser();

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onStartLoadingFrame(StartLoadingEvent event) {
                if (event.isMainFrame()) {
                    System.out.println("Main frame has started loading");
                }
            }

            @Override
            public void onProvisionalLoadingFrame(ProvisionalLoadingEvent event) {
                if (event.isMainFrame()) {
                    System.out.println("Provisional load was committed for a frame");
                }
            }

            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    System.out.println("Main frame has finished loading");
                    System.out.println(browser.getHTML());
                }
            }

            @Override
            public void onFailLoadingFrame(FailLoadingEvent event) {
                NetError errorCode = event.getErrorCode();
                if (event.isMainFrame()) {
                    System.out.println("Main frame has failed loading: " + errorCode);
                }
            }

            @Override
            public void onDocumentLoadedInFrame(FrameLoadEvent event) {
                System.out.println("Frame document is loaded.");
            }

            @Override
            public void onDocumentLoadedInMainFrame(LoadEvent event) {
                System.out.println("Main frame document is loaded.");
            }
        });

        browser.loadURL("http://www.baidu.com");
        System.out.println(browser.getHTML());
    }
}
