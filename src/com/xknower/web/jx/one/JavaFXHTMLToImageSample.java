package com.xknower.web.jx.one;

/*
 * Copyright (c) 2000-2017 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import com.teamdev.jxbrowser.chromium.javafx.internal.LightWeightWidget;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * The sample demonstrates how to get screen shot of the web page
 * and save it as PNG image file.
 */
public class JavaFXHTMLToImageSample extends Application {
    @Override
    public void start(final Stage primaryStage) throws Exception {
        // #1 Create Browser instance
        final Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
        final BrowserView view = new BrowserView(browser);

        // #2 Set the required view size
        browser.setSize(1280, 1024);

        // Wait until Chromium resizes view
        Thread.sleep(500);

        // #3 Load web page and wait until web page is loaded completely
        Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
            @Override
            public void invoke(Browser browser) {
                browser.loadURL("https://www.google.com");
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Wait until Chromium renders web page content
                    Thread.sleep(500);

                    // #4 Get javafx.scene.image.Image of the loaded web page
                    LightWeightWidget lightWeightWidget = (LightWeightWidget) view.getChildren().get(0);
                    Image image = lightWeightWidget.getImage();

                    // Save the image into a PNG file
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", new File("google.com.png"));
                } catch (Exception ignored) {
                } finally {
                    // Dispose Browser instance
                    browser.dispose();
                }
            }
        });

        // 如果需要截取整个网页（包括可滚动隐藏部分）的屏幕截图，而又不知道网页的尺寸，则需要使用以下方法进行计算:
        // If you need to take screenshot of the whole web page including scrollable hidden parts
        // and you don’t know dimension of the web page, then you need to calculate it using the following approach:
        JSValue documentHeight = browser.executeJavaScriptAndReturnValue("Math.max(document.body.scrollHeight, " +
                "document.documentElement.scrollHeight, document.body.offsetHeight, " +
                "document.documentElement.offsetHeight, document.body.clientHeight, " +
                "document.documentElement.clientHeight);");
        JSValue documentWidth = browser.executeJavaScriptAndReturnValue("Math.max(document.body.scrollWidth, " +
                "document.documentElement.scrollWidth, document.body.offsetWidth, " +
                "document.documentElement.offsetWidth, document.body.clientWidth, " +
                "document.documentElement.clientWidth);");

        final int scrollBarSize = 25;
        int viewWidth = (int) documentWidth.getNumberValue() + scrollBarSize;
        int viewHeight = (int) documentHeight.getNumberValue() + scrollBarSize;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
