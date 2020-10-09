package com.xknower.web.jx.nineteen;

/**
 * JxBrowser doesn't support Google Chrome extensions. Google Chrome extensions integrate with Google Chrome app UI such as tool bar,
 * context menu, etc. So, it's expected that Chrome extension is running in Google Chrome.
 * <p>
 * JxBrowser doesn't integrate with Google Chrome app. It integrates only with web browser control
 * that renders web content via Chromium engine. So, the library doesn't have Google Chrome UI required for extensions.
 * This is why JxBrowser doesn't support extensions.
 */
public class ChromiumExtensions {
}
