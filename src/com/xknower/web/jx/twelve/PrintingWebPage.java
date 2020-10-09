package com.xknower.web.jx.twelve;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The web page you load in browser can be printed using one of the following ways:
 * <p>
 * 1. Using the window.print() JavaScript function. This function can be invoked from JavaScript code on the loaded web page (e.g. when user clicks some button, JavaScript code invokes this function to print the web page).
 * <p>
 * 2. Using the Browser.print() method of JxBrowser API. For example, you can invoke this method when user presses Print button in your Java application. For example:
 * Java
 * JButton print = new JButton("Print");
 * print.addActionListener(new ActionListener() {
 *
 * @Override public void actionPerformed(ActionEvent e) {
 * browser.print();
 * }
 * });
 * Both methods prints currently loaded web page using default print settings. If you need to print web page with custom print settings, you must register your own implementation of PrintHandler interface where you can override default print settings or cancel printing. For example:
 * <p>
 * The sample demonstrates how to print currently loaded web page with custom print settings.
 */
public class PrintingWebPage {
    public static void main(String[] args) {
        final Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

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
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
//
//        browser.setPrintHandler(new PrintHandler() {
//            @Override
//            public PrintStatus onPrint(PrintJob printJob) {
//                PrintSettingsSimple printSettings = printJob.getPrintSettings();
//                printSettings.setPrinterName("Microsoft XPS Document Writer");
//                printSettings.setLandscape(false);
//                printSettings.setPrintBackgrounds(false);
//                printSettings.setColorModel(ColorModel.COLOR);
//                printSettings.setDuplexMode(DuplexMode.SIMPLEX);
//                printSettings.setDisplayHeaderFooter(true);
//                printSettings.setCopies(1);
//                printSettings.setPaperSize(PaperSize.ISO_A4);
//
//                List<PageRange> ranges = new ArrayList<PageRange>();
//                ranges.add(new PageRange(0, 3));
//                printSettings.setPageRanges(ranges);
//
//                printJob.addPrintJobListener(new PrintJobListener() {
//                    @Override
//                    public void onPrintingDone(PrintJobEvent event) {
//                        System.out.println("Printing is finished successfully: " +
//                                event.isSuccess());
//                    }
//                });
//                return PrintStatus.CONTINUE;
//            }
//        });

        browser.loadURL("http://www.teamdev.com");
    }
}
