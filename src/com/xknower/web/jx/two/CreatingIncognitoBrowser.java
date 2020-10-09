package com.xknower.web.jx.two;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;
import com.teamdev.jxbrowser.chromium.StorageType;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * 创建隐身浏览器
 * <p>
 * This sample demonstrates how to configure Browser instance
 * to use in-memory data storage.
 * <p>
 * By default, each Browser instance stores all user data such as history, cookies, cache on disk.
 * Since 6.8 you can configure Browser instance to store all user data in memory (Chromium's "Incognito" mode),
 * so that all user data will be cleared once your Java application is terminated.
 * <p>
 * The following example demonstrates how to configure Browser instance to work in "Incognito" mode
 * and store all user data in memory:
 */
public class CreatingIncognitoBrowser {
    public static void main(String[] args) {
        // No user data will be stored to the "user-data-dir" folder.
        // This directory will be used for internal purposes
        // on macOS and Linux platforms.
        BrowserContextParams params = new BrowserContextParams("user-data-dir");
        params.setStorageType(StorageType.MEMORY);

        BrowserContext browserContext = new BrowserContext(params);
        Browser browser = new Browser(browserContext);
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://google.com");
    }
}
