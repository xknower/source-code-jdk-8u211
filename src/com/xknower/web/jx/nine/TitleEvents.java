package com.xknower.web.jx.nine;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.TitleEvent;
import com.teamdev.jxbrowser.chromium.events.TitleListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * To get notifications when document's title has been changed use TitleListener as shown on the following example:
 * <p>
 * The sample demonstrates how to listen to title change events.
 */
public class TitleEvents {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addTitleListener(new TitleListener() {
            @Override
            public void onTitleChange(TitleEvent event) {
                System.out.println("Title = " + event.getTitle());
            }
        });

        browser.loadURL("http://www.google.com");
    }
}
