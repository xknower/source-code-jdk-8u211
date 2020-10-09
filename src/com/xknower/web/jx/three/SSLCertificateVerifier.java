package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * In JxBrowser 6.3 the CertificateVerifier API has been introduced.
 * Using this API you can get information about each SSL certificate used for displaying HTTPS web pages
 * and decide whether it should be accepted or rejected. By default,
 * Chromium engine decides whether certificate should be accepted/rejected.
 * You can register your own CertificateVerifier implementation to modify default behavior. For example:
 * <p>
 * The sample demonstrates how to accept/reject SSL certificates using
 * custom SSL certificate verifier.
 */
public class SSLCertificateVerifier {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        NetworkService networkService = browser.getContext().getNetworkService();
        networkService.setCertificateVerifier(new CertificateVerifier() {
            @Override
            public CertificateVerifyResult verify(CertificateVerifyParams params) {
                // Reject SSL certificate for all "google.com" hosts.
                if (params.getHostName().contains("google.com")) {
                    return CertificateVerifyResult.INVALID;
                }
                return CertificateVerifyResult.OK;
            }
        });

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://google.com");
    }
}
