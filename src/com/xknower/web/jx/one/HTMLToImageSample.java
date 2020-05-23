package com.xknower.web.jx.one;

/*
 * Copyright (c) 2000-2017 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.internal.LightWeightWidget;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;

/**
 * 截屏
 *
 * JxBrowser library allows you to take a screenshot of any web page including hidden scrollable area
 * and save it as an image file. This functionality can be used:
 * <p>
 * In a web service that provides image of a web page by its URL
 * For automated tests when you need to compare screenshots of the same web page to find regression and changes.
 * We recommend that you take a look at Quick Start Guides where you can find detailed instruction about
 * how to download and configure JxBrowser,
 * include it into your Java project using your favorite IDE and run your first program.
 * <p>
 * To take a screenshot of a specified web page you need to follow the steps below:
 * <p>
 * Create Browser instance.
 * Set the required Browser view size.
 * Load required web page by its URL or HTML and wait until it is loaded completely.
 * Get java.awt.Image of the loaded web page.
 * <p>
 * Important: Chromium engine has limitation as for the maximum web page size that can be captured.
 * The maximum page height is 16384 pixels.
 * <p>
 * The example below demonstrates how to perform all these steps:
 * <p>
 * The sample demonstrates how to get screen shot of the web page
 * and save it as PNG image file.
 */
public class HTMLToImageSample {
    public static void main(String[] args) throws Exception {
        // #1 Create Browser instance
        Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
        BrowserView view = new BrowserView(browser);

        // #2 Set the required view size
        browser.setSize(1280, 1024);

        // Wait until Chromium resizes view
        Thread.sleep(500);

        // #3 Load web page and wait until web page is loaded completely
        Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
            @Override
            public void invoke(Browser browser) {
                browser.loadURL("https://www.baidu.com");
            }
        });

        // Wait until Chromium renders web page content
        Thread.sleep(500);

        // #4 Get java.awt.Image of the loaded web page.
        LightWeightWidget lightWeightWidget = (LightWeightWidget) view.getComponent(0);
        Image image = lightWeightWidget.getImage();
        ImageIO.write((RenderedImage) image, "PNG", new File("teamdev.com.png"));

        // Dispose Browser instance
        browser.dispose();
    }
}
