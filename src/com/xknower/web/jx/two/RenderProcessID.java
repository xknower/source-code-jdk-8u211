package com.xknower.web.jx.two;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * JxBrowser implements Chromium multi-process architecture.
 * Chromium engine runs DOM and JavaScript of the loaded web page in separate render process.
 * If you load a web page with different domain,
 * Chromium can terminate render process of the previously loaded web page and load a new web page in new render process.
 * <p>
 * To get information about render process where the currently loaded web page is running, use the Browser.getRenderProcessInfo() method.
 * This method collects information about currently running render process and returns it as RenderProcessInfo type.
 * <p>
 * Demonstrates how to get PID of Chromium render process where DOM and
 * JavaScript of the currently loaded web page are running.
 */
public class RenderProcessID {
    public static void main(String[] args) {
        // The following example demonstrates how to use this functionality:
        final Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Wait until https://google.com is loaded completely
        Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
            @Override
            public void invoke(Browser value) {
                browser.loadURL("https://google.com");
            }
        });

        // Get PID of Chromium render process associated with
        // the current Browser instance
        long pid = browser.getRenderProcessInfo().getPID();

        System.out.println(pid);
    }
}
