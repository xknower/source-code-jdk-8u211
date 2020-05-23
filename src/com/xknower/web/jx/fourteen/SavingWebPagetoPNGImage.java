package com.xknower.web.jx.fourteen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.internal.LightWeightWidget;
import com.teamdev.jxbrowser.chromium.swing.internal.events.LightWeightWidgetListener;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import java.awt.Dimension;
import java.awt.Rectangle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * To take an image of the currently loaded web page you need to perform the steps below:
 * <p>
 * Create Browser instance.
 * Set the required Browser view size.
 * Load the required web page or HTML and wait until it is loaded completely.
 * Get java.awt.Image of the loaded web page.
 * The example below demonstrates how to perform all these steps:
 */

/**
 * Note: functionality that allows capturing image of loaded web page is available for Browser instance in LIGHTWEIGHT rendering mode only. When HEAVYWEIGHT rendering mode is used, web page's content is rendered via GPU directly onto a native window/surface embedded and displayed in Java Swing/JavaFX container.
 * <p>
 * <p>
 * <p>
 * Calculating Page Size
 * If you need to take screenshot of the whole web page including scrollable hidden parts and you don’t know dimension of the web page, then you need to calculate it using the following approach:
 * <p>
 * Java
 * JSValue documentHeight = browser.executeJavaScriptAndReturnValue(
 * "Math.max(document.body.scrollHeight, " +
 * "document.documentElement.scrollHeight, document.body.offsetHeight, " +
 * "document.documentElement.offsetHeight, document.body.clientHeight, " +
 * "document.documentElement.clientHeight);");
 * JSValue documentWidth = browser.executeJavaScriptAndReturnValue(
 * "Math.max(document.body.scrollWidth, " +
 * "document.documentElement.scrollWidth, document.body.offsetWidth, " +
 * "document.documentElement.offsetWidth, document.body.clientWidth, " +
 * "document.documentElement.clientWidth);");
 * <p>
 * final int scrollBarSize = 25;
 * int viewWidth = documentWidth.asNumber().getInteger() + scrollBarSize;
 * int viewHeight = documentHeight.asNumber().getInteger() + scrollBarSize;
 * In this code we use JavaScript and DOM API to get dimension of the loaded document.
 */
public class SavingWebPagetoPNGImage {
}

// Swing

/**
 * The sample demonstrates how to get screen shot of the web page
 * and save it as PNG image file.
 */
class HTMLToImageSample {
    public static void main(String[] args) throws Exception {
        final int viewWidth = 1024;
        final int viewHeight = 20000;
        // Disables GPU process and changes maximum texture size
        // value from default 16384 to viewHeight. The maximum texture size value
        // indicates the maximum height of the canvas where Chromium
        // renders web page's content. If the web page's height
        // exceeds the maximum texture size, the part of outsize the
        // texture size will not be drawn and will be filled with
        // black color.
        String[] switches = {
                "--disable-gpu",
                "--max-texture-size=" + viewHeight
        };
        BrowserPreferences.setChromiumSwitches(switches);

        // #1 Create LIGHTWEIGHT Browser instance.
        Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
        BrowserView view = new BrowserView(browser);

        // #2 Register LightWeightWidgetListener.onRepaint() to get
        // notifications about paint events. We expect that web page
        // will be completely rendered twice:
        // 1. When its size is updated to viewWidth x viewHeight.
        // 2. When HTML content is loaded and displayed.
        final CountDownLatch latch = new CountDownLatch(2);
        LightWeightWidget widget = (LightWeightWidget) view.getComponent(0);
        widget.addLightWeightWidgetListener(new LightWeightWidgetListener() {
            @Override
            public void onRepaint(Rectangle updatedRect, Dimension viewSize) {
                // Make sure that all view content has been repainted.
                if (viewSize.equals(updatedRect.getSize())) {
                    latch.countDown();
                }
            }
        });

        // #3 Set the required view size.
        browser.setSize(viewWidth, viewHeight);

        // #4 Load web page and wait until web page is loaded completely.
        Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
            @Override
            public void invoke(Browser browser) {
                browser.loadURL("https://teamdev.com/jxbrowser");
            }
        });

        // #5 Wait until Chromium renders web page content.
        latch.await(45, TimeUnit.SECONDS);

        // #6 Save java.awt.Image of the loaded web page into a PNG file.
        ImageIO.write((RenderedImage) widget.getImage(), "PNG",
                new File("teamdev.com.png"));

        // #7 Dispose Browser instance.
        browser.dispose();
    }
}

// JavaFX

/**
 * The sample demonstrates how to get screen shot of the web page
 * and save it as PNG image file.
 */
class JavaFXHTMLToImageSample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        final int viewWidth = 1024;
        final int viewHeight = 15000;
        // Disables GPU process and changes maximum texture size
        // value from default 16384 to viewHeight. The maximum texture size value
        // indicates the maximum height of the canvas where Chromium
        // renders web page's content. If the web page's height
        // exceeds the maximum texture size, the part of outsize the
        // texture size will not be drawn and will be filled with
        // black color.
        String[] switches = {
                "--disable-gpu",
                "--disable-gpu-compositing",
                "--enable-begin-frame-scheduling",
                "--max-texture-size=" + viewHeight
        };
        BrowserPreferences.setChromiumSwitches(switches);

        // #1 Create LIGHTWEIGHT Browser instance
        final Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
        final com.teamdev.jxbrowser.chromium.javafx.BrowserView view = new com.teamdev.jxbrowser.chromium.javafx.BrowserView(browser);

        // #2 Get javafx.scene.image.Image of the loaded web page
        final com.teamdev.jxbrowser.chromium.javafx.internal.LightWeightWidget widget = (com.teamdev.jxbrowser.chromium.javafx.internal.LightWeightWidget) view.getChildren().get(0);

        // #3 Register LightWeightWidgetListener.onRepaint() to get
        // notifications about paint events. We expect that web page
        // will be completely rendered twice:
        // 1. When its size is updated to viewWidth x viewHeight.
        // 2. When HTML content is loaded and displayed.
//        final CountDownLatch latch = new CountDownLatch(2);
//        widget.addLightWeightWidgetListener(new com.teamdev.jxbrowser.chromium.internal.LightWeightWidgetListener() {
//            @Override
//            public void onRepaint(Rectangle updatedRect, Dimension viewSize) {
//                if (viewSize.equals(updatedRect.getSize())) {
//                    latch.countDown();
//                }
//            }
//        });

        // #4 Set the required view size
        browser.setSize(viewWidth, viewHeight);

        // #5 Load web page and wait until web page is loaded completely
        Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
            @Override
            public void invoke(Browser browser) {
                browser.loadURL("https://teamdev.com/jxbrowser");
            }
        });

        // #6 Wait until Chromium renders web page content.
//        latch.await(45, TimeUnit.SECONDS);

        final Image image = widget.getImage();

        // #7 Save the image into a PNG file
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", new File("teamdev.com.png"));

        // #8 Dispose Browser instance.
        browser.dispose();

        // #9 Close the application.
        Platform.exit();
    }
}
