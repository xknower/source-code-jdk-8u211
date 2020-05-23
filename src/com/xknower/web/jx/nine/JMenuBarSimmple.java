package com.xknower.web.jx.nine;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * When you embed BrowserView component into Java Swing frame with menu bar,
 * by default you can see that browser component overlays pop-up menu as shown on the following screenshot:
 * <p>
 * The reason of this issue is in mixing lightweight pop-up menu
 * and heavyweight Browser component (by default Browser component is always heavyweight).
 * To fix this issue you need to disable lightweight implementation of pop-ups using the following command:
 * <p>
 * JPopupMenu.setDefaultLightWeightPopupEnabled(false);
 * <p>
 * This code forces all Swing pop-up menus to be heavyweight. As result you don't see the issue:
 */
public class JMenuBarSimmple {
    public static void main(String[] args) {
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("Open..."));
        fileMenu.add(new JMenuItem("Close"));

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);

        JFrame frame = new JFrame();
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new JTextField("http://www.google.com"), BorderLayout.NORTH);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(425, 290);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://google.com");
    }
}
