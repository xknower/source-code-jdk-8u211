package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * JxBrowser allows displaying PDF document using Chromium PDF Viewer plugin.
 * You can display PDF file available on a remote web server (using URL of the PDF file)
 * or a PDF file located on your local file system.
 * <p>
 * The sample demonstrates capability of JxBrowser to display PDF documents using
 * built-in Chromium PDF Viewer.
 */
public class DisplayingPDF {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://www.orimi.com/pdf-test.pdf");
    }
}
