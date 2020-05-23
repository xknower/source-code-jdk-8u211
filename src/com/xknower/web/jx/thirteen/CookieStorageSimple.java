package com.xknower.web.jx.thirteen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.Cookie;
import com.teamdev.jxbrowser.chromium.CookieStorage;

import java.util.List;

/**
 * Each CookieStorage stores cookies on a local file system in Chromium's user data directory.
 * So, each CookieStorage depends on BrowserContext that defines where Chromium's user data folder is located.
 * If you need to use different cookie storages for different Browser instances,
 * then you need to configure Browser instances with different BrowserContext instances and different Chromium's user data directories.
 * <p>
 * Browser instances with same BrowserContext instance will share the cookies.
 * <p>
 * The following code demonstrates how to create two Browser instances that don't share cookies:
 * <p>
 * Java
 * Browser browserOne = new Browser(new BrowserContext("C:\\MyUserData1"));
 * Browser browserTwo = new Browser(new BrowserContext("C:\\MyUserData2"));
 * To access CookieStorage of Browser instance use the browser.getCookieStorage() method. Using CookieStorage you can get, modify, and delete cookies. When you modify cookies you must save changes using the CookieStorage.save() method. For example:
 * <p>
 * CookieStorage cookieStorage = browser.getCookieStorage();
 * cookieStorage.setSessionCookie("http://www.a<span class="fr-marker" data-id="0" data-type="false" style="display: none; line-height: 0;"></span><span class="fr-marker" data-id="0" data-type="true" style="display: none; line-height: 0;"></span>.com", "name1", "value1", ".a.com", "/", false, false);
 * cookieStorage.save();
 * JxBrowser supports cookies. Cookies are stored in Chromium user data directory (persistent cookies) and application memory (session cookies).
 * If you delete Chromium user data directory, then all persistent cookies will be removed.
 * Session cookies will be removed automatically when corresponding application terminates.
 * Apart from persistent and session cookies, JxBrowser supports secure and http-only cookies.
 */
public class CookieStorageSimple {
    public static void main(String[] args) {
        Browser browser = new Browser();
        CookieStorage cookieStorage = browser.getCookieStorage();

        // Getting All Cookies
        List<Cookie> cookies = cookieStorage.getAllCookies();
        for (Cookie cookie : cookies) {
            System.out.println("cookie = " + cookie);
        }

        // Getting All Cookies by URL
        List<Cookie> cookies1 = cookieStorage.getAllCookies("http://www.google.com");
        for (Cookie cookie : cookies1) {
            System.out.println("cookie = " + cookie);
        }

        // Creating a Cookie
        // Create and add new cookie
        final int oneHourInMilliseconds = 36000000;
        final int microsecondsOffset = 1000;
        // Cookie will be alive during one hour starting from now
        long expirationTimeInMicroseconds = (System.currentTimeMillis() +
                oneHourInMilliseconds) * microsecondsOffset;
        cookieStorage.setCookie("http://www.google.com", "mycookie",
                "myvalue", ".google.com", "/", expirationTimeInMicroseconds,
                false, false);
        cookieStorage.save();

        //  Creating a Session Cookie
        // Create and add new session cookie
        cookieStorage.setSessionCookie("http://www.google.com", "mycookie",
                "myvalue", ".google.com", "/", false, false);
        cookieStorage.save();

        // Deleting All Cookies
        int numberOfDeletedCookies = cookieStorage.deleteAll();
        cookieStorage.save();

        //Deleting a Cookie
        List<Cookie> cookies2 = cookieStorage.getAllCookies();
        for (Cookie cookie : cookies2) {
            boolean success = cookieStorage.delete(cookie);
        }
        cookieStorage.save();
    }
}
