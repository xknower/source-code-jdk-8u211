package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.LoadURLParams;

/**
 * To load web page by its URL and send some POST data, use the Browser.loadURL(LoadURLParams params) method.
 * The following code demonstrates how to load URL and send POST data in different formats using extra headers:
 */
public class LoadingURLWithPOST {
    public static void main(String[] args) {
        Browser browser = new Browser();

        // application/x-www-form-urlencoded
        browser.loadURL(new LoadURLParams("http://www.google.com", "myKey=myValue&myKey2=myValue2"));

        // text/plain
        browser.loadURL(new LoadURLParams("http://www.google.com", "Some Text...", "Content-Type: text/plain\n"));

        // application/json
        browser.loadURL(new LoadURLParams("http://www.google.com", "{\"title\":\"Hello\"}", "Content-Type: application/json\n"));
    }
}
