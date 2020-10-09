package com.xknower.web.jx.nine;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserCore;
import com.teamdev.jxbrowser.chromium.internal.Environment;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


/**
 * JxBrowser supports JavaFX toolkit and can be embedded into JavaFX desktop applications as well.
 * To embed control that displays HTML content you must create com.teamdev.jxbrowser.chromium.javafx.BrowserView instance
 * and put it into a Pane or any other container on Scene.
 * <p>
 * Note: To use JxBrowser in JavaFX applications, JDK 1.8 or higher is required.
 */

/**
 * The following sample demonstrates how to use JxBrowser in a simple JavaFX application:
 * <p>
 * Demonstrates how to embed Browser instance into JavaFX application.
 */
public class UsingJxBrowserInJavaFX extends Application {

    /**
     * Note: in macOS environment you must initialize JxBrowser core functionality in non-UI thread. For example,
     * in the Application.init() method as shown below:
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        // On Mac OS X Chromium engine must be initialized in non-UI thread.
        if (Environment.isMac()) {
            BrowserCore.initialize();
        }
    }

    @Override
    public void start(final Stage primaryStage) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        Scene scene = new Scene(new BorderPane(view), 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        browser.loadURL("http://www.google.com");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/**
 * FXML
 * It is possible to embed BrowserView into a JavaFX app GUI via FXML. In this section we will show you how to do it.
 * <p>
 * 1) First we need to describe the structure of the browser-view-control.fxml file to tell JavaFX
 * how the BrowserView control should be embedded into the app UI.
 */
//<?xml version="1.0" encoding="UTF-8"?>
//
//<?import com.teamdev.jxbrowser.chromium.javafx.BrowserView?>
//<?import javafx.scene.control.TextField?>
//<?import javafx.scene.layout.BorderPane?>
//<BorderPane fx:controller="BrowserViewControl" xmlns:fx="http://javafx.com/fxml">
//    <top>
//        <TextField fx:id="textField" text="http://www.google.com" onAction="#loadURL"/>
//    </top>
//    <center>
//        <BrowserView fx:id="browserView"/>
//    </center>
//</BorderPane>

// 2) Then we need to implement the BrowserViewControl defined in the browser-view-control.fxml file:

/**
 * Represents FXML control with address bar and view area that
 * displays URL entered in the address bar text field.
 */
class BrowserViewControl implements Initializable {

    @FXML
    private TextField textField;

    @FXML
    private BrowserView browserView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        browserView.getBrowser().loadURL(textField.getText());
    }

    public void loadURL(ActionEvent actionEvent) {
        browserView.getBrowser().loadURL(textField.getText());
    }
}

// 3) And finally, create an FXMLSample that displays the app GUI with the embedded BrowserView control using FXML:

/**
 * The sample demonstrates how to embed JavaFX BrowserView
 * component into JavaFX app using FXML.
 */
class FXMLSample extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = FXMLLoader.load(
                FXMLSample.class.getResource("browser-view-control.fxml"));

        primaryStage.setTitle("FXMLSample");
        primaryStage.setScene(new Scene(pane, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(FXMLSample.class, args);
    }
}
