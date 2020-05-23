package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * To get selected HTML on the loaded web page use
 * the Browser.getSelectedHTML() method as described in the example below:
 * <p>
 * The example demonstrates how to get selected HTML on the loaded web page.
 */
public class GettingSelectedHTML {
    public static void main(String[] args) {
        final Browser browser = new Browser();
        final BrowserView view = new BrowserView(browser);

        JButton button = new JButton("Get Selected HTML");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String html = browser.getSelectedHTML();
                JOptionPane.showMessageDialog(view,
                        html, "Selected HTML", JOptionPane.PLAIN_MESSAGE);
            }
        });

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(button, BorderLayout.NORTH);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://teamdev.com");
    }
}
