package com.xknower.web.jx.two;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.SearchParams;
import com.teamdev.jxbrowser.chromium.SearchResult;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * This sample demonstrates, how to find text on the loaded web page.
 * <p>
 * JxBrowser API provides functionality that allows on the currently loaded web page:
 * Finding specified text
 * Highlighting all matches
 * Selecting first match
 * <p>
 * To find specified text on the loaded web page use the Browser.findText() method.
 * This method returns SearchResult instance that provides access to search results
 * such as number of matches and index of selected match.
 * <p>
 * Note: Browser performs search only through visible content on the loaded document.
 * If some text presented on the web page isn't visible due to CSS rules,
 * Browser will not go through this content during search. Also,
 * Browser doesn't search text on the document with size 0x0, so make sure that Browser component is visible and its size isn't empty.
 * <p>
 * To clear highlights on a web page (search results) and cancel search use Browser.stopFindingText(StopFindAction action) method.
 */
public class FindingText {
    public static void main(String[] args) {
        final Browser browser = new Browser();
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
                    SearchParams request = new SearchParams("find me");
                    // Find text from the beginning of the loaded web page.
                    SearchResult result = browser.findText(request);
                    System.out.println(result.indexOfSelectedMatch() + "/" +
                            result.getNumberOfMatches());
                    // Find the same text again from the currently selected match.
                    result = browser.findText(request);
                    System.out.println(result.indexOfSelectedMatch() + "/" +
                            result.getNumberOfMatches());
                }
            }
        });
        browser.loadHTML("<html><body><p>Find me</p><p>Find me</p></body></html>");
    }
}
