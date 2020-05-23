package com.xknower.web.jx.nine;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * In JxBrowser 6.10 and higher there's an option that allows enabling accelerated lightweight rendering mode.
 * This rendering mode has improved performance compared to default lightweight rendering mode.
 * It allows producing ~30% more frames per second.
 * <p>
 * Accelerated lightweight rendering is faster than the ordinary lightweight rendering,
 * as in the accelerated mode rendering is performed directly in RAM. The ordinary lightweight mode uses a video card
 * where there is a phase of copying an image from video memory to RAM.
 * As the stage of copying from video memory to RAM is absent in the accelerated lightweight mode, we can see an increase in speed.
 * <p>
 * Important: the only disadvantage of this accelerated rendering mode is
 * that it doesn't support WebGL, because GPU process isn't used in this case.
 * If you don't need to display web pages with WebGL, we recommend that you enable and use accelerated lightweight rendering mode.
 * <p>
 * To enable accelerated lightweight rendering mode you must specify the following Chromium switches before
 * you create the first Browser instance in your application:
 */
public class AcceleratedLightweightRendering {
    public static void main(String[] args) {
        BrowserPreferences.setChromiumSwitches(
                "--disable-gpu",
                "--disable-gpu-compositing",
                "--enable-begin-frame-scheduling",
                "--software-rendering-fps=60"
        );

        Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(1100, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("https://www.youtube.com/watch?v=lfwjzNB--5k");
    }
}
