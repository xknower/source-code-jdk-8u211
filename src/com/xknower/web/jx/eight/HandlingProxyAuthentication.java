package com.xknower.web.jx.eight;

import com.teamdev.jxbrowser.chromium.AuthRequiredParams;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

/**
 * Some proxy servers require authentication.
 * JxBrowser API allows handling proxy server authentication and providing username & password programmatically.
 * The following code demonstrates how to do it:
 */
public class HandlingProxyAuthentication {
    public static void main(String[] args) {
        Browser browser = new Browser();
        browser.getContext().getNetworkService().setNetworkDelegate(new DefaultNetworkDelegate() {
            @Override
            public boolean onAuthRequired(AuthRequiredParams params) {
                if (params.isProxy()) {
                    params.setUsername("proxy-username");
                    params.setPassword("proxy-password");
                    return false;
                }
                return true;
            }
        });
    }
}
