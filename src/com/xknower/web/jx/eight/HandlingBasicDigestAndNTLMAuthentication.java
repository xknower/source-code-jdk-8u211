package com.xknower.web.jx.eight;

import com.teamdev.jxbrowser.chromium.AuthRequiredParams;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

/**
 * To handle basic, digest or NTLM authentication
 * you can use the NetworkDelegate.onAuthRequired(AuthRequiredParams params) handler.
 * <p>
 * To display authentication dialog where user can enter valid user name and password
 * you must register default Swing/JavaFX implementation of the NetworkDelegate
 * or your own implementation of the NetworkDelegate interface.
 * <p>
 * The following example demonstrates how to register and override default Swing implementation
 * of the NetworkDelegate interface in order to provide user name and password
 * without displaying authorization dialog:
 */
public class HandlingBasicDigestAndNTLMAuthentication {
    public static void main(String[] args) {
        Browser browser = new Browser();
        browser.getContext().getNetworkService().setNetworkDelegate(new DefaultNetworkDelegate() {
            @Override
            public boolean onAuthRequired(AuthRequiredParams params) {
                if (!params.isProxy()) {
                    params.setUsername("proxy-username");
                    params.setPassword("proxy-password");
                    // Don't cancel authentication
                    return false;
                }
                // Cancel authentication
                return true;
            }
        });
    }
}
