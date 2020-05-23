package com.xknower.web.jx.twelve;

/**
 * If you need to cancel or disable printing at all you can use the following approach:
 * <p>
 * Java
 * browser.setPrintHandler(new PrintHandler() {
 * *     @Override
 * *     public PrintStatus onPrint(PrintJob printJob) {
 * *         return PrintStatus.CANCEL;
 * *     }
 * });
 */
public class CancelPrinting {
}
