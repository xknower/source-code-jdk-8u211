package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;

public class CustomProtocolHandler {

    /**
     * JxBrowser 6.11 and higher provides the API that allows you to handle URL requests for standard (e.g. HTTP, HTTPS, FTP, etc.)
     * and non-standard (e.g. JAR, MYPROTOCOL, etc.) protocols.
     * The following example demonstrates how to register protocol handler for standard HTTPS protocol and response with custom data:
     */
    private static void v1() {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        BrowserContext browserContext = browser.getContext();
        ProtocolService protocolService = browserContext.getProtocolService();
        protocolService.setProtocolHandler("https", new ProtocolHandler() {
            @Override
            public URLResponse onRequest(URLRequest request) {
                URLResponse response = new URLResponse();
                String html = "<html><body><p>Hello there!</p></body></html>";
                response.setData(html.getBytes());
                response.getHeaders().setHeader("Content-Type", "text/html");
                return response;
            }
        });

        browser.loadURL("https://google.com/");
    }

    /**
     * You can use the same way to handle custom non-standard protocols (e.g. "teamdev"). For example:
     */
    private static void v2() {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        BrowserContext browserContext = browser.getContext();
        ProtocolService protocolService = browserContext.getProtocolService();
        protocolService.setProtocolHandler("teamdev", new ProtocolHandler() {
            @Override
            public URLResponse onRequest(URLRequest request) {
                URLResponse response = new URLResponse();
                String html = "<html><body><p>Hello there!</p></body></html>";
                response.setData(html.getBytes());
                response.getHeaders().setHeader("Content-Type", "text/html");
                return response;
            }
        });

        browser.loadURL("teamdev://custom-request/");
    }

    /**
     * It is also possible to register a custom protocol handler for JAR protocol. It allows you to load HTML files directly from JAR libraries included into your application class path:
     */
    private static void v3() {
        final Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        BrowserContext browserContext = browser.getContext();
        ProtocolService protocolService = browserContext.getProtocolService();
        protocolService.setProtocolHandler("jar", new ProtocolHandler() {
            @Override
            public URLResponse onRequest(URLRequest request) {
                try {
                    URLResponse response = new URLResponse();
                    URL path = new URL(request.getURL());
                    InputStream inputStream = path.openStream();
                    DataInputStream stream = new DataInputStream(inputStream);
                    byte[] data = new byte[stream.available()];
                    stream.readFully(data);
                    response.setData(data);
                    String mimeType = getMimeType(path.toString());
                    response.getHeaders().setHeader("Content-Type", mimeType);
                    return response;
                } catch (Exception ignored) {
                }
                return null;
            }
        });

        // Assume that we need to load a resource related to this class in the JAR file
        browser.loadURL(CustomProtocolHandler.class.getResource("index.html").toString());
    }

    private static String getMimeType(String path) {
        if (path.endsWith(".html")) {
            return "text/html";
        }
        if (path.endsWith(".css")) {
            return "text/css";
        }
        if (path.endsWith(".js")) {
            return "text/javascript";
        }
        return "text/html";
    }
}
