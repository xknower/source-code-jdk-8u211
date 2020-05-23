package com.xknower.web.jx.nine;

import com.teamdev.jxbrowser.chromium.Browser;

import com.teamdev.jxbrowser.chromium.ContextMenuHandler;
import com.teamdev.jxbrowser.chromium.ContextMenuParams;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * By default JxBrowser doesn't display context menu when user right clicks on the loaded web page.
 * Standard Chromium context menu isn't supported either because it's part of Google Chromium application.
 * <p>
 * To display context menu when user right clicks on the loaded web page, you must register your ContextMenuHandler implementation.
 * When user right clicks on the web page the ContextMenuHandler.showContextMenu(ContextMenuParams params) method is invoked.
 * This method receives ContextMenuParams parameter that contains information about HTML element at mouse right click location on the web page,
 * location of mouse pointer, type of HTML element under mouse pointer, link URL, link text, image src attribute value, etc.
 * Use this information to configure context menu.
 * <p>
 * To register your implementation of the ContextMenuHandler use the Browser.setContextMenuHandler(ContextMenuHandler contextMenuHandler) method.
 */
public class ContextMenuSimple {
}

/**
 * The sample demonstrates how to register custom ContextMenuHandler,
 * to handle mouse right clicks and display custom Swing context menu.
 */
class ContextMenuSample {
    public static void main(String[] args) {
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.setContextMenuHandler(new MyContextMenuHandler(view));
        browser.loadURL("http://www.google.com");
    }

    private static class MyContextMenuHandler implements ContextMenuHandler {

        private final JComponent component;

        private MyContextMenuHandler(JComponent parentComponent) {
            this.component = parentComponent;
        }

        @Override
        public void showContextMenu(final ContextMenuParams params) {
            final JPopupMenu popupMenu = new JPopupMenu();
            if (!params.getLinkText().isEmpty()) {
                popupMenu.add(createMenuItem("Open link in new window", new Runnable() {
                    @Override
                    public void run() {
                        String linkURL = params.getLinkURL();
                        System.out.println("linkURL = " + linkURL);
                    }
                }));
            }

            final Browser browser = params.getBrowser();
            popupMenu.add(createMenuItem("Reload", new Runnable() {
                @Override
                public void run() {
                    browser.reload();
                }
            }));

            final Point location = params.getLocation();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    popupMenu.show(component, location.x, location.y);
                }
            });
        }

        private static JMenuItem createMenuItem(String title, final Runnable action) {
            JMenuItem reloadMenuItem = new JMenuItem(title);
            reloadMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action.run();
                }
            });
            return reloadMenuItem;
        }
    }
}

/**
 * The sample demonstrates how to register custom ContextMenuHandler,
 * to handle mouse right clicks and display custom JavaFX context menu.
 */
class JavaFXContextMenuSample extends Application {
    @Override
    public void start(Stage primaryStage) {
        Browser browser = new Browser();
        com.teamdev.jxbrowser.chromium.javafx.BrowserView browserView = new com.teamdev.jxbrowser.chromium.javafx.BrowserView(browser);

        browser.setContextMenuHandler(new MyContextMenuHandler(browserView));

        StackPane pane = new StackPane();
        pane.getChildren().add(browserView);
        Scene scene = new Scene(pane, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        browser.loadURL("http://www.google.com");
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class MyContextMenuHandler implements ContextMenuHandler {

        private final Pane pane;

        private MyContextMenuHandler(Pane paren) {
            this.pane = paren;
        }

        @Override
        public void showContextMenu(final ContextMenuParams params) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    createAndDisplayContextMenu(params);
                }
            });
        }

        private void createAndDisplayContextMenu(final ContextMenuParams params) {
            final ContextMenu contextMenu = new ContextMenu();

            // Since context menu doesn't auto hide, listen mouse press events
            // on BrowserView and hide context menu on mouse press
            pane.getChildren().get(0).setOnMousePressed(new javafx.event.EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    contextMenu.hide();
                }
            });

            // If there's link under mouse pointer, create and add
            // the "Open link in new window" menu item to our context menu
            if (!params.getLinkText().isEmpty()) {
                contextMenu.getItems().add(createMenuItem(
                        "Open link in new window", new Runnable() {
                            @Override
                            public void run() {
                                String linkURL = params.getLinkURL();
                                System.out.println("linkURL = " + linkURL);
                            }
                        }));
            }

            // Create and add "Reload" menu item to our context menu
            contextMenu.getItems().add(createMenuItem("Reload", new Runnable() {
                @Override
                public void run() {
                    params.getBrowser().reload();
                }
            }));

            // Display context menu at required location on screen
            Point location = params.getLocation();
            Point2D screenLocation = pane.localToScreen(location.x, location.y);
            contextMenu.show(pane, screenLocation.getX(), screenLocation.getY());
        }

        private static MenuItem createMenuItem(String title, final Runnable action) {
            MenuItem menuItem = new MenuItem(title);
//            menuItem.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    action.run();
//                }
//            });
            return menuItem;
        }
    }
}
