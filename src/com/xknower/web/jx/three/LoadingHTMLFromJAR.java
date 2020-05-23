package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * The complete example you can find below:
 */
public class LoadingHTMLFromJAR {
    public static void main(String[] args) {
        final Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // JxBrowser allows loading HTML by URL, from local HTML file, from a String.
        // Very often Java application resources such as HTML files are located inside JAR archives included into application class path.
        // To be able to load resources located inside JAR archive you must register custom ProtocolHandler with the following implementation:
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

        // jar 包中 创建 index.html 资源文件
        // Assume that we need to load a resource related to this class in the JAR file
        browser.loadURL(LoadingHTMLFromJAR.class.getResource("index.html").toString());
    }

    /**
     * The getMimeType() method returns appropriate mime type for the given resource extension:
     *
     * @param path
     * @return
     */
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
