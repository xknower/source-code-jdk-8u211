package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * This implementation cancels loading of all images.
 * Using same technique you can implement and register your own ResourceHandler
 * where you can cancel loading of required resources.
 * <p>
 * This sample demonstrates how to handle all resources such as
 * HTML, PNG, JavaScript, CSS files and decide whether web browser
 * engine should load them from web server or not. For example, in
 * this sample we cancel loading of all Images.
 */
public class HandlingResourcesLoading {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Using ResourceHandler you can determine whether resources
        // such as HTML, Images, JavaScript & CSS files, favicon, etc.
        // should be loaded or not. By default all resources are loaded.
        // To modify default behavior you need to register your own ResourceHandler implementation. For example:
        NetworkService networkService = browser.getContext().getNetworkService();
        networkService.setResourceHandler(new ResourceHandler() {
            @Override
            public boolean canLoadResource(ResourceParams params) {
                System.out.println("URL: " + params.getURL());
                System.out.println("Type: " + params.getResourceType());
                boolean isNotImageType = params.getResourceType() != ResourceType.IMAGE;
                if (isNotImageType) {
                    return true;    // Cancel loading of all images
                }
                return false;
            }
        });

        browser.loadURL("http://www.baidu.com");
    }
}
