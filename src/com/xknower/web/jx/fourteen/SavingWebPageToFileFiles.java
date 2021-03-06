package com.xknower.web.jx.fourteen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.SavePageType;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * JxBrowser allows you to save web pages as a file or set of files.
 * You can use Browser.saveWebPage(String filePath, String dirPath, SavePageType saveType) method to save the current web page.
 * Before saving make sure the page is loaded completely.
 * <p>
 * Java
 * String filePath = "C:\\SavedPages\\index.html";
 * String dirPath = "C:\\SavedPages\\resources";
 * browser.saveWebPage(filePath, dirPath, SavePageType.COMPLETE_HTML);
 * <p>
 * This sample demonstrates how to save the loaded web page.
 */
public class SavingWebPageToFileFiles {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    String filePath = "D:\\Test\\index.html";
                    String dirPath = "D:\\Test\\resources";
                    event.getBrowser().saveWebPage(filePath, dirPath, SavePageType.COMPLETE_HTML);
                }
            }
        });

        browser.loadURL("http://www.google.com");
    }
}
