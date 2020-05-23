package com.xknower.web.jx.nine;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import javafx.application.Application;

import javax.swing.*;
import java.awt.*;

import com.teamdev.jxbrowser.chromium.BrowserCore;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.internal.Environment;
import com.teamdev.jxbrowser.chromium.javafx.internal.LightWeightWidget;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Since 6.0 version JxBrowser supports two rendering modes: heavyweight (default mode) and lightweight.
 * Drag and drop functionality is supported in both heavyweight and lightweight rendering modes, but for each rendering mode it works differently.
 * <p>
 * Lightweight Rendering Mode
 * In lightweight rendering mode Drag and Drop functionality is implemented using Java Swing and JavaFX Drag&Drop API.
 * Drag and Drop support is limited. It supports only the following features:
 * <p>
 * Drag selected text and links on loaded web page and drop them into other applications.
 * Drag selected text and links on loaded web page and drop them into text fields or text areas on the same web page.
 * Drag text from other applications and drop it into text fields or text areas on loaded web page.
 * <p>
 * Heavyweight Rendering Mode
 * In heavyweight rendering mode Drag and Drop functionality is implemented by Chromium engine.
 * Chromium handles all Drag and Drop operations, so it works exactly as in Google Chromium.
 * <p>
 * Disabling Drag and Drop
 * By default Drag and Drop is enabled. To disable Drag and Drop use the BrowserView.setDragAndDropEnabled(boolean enabled) method
 * or the jxbrowser.chromium.dnd.enabled=false System Property.
 */
public class DragDrop {
}

/**
 * The sample demonstrates how to disable functionality that allows dragging/dropping content from/onto the loaded web page.
 * <p/>
 * By default Drag&Drop is enabled. You can disable default behavior using the "jxbrowser.chromium.dnd.enabled" System Property.
 * For example:
 * System.setProperty("jxbrowser.chromium.dnd.enabled", "false");
 */
class DisableDragAndDropSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        // Disable Drag and Drop.
        view.setDragAndDropEnabled(false);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://www.google.com");
    }
}

/**
 * The sample demonstrates how to disable functionality that allows dragging
 * links from the loaded web page.
 */
class JavaFXDisableDnDSample extends Application {

    @Override
    public void init() throws Exception {
        // On Mac OS X Chromium engine must be initialized in non-UI thread.
        if (Environment.isMac()) {
            BrowserCore.initialize();
        }
    }

    @Override
    public void start(final Stage primaryStage) {
        Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
        final com.teamdev.jxbrowser.chromium.javafx.BrowserView view = new com.teamdev.jxbrowser.chromium.javafx.BrowserView(browser);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                LightWeightWidget lightWeightWidget = (LightWeightWidget)
                        view.getChildren().get(0);
                if (lightWeightWidget.isDragAndDropEnabled()) {
                    // Now you cannot drag and drop links from the loaded web page.
                    lightWeightWidget.setDragAndDropEnabled(false);
                }
            }
        });

        Scene scene = new Scene(new BorderPane(view), 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        browser.loadURL("http://www.google.com");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
