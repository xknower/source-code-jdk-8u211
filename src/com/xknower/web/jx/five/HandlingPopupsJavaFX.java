package com.xknower.web.jx.five;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.PopupContainer;
import com.teamdev.jxbrowser.chromium.PopupHandler;
import com.teamdev.jxbrowser.chromium.PopupParams;
import com.teamdev.jxbrowser.chromium.events.DisposeEvent;
import com.teamdev.jxbrowser.chromium.events.DisposeListener;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;

/**
 * To handle pop-up windows in your JavaFX application you have two options:
 * <p>
 * 1. Register default JavaFX pop-up handler implementation:
 * browser.setPopupHandler(new com.teamdev.jxbrowser.chromium.javafx.DefaultPopupHandler());
 * <p>
 * 2. Register your own implementation where you decide how exactly you would like to display a new pop-up window:
 */
public class HandlingPopupsJavaFX {

    public static void main(String[] args) {
        Browser browser = new Browser();
        browser.setPopupHandler(new PopupHandler() {
            @Override
            public PopupContainer handlePopup(PopupParams params) {
                return new PopupContainer() {
                    @Override
                    public void insertBrowser(final Browser browser, final Rectangle initialBounds) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                final Stage stage = new Stage();
                                StackPane root = new StackPane();
                                Scene scene = new Scene(root, 800, 600);
                                BrowserView browserView = new BrowserView(browser);
                                root.getChildren().add(browserView);
                                stage.setScene(scene);
                                stage.setTitle("Popup");
                                if (!initialBounds.isEmpty()) {
                                    stage.setX(initialBounds.getLocation().getX());
                                    stage.setY(initialBounds.getLocation().getY());
                                    stage.setWidth(initialBounds.width);
                                    stage.setHeight(initialBounds.height);
                                }

                                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                    @Override
                                    public void handle(javafx.stage.WindowEvent event) {
                                        browser.dispose();
                                    }
                                });

                                browser.addDisposeListener(new DisposeListener<Browser>() {
                                    @Override
                                    public void onDisposed(DisposeEvent<Browser> event) {
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                stage.close();
                                            }
                                        });
                                    }
                                });
                                stage.show();
                            }
                        });
                    }
                };
            }
        });
    }
}
