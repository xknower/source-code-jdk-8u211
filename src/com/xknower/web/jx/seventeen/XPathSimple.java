package com.xknower.web.jx.seventeen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.XPathResult;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * JxBrowser DOM API provides functionality that allows evaluating XPath expressions.
 * You can evaluation XPath expression in scope of DOMDocument or specified DOMNode.
 * Both DOMDocument and DOMNode implements SearchContext interface that provides the following methods:
 *
 * XPathResult evaluate(String expression)
 * XPathResult evaluate(String expression, XPathResultType type)
 */

/**
 * Sample code:
 *
 * Java
 * DOMDocument document = browser.getDocument();
 * XPathResult result = document.evaluate("count(//div)");
 *
 *
 * The result of evaluation is stored in the XPathResult object.
 * First you need to make sure that result is not an error and the evaluation completed successfully:
 *
 * if (result.isError()) {
 *     System.out.println("Error: " + result.getErrorMessage());
 *     return;
 * }
 * Then you need to make sure that result contains expected value type (e.g. Number, Boolean, String, DOMNode) and extract the value itself:
 *
 * if (result.isNumber()) {
 *     System.out.println("Result: " + result.getNumber());
 * }
 * or
 *
 * if (result.isString()) {
 *     System.out.println("Result: " + result.getString());
 * }
 * or
 *
 * if (result.isSingleNode()) {
 *     DOMNode node = result.getSingleNode();
 *     System.out.println("Result: " + node.getTextContent());
 * }
 */

/**
 * This sample demonstrates how to evaluate the XPath expression and work with the result.
 */
public class XPathSimple {
    public static void main(String[] args) {
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.getContentPane().add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    DOMDocument document = browser.getDocument();
                    XPathResult result = document.evaluate("count(//div)");
                    // If the expression is not a valid XPath expression or the document
                    // element is not available, we'll get an error.
                    if (result.isError()) {
                        System.out.println("Error: " + result.getErrorMessage());
                        return;
                    }

                    // Make sure that result is a number.
                    if (result.isNumber()) {
                        System.out.println("Result: " + result.getNumber());
                    }
                }
            }
        });
        browser.loadURL("http://www.teamdev.com/jxbrowser");
    }
}
