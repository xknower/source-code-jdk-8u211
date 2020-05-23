package com.xknower.web.jx.twelve;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JxBrowser Printing API allows you to decide whether web page should be printed using printer device or saved as PDF document.
 * <p>
 * <p>
 * <p>
 * To save web page as PDF document you must register your own implementation of PrintHandler
 * where you override default print settings and tell Chromium engine to save web page as PDF document
 * at the specified path on the local file system. For example:
 * <p>
 * browser.setPrintHandler(new PrintHandler() {
 *
 * @Override public PrintStatus onPrint(PrintJob printJob) {
 * PrintSettings settings = printJob.getPrintSettings();
 * settings.setPrintToPDF(true);
 * settings.setPDFFilePath("web_page.pdf");
 * return PrintStatus.CONTINUE;
 * }
 * });
 * <p>
 * Once you register PrintHandler that saves web page as PDF document, you can initiate printing using one of the following ways:
 * <p>
 * By invoking the Browser.print() method.
 * By invoking the window.print() JavaScript function.
 */

/**
 * Now, every time when printing is requested from Java or JavaScript code, the PrintHandler will be used
 * where you configure print settings to save web page as PDF document at specified file on the local file system.
 * <p>
 * Note: in your PrintHandler implementation you can display UI dialog where user can decide whether web page
 * should be printed using printer device or saved as PDF document.
 * <p>
 * The sample demonstrates how to print currently loaded web page in
 * a PDF file with custom print settings.
 */
public class PrintingWebPageToPDF {
    public static void main(String[] args) {
        final Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);
//
//        browser.setPrintHandler(new PrintHandler() {
//            @Override
//            public PrintStatus onPrint(PrintJob printJob) {
//                PrintSettingsSimple settings = printJob.getPrintSettings();
//                settings.setPrintToPDF(true);
//                settings.setPDFFilePath("web_page.pdf");
//                settings.setPrintBackgrounds(true);
//                return PrintStatus.CONTINUE;
//            }
//        });

        final JButton print = new JButton("Print to PDF");
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.print();
            }
        });

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(print, BorderLayout.NORTH);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://google.com");
    }
}
