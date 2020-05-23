package com.xknower.web.jx.nineteen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * Voice recognition is one of those Chromium features that uses Google API.
 * You must enable Speech API and billing, otherwise voice recognition will not work.
 * <p>
 * <p>
 * Once you enable Speech API and billing, you can provide the keys to JxBrowser Chromium engine using one of
 * the approaches described in the Chromium API Keys article.
 * <p>
 * <p>
 * The following example demonstrates how to configure Chromium with your API keys and test voice recognition functionality:
 * <p>
 * The sample demonstrates how to enable voice recognition functionality in Chromium
 * engine. By default, voice recognition functionality is disabled. To enable it you must
 * provide your Google API Keys to Chromium engine as shown in the example.
 */
public class VoiceRecognitionSimple {
    public static void main(String[] args) {
        BrowserPreferences.setChromiumVariable("GOOGLE_API_KEY", "My API Key");
        BrowserPreferences.setChromiumVariable("GOOGLE_DEFAULT_CLIENT_ID", "My Client ID");
        BrowserPreferences.setChromiumVariable("GOOGLE_DEFAULT_CLIENT_SECRET", "My Client Secret");

        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("https://google.com");
    }
}
