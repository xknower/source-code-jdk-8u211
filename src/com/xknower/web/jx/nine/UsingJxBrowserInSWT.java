package com.xknower.web.jx.nine;

//import java.awt.Frame;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.awt.SWT_AWT;
//import org.eclipse.swt.layout.FillLayout;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Shell;
//
//import com.teamdev.jxbrowser.chromium.Browser;
//import com.teamdev.jxbrowser.chromium.swing.BrowserView;
//import javafx.embed.swt.FXCanvas;
//import javafx.scene.Scene;
//
//import com.teamdev.jxbrowser.chromium.Browser;
//import com.teamdev.jxbrowser.chromium.javafx.BrowserView;

/**
 * JxBrowser supports Swing/AWT and JavaFX UI toolkits. It provides two implementations of the BrowserView class
 * that can be used for embedding JxBrowser into Swing/AWT and JavaFX applications:
 * // com.teamdev.jxbrowser.chromium.swing.BrowserView
 * // com.teamdev.jxbrowser.chromium.javafx.BrowserView
 * <p>
 * To embed JxBrowser into SWT applications you can use the approaches described below.
 * These approaches are based on the third-party SWT-AWT and SWT-JavaFX components (bridges).
 * <p>
 * Note: please note that mixing the two UI toolkits isn't a simple task, so there might be some integration issues
 * such as focus transferring, flickering, freezing, thread deadlocks, etc.
 * You can read more about possible issues in the Swing/SWT Integration article.
 * <p>
 * Important for macOS: Java should be run with -XstartOnFirstThread parameter in case
 * you are not starting the script via Eclipse using macOS. Otherwise you will  get the "org.eclipse.swt.SWTException:
 * Invalid thread access" error. If you are using Eclipse, this parameter is applied automatically and you won't encounter this error.
 * <p>
 * Since JxBrowser 6.0 we started initializing Chromium engine in Java process to enable heavyweight rendering mode.
 * Chromium engine actively uses Cocoa UI threads as well as Java Swing, and SWT.
 * It's very important to initialize Chromium in Cocoa UI thread (AppKit).
 * With using the -XstartOnFirstThread VM flag, default UI thread is changed to Java "main" thread which breaks Chromium functionality
 * that requires AppKit thread. As result, Chromium fails to initialize in "main" thread and you see the freezing issue
 * and later the "IPC cannot start because Chromium doesn't respond" error message.
 * <p>
 * This is a regression issue caused by architectural changes in 6.0. It should be completely resolved after the implementation of SWT support.
 * <p>
 * Meanwhile, as a workaround, you can use lightweight rendering mode with the jxbrowser.ipc.external=true JVM parameter.
 * In this case, Chromium engine will be initialized in a separate native process, so you will not see this issue anymore.
 * Please note that with this JVM parameter you can use only lightweight browser mode.
 */
public class UsingJxBrowserInSWT {
}

// Swing & SWT

/**
 * To embed Swing/AWT implementation of com.teamdev.jxbrowser.chromium.swing.BrowserView into SWT application
 * use the org.eclipse.swt.awt.SWT_AWT instance as shown below:
 * <p>
 * The sample demonstrates how to use JxBrowser Swing control in
 * SWT application using SWT_AWT bridge.
 */
//public class JxBrowserSwingSWT {
//    public static void main(String[] arguments) {
//        Browser browser = new Browser();
//        BrowserView view = new BrowserView(browser);
//
//        Display display = new Display();
//        Shell shell = new Shell(display);
//        shell.setLayout(new FillLayout());
//
//        Composite composite = new Composite(shell,
//                SWT.EMBEDDED | SWT.NO_BACKGROUND);
//        Frame frame = SWT_AWT.new_Frame(composite);
//        frame.add(view);
//
//        browser.loadURL("http://google.com");
//
//        shell.open();
//
//        while (!shell.isDisposed()) {
//            if (!display.readAndDispatch()) {
//                display.sleep();
//            }
//        }
//        display.dispose();
//    }
//}

// JavaFX & SWT

/**
 * To embed JavaFX implementation of com.teamdev.jxbrowser.chromium.javafx.BrowserView into SWT application
 * use the javafx.embed.swt.FXCanvas instance as shown below:
 * <p>
 * The sample demonstrates how to use JxBrowser JavaFX
 * control in SWT application using FXCanvas.
 */
//public class JxBrowserJavaFXSWT {
//    public static void main(String[] arguments) {
//        Display display = new Display();
//        Shell shell = new Shell(display);
//        shell.setLayout(new FillLayout());
//
//        Browser browser = new Browser();
//        FXCanvas canvas = new FXCanvas(shell, SWT.NONE);
//
//        // BrowserView instance must be initialized after FXCanvas.
//        BrowserView view = new BrowserView(browser);
//        canvas.setScene(new Scene(view));
//
//        browser.loadURL("http://google.com");
//
//        shell.open();
//        while (!shell.isDisposed()) {
//            if (!display.readAndDispatch()) {
//                display.sleep();
//            }
//        }
//        display.dispose();
//    }
//}