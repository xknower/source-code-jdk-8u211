package com.xknower.web.jx.nine;

import com.teamdev.jxbrowser.chromium.Browser;
import javafx.application.Platform;


/**
 * JxBrowser supports two rendering modes: lightweight and heavyweight.
 * <p>
 * By default, heavyweight rendering mode is enabled, so when you create Browser instance using the following code,
 * it creates a new Browser instance configured to use heavyweight rendering mode:
 */
public class LightweightOrHeavyweight {
    // The differences between lightweight and heavyweight rendering modes and recommendations about what rendering mode you should use in your application you can find below:

    // To create a new Browser instance with specified rendering mode use the following way:
    // Creates Browser instance with enabled lightweight rendering mode
    //Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
    //
    // Creates Browser instance with enabled heavyweight rendering mode
    //Browser browser = new Browser(BrowserType.HEAVYWEIGHT);

    /**
     * Lightweight (off-screen)
     * In lightweight rendering mode Chromium engine renders web page off-screen using CPU.
     * Images that represent different parts of loaded web page are stored in shared memory.
     * JxBrowser reads the images from the shared memory and displays them using standard Java Swing/JavaFX 2D Graphics API.
     *
     * To interact with displayed web page we listen to mouse and keyboard events using Java Events API,
     * convert them to appropriate Chromium mouse/keyboard events, and send them to Chromium engine.
     * With this approach we receive a pure lightweight solution.
     *
     * This rendering mode works great if you need a true lightweight Swing/JavaFX component
     * that allows displaying modern HTML5/JavaScript/CSS web pages in your Java application.
     * You can make this lightweight component semitransparent, display other components on top of it,
     * put it into JLayeredPane or JInternalFrame, get notifications about mouse and keyboard events, etc.
     *
     *
     * Please note, that JxBrowser does not support accessibility in a lightweight mode.
     */
    /**
     * Headless environment
     * In case you need to use JxBrowser in a headless Linux environment, we recommend that you use the Lightweight rendering mode.
     *
     * There are two main reasons for that:
     * 1. The Heavyweight (GPU-accelerated) rendering mode running in a headless environment via xvfb is useless
     * because GPU acceleration in xvfb is disabled.
     * 2. Very often in such environment the JxBrowser functionality that allows getting screenshots of the loaded web-pages is used.
     * That functionality is available in the Lightweight rendering mode only.
     */
    /**
     * Limitations
     * 1. Heavy animation on loaded web page cause high CPU and memory usage.
     * It happens because Chromium engine needs to render a lot of images and save them into allocated shared memory.
     * 2. In lightweight mode only Chromium plugins that supports windowless mode can be displayed.
     * Since rendering is done off-screen, Flash player or other plugin must support windowless (off-screen) rendering as well.
     * If plugin doesn't support off-screen rendering, it will not be displayed in lightweight mode.
     * 3. Mouse/keyboard/touch events are processes on Java side and forwarded to Chromium engine.
     * Right now Java doesn't provide fully functional touch events support, so in lightweight rendering mode JxBrowser doesn't support
     * some touch gestures. Same limitation is true for Drag&Drop functionality.
     * In lightweight rendering mode, drag and drop is processed using Java API, so it doesn't work exactly as in Google Chrome.
     * Drag&Drop supports only predefined set of flavours.
     */
    /**
     * Heavyweight (GPU-accelerated)
     * In the heavyweight rendering mode, which is default mode in JxBrowser, we embed a native window into your Java application window
     * and configure Chromium engine to render content into this native window using GPU.
     *
     * User interaction with displayed web page is handled by the Chromium engine. The native Chromium window receives
     * and processes all mouse/keyboard/touch events using platform specific native functionality when it is focused.
     * Such behavior brings a better support of these events compared to the lightweight rendering mode
     * where JxBrowser has to process Java events and forward them to Chromium engine.
     *
     * However, when the native window is focused, the Java application does not receive the mouse/keyboard/touch events automatically,
     * so these events are forwarded to the Java side after processing them in Chromium.
     *
     * Heavyweight (GPU-accelerated) rendering mode works much faster compared to lightweight rendering mode.
     * Rendering performance is the same as in Google Chrome. You can display full screen video or HTML5 animation with 60fps.
     * Compared to lightweight rendering mode, CPU and memory usage is much smaller, because web page's content is rendered via GPU.
     *
     * So, if rendering performance is very important for your application, then we recommend that you use the default heavyweight rendering mode.
     */
    /**
     * Limitations
     * <p>
     * Lightweight and heavyweight mixing
     * Java Swing/JavaFX toolkits allows building UI based on lightweight components. When we embed a native window into Java frame
     * it can lead to a well-known issue with mixing heavyweight and lightweight components.
     * It's not recommended that you put heavyweight BrowserView component into JLayeredPane or JInternalFrame,
     * or mix it with the lightweight components (e.g. trying to display a lightweight component over heavyweight BrowserView).
     * <p>
     * Layered windows in JavaFX applications
     * Configuring the stage with the StageStyle.TRANSPARENT style adds the WS_EX_LAYERED window style flag to JavaFX window.
     * This flag is used to create a layered window. The layered window represents an off-screen window that draws its content off-screen.
     * If we embed another window (it happens when heavyweight rendering mode is used in JxBrowser) into a layered window,
     * its content will not be painted because of window types conflict.
     * <p>
     * Browser disposal in JavaFX
     * Disposing Browser instance in the incorrect thread in JavaFX may lead to a deadlock on the native side.
     * The Browser instances must be disposed on different threads depending on the operating system.
     * In Linux and macOS the Browser.dispose() method must be called on the UI thread,
     * while on Windows Browser must be disposed on the non-UI thread. For example:
     */
    public static void main(String[] args) {
        Browser browser = new Browser();
        // Linux and MacOS
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                browser.dispose();
            }
        });

        // Windows
        new Thread(new Runnable() {
            @Override
            public void run() {
                browser.dispose();
            }
        }).start();
    }
}
