package com.xknower.web.jx.thirteen;

/**
 * JxBrowser delegates work with cookies to Chromium engine. Chromium engine decides how to download cookies from a web server,
 * extract them from HTTP headers and store them on a local file system (persistent cookies) or memory (session cookies).
 * By default JxBrowser doesn't modify Chromium's behavior, so support of cookies is enabled by default.
 * <p>
 * <p>
 * <p>
 * To work with cookies, JxBrowser provides CookieStorage and Cookie classes.
 * Using CookieStorage you can access the actual cookie storage to get, modify or remove cookies.
 * The Cookie class allows you to get information about specified cookie.
 * <p>
 * <p>
 * <p>
 * Supported Protocols
 * JxBrowser supports cookies that are sent using the following protocols:
 * <p>
 * HTTP
 * HTTPS
 * WS (WebSocket)
 * WSS (Secured WebSocket)
 * If the cookie is sent using a protocol that isn't in the list (e.g. ftp://), then they will not be stored in the storage.
 */
public class AboutCookies {
}
