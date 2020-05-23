package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.LoadHTMLParams;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.WebStorage;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.LoadEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

/**
 * HTML5 supports Web Storage API that allows browsers to store key/value pairs,
 * in a much more better way than using cookies. Web Storage API provides two mechanisms of storing data locally:
 * window.localStorage stores data with no expiration date.
 * window.sessionStorage stores data for one session (data is lost when the browser tab is closed).
 * <p>
 * JxBrowser provides API that allows working with both Local and Session storages. See the following methods:
 * Browser.getLocalWebStorage() returns Local web storage instance.
 * Browser.getSessionWebStorage() returns Session web storage instance.
 * <p>
 * Local and Session storages implement same WebStorage interface that provides methods for working with storage.
 * <p>
 * The sample demonstrates how to access WebStorage on
 * the loaded web page using JxBrowser API.
 */
public class HTML5LocalSessionStorages {
    public static void main(String[] args) {
        LoggerProvider.setLevel(Level.OFF);

        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Important: the Browser.getLocalWebStorage() method returns null if JavaScript hasn't accessed the localStorage variable.
        // The reason is that there's a lazy initialization of the local storage in Chromium engine.
        // You need access the localStorage variable in JavaScript to initialize local storage.
        // After that you can access it via JxBrowser Web Storage API. It's a limitation in the current implementation.
        // The following code snippet demonstrates how to workaround this limitation:
        // browser.addLoadListener(new LoadAdapter() {
        //    @Override
        //    public void onDocumentLoadedInMainFrame(LoadEvent event) {
        //        Browser browser = event.getBrowser();
        //        // Initialize WebStorage
        //        browser.executeJavaScript("localStorage");
        //
        //        // Access WebStorage
        //        WebStorage webStorage = browser.getLocalWebStorage();
        //        // Read and display the 'myKey' storage value.
        //        String itemValue = webStorage.getItem("myKey");
        //    }
        //});
        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onDocumentLoadedInMainFrame(LoadEvent event) {
                Browser browser = event.getBrowser();
                WebStorage webStorage = browser.getLocalWebStorage();
                // Read and display the 'myKey' storage value.
                System.out.println("The myKey value: " + webStorage.getItem("myKey"));
                // Modify the 'myKey' storage value.
                webStorage.setItem("myKey", "Hello from Local Storage");
            }
        });

        browser.loadHTML(new LoadHTMLParams(
                "<html><body><button onclick=\"myFunction()\">" +
                        "Modify 'myKey' value</button>" +
                        "<script>localStorage.myKey = \"Initial Value\";" +
                        "function myFunction(){alert(localStorage.myKey);}" +
                        "</script></body></html>",
                "UTF-8",
                "http://teamdev.com"));
    }
}
