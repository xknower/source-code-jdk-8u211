package com.xknower.web.jx.two;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;

/**
 * By default, JxBrowser stores user data in a folder with the path that usually looks like
 * %LocalAppData%\JxBrowser\browsercore-<version>\data” on Windows.
 * On Linux and macOS user’s temp directory is used.
 * <p>
 * Important: In case of using a custom user data directory,
 * it is completely possible to upgrade JxBrowser from an older version to a newer one,
 * however, in case of a downgrade, the user data folder may appear incompatible.
 * Such functionality is not supported, so you should avoid such situations when you downgrade JxBrowser
 * and tell it to use the user data directory created by a newer version.
 */
public class StoringUserData {
    public static void main(String[] args) {
        // To change the default behavior and customize the path to the user data directory use the following approach:
        BrowserContext context = new BrowserContext(new BrowserContextParams("Users/Me/JxBrowser/Data"));
        Browser browser = new Browser(context);
    }
}
