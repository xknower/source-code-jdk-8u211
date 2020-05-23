package com.xknower.web.jx.four;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Since 6.3 version JxBrowser provides API that allows muting/unmuting audio on the loaded web page
 * and checking whether audio is muted or not.
 * <p>
 * To mute audio use the following code: browser.setAudioMuted(true);
 * <p>
 * To check whether audio is muted use the following code: boolean audioMuted = browser.isAudioMuted();
 * <p>
 * Entire example that demonstrates how how to mute audio sound on the opened web page
 * and check whether audio is muted or not is the following:
 * <p>
 * This sample demonstrates how to mute audio sound on the opened web page
 * and check whether audio is muted or not.
 */
public class MutingAudio {
    public static void main(String[] args) {
        final Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        final JButton muteAudioButton = new JButton();
        muteAudioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.setAudioMuted(!browser.isAudioMuted());
                updateButtonText(muteAudioButton, browser);
            }
        });
        updateButtonText(muteAudioButton, browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(muteAudioButton, BorderLayout.NORTH);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("https://www.youtube.com/");
    }

    private static void updateButtonText(final JButton button, final Browser browser) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                button.setText(browser.isAudioMuted() ? "Unmute Audio" : "Mute Audio");
            }
        });
    }
}
