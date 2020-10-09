package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.Certificate;
import com.teamdev.jxbrowser.chromium.CertificateErrorParams;
import com.teamdev.jxbrowser.chromium.DefaultLoadHandler;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * The CertificateErrorParams parameter provides information about invalid SSL certificate.
 * You can decide whether certificate is allowed or not using its details.
 * <p>
 * Demonstrates how to handle SSL certificate errors.
 */
public class HandlingSSLCertificateErrors {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // By default JxBrowser allows loading HTTPS web sites with invalid SSL certificates.
        // If you need to change this default behavior and don't allow invalid SSL certificates,
        // you can register your own LoadHandler implementation
        // where you decide whether invalid SSL certificates should be ignored or not. For example:
        browser.setLoadHandler(new DefaultLoadHandler() {
            @Override
            public boolean onCertificateError(CertificateErrorParams params) {
                Certificate certificate = params.getCertificate();
                System.out.println("subjectName = " + certificate.getSubjectName());
                System.out.println("issuerName = " + certificate.getIssuerName());
                System.out.println("hasExpired = " + certificate.hasExpired());
                System.out.println("errorCode = " + params.getCertificateError());
                // Return false to ignore certificate error.
                return false;
            }
        });
        browser.loadURL("<https-url-with-invalid-ssl-certificate>");
    }
}
