package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.Callback;

/**
 * In some cases (e.g. automation testing) you might need to block current thread execution
 * and wait until web page is loaded completely. JxBrowser API provides functionality that allows doing it.
 * <p>
 * The following sample code demonstrates how to load http://www.google.com web page
 * and wait until it's loaded completely:
 */
public class LoadingWaiting {
    public static void main(String[] args) {
        Browser browser = new Browser();

        // Blocks current thread execution and waits until http://www.google.com web page is loaded completely
        // Note: use this method for loading web pages only.
        Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
            @Override
            public void invoke(Browser value) {
                value.loadURL("http://www.baidu.com");
            }
        });
    }
}
