package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.Charset;
import java.util.logging.Level;

/**
 * JxBrowser API provides functionality that allows accessing HTTP response data
 * such as HTML, plain text, JavaScript code, CSS, images, etc.
 * Using this functionality you can capture AJAX response body content with information about its mime type.
 * The following sample demonstrates how to use this functionality to access text/html data of each response:
 * <p>
 * This sample demonstrates how to capture response body of HTTP request.
 */
public class AccessingHTTPResponseData {
    public static void main(final String[] args) {
        LoggerProvider.setLevel(Level.OFF);

        BrowserContext browserContext = BrowserContext.defaultContext();
        NetworkService networkService = browserContext.getNetworkService();
        networkService.setNetworkDelegate(new DefaultNetworkDelegate() {
            @Override
            public void onDataReceived(DataReceivedParams params) {
                if (params.getMimeType().equals("text/html")) {
                    String data = new String(params.getData(),
                            Charset.forName("UTF-8"));
                    System.out.println("data = " + data);
                }
            }
        });

        Browser browser = new Browser(browserContext);
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("https://www.wikipedia.org/");
    }
}
