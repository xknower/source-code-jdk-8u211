package com.xknower.web.jx.two;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.DisposeEvent;
import com.teamdev.jxbrowser.chromium.events.DisposeListener;

public class DisposingBrowser {
    public static void main(String[] args) {
        // When you don't need to use a Browser instance you must dispose it using the Browser.dispose() method.
        Browser browser = new Browser();
//        browser.dispose();

        // Accessing disposed instance
        // Once you dispose a Browser instance, you cannot use it anymore.
        // If you try to access already disposed Browser instance the IllegalStateException exception will be thrown.
        // For example:
//        browser.dispose();
        browser.getDocument(); // IllegalStateException will be thrown

        // To check if the Browser instance is disposed or not, you can use the Browser.isDisposed() method.
        browser.isDisposed();

        // Dispose events
        // Each Browser instance can be also disposed from JavaScript via the window.close() function.
        // In this case you might be interested in receiving notification when a Browser instance is disposed.
        // To get such notifications, you can use DisposeListener.
        // For example:
        browser.addDisposeListener(new DisposeListener<Browser>() {
            @Override
            public void onDisposed(DisposeEvent<Browser> event) {
                System.out.println(event);
                // B<span class="fr-marker" data-id="0" data-type="false" style="display: none; line-height: 0;"></span><span class="fr-marker" data-id="0" data-type="true" style="display: none; line-height: 0;"></span>rowser is disposed
            }
        });

        browser.dispose();

        // When you dispose a Browser instance manually via the Browser.dispose() method, the Dispose event will also be fired.
    }
}
