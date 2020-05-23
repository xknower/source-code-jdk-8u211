package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * JxBrowser provides API for working with navigation history. Using this API you can get information about navigation entries, got to entry at specified index, remove navigation entries, etc.
 * <p>
 * Note: When you create Browser instance it navigates to the about:blank web page by default, so there's always one entry in navigation history.
 * <p>
 * Receiving navigation entry count
 * Returns the number of entries in the back/forward list.
 * int entryCount = browser.getNavigationEntryCount();
 * <p>
 * Index of the current navigation entry
 * Returns index of the current navigation entry in the back/forward list.
 * int index = browser.getCurrentNavigationEntryIndex();
 * <p>
 * Navigating to the entry at index
 * Navigates to the entry at a specific index in the back/forward list.
 * browser.goToIndex(index);
 * <p>
 * Removing the entry at index
 * Removes navigation entry from the back/forward list at a specific index.
 * boolean success = browser.removeNavigationEntryAtIndex(index);
 * <p>
 * Getting information about navigation entry
 * Prints information about the navigation entry at specific index.
 * NavigationEntry navigationEntry = browser.getNavigationEntryAtIndex(index);
 * System.out.println("URL = " + navigationEntry.getURL());
 * System.out.println("Original URL = " + navigationEntry.getOriginalURL());
 * System.out.println("Title = " + navigationEntry.getTitle());
 * <p>
 * The sample demonstrates how to clear entire navigation history.
 */
public class NavigationHistory {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loadURLAndWaitReady(browser, "http://www.google.com");
        loadURLAndWaitReady(browser, "http://www.teamdev.com");

        // Returns the number of entries in the back/forward list.
        int entryCount = browser.getNavigationEntryCount();
        // Remove navigation entries at index.
        for (int i = entryCount - 2; i >= 0; i--) {
            boolean success = browser.removeNavigationEntryAtIndex(i);
            System.out.println("Navigation entry at index " + i +
                    " has been removed successfully? " + success);
        }
    }

    private static void loadURLAndWaitReady(Browser browser, final String url) {
        Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
            @Override
            public void invoke(Browser value) {
                value.loadURL(url);
            }
        });
    }
}
