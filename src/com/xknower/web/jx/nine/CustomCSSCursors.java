package com.xknower.web.jx.nine;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * JxBrowser supports custom web cursors. The following sample demonstrates how it works:
 * <p>
 * The sample demonstrates support of custom CSS cursors.
 */
public class CustomCSSCursors {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadHTML("<html><body><a href=\"\" style=\"cursor: " +
                "url(http://www.kyrnin.com/about/redball.cur),default;\">" +
                "cursor: url(redball.cur);</a></body></html>");
    }
}
