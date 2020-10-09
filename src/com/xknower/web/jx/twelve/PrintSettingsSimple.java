package com.xknower.web.jx.twelve;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.PrintJob;
import com.teamdev.jxbrowser.chromium.events.PrintJobEvent;
import com.teamdev.jxbrowser.chromium.events.PrintJobListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JxBrowser API allows providing custom print settings on each print request.
 * To provide custom print settings you must register your own implementation
 * of the PrintHandler interface using the Browser.setPrintHandler() method.
 * When Browser instance or JavaScript on the web page requests printing, the PrintHandler.onPrint(PrintJob printJob) method will be invoked.
 * In this method you can modify default print settings:
 * <p>
 * The sample demonstrates how to print currently loaded web page with custom print settings.
 */
public class PrintSettingsSimple {
    public static void main(String[] args) {
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton print = new JButton("Print");
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.print();
            }
        });
        frame.add(print, BorderLayout.NORTH);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.setPrintHandler(new PrintHandler() {
            @Override
            public PrintStatus onPrint(PrintJob printJob) {
                PrintSettings printSettings = printJob.getPrintSettings();
                printSettings.setLandscape(true);
                printSettings.setPrintBackgrounds(true);
                printJob.addPrintJobListener(new PrintJobListener() {
                    @Override
                    public void onPrintingDone(PrintJobEvent event) {
                        System.out.println("PrintingDone success: " + event.isSuccess());
                    }
                });
                return PrintStatus.CONTINUE;
            }
        });

        browser.loadURL("http://www.javascriptkit.com/howto/newtech2.shtml");
    }
}
