package com.xknower.web.jx.two;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;

/**
 * Caching is enabled by default in Chromium engine.
 * The persistent cache data is stored in the Cache folder of the Chromium's data directory.
 * For example, c:\Users\<username>\AppData\Local\JxBrowser\jxbrowser-chromium-43.0.2357.52.6.2\data\Cache\
 * <p>
 * Chromium API doesn't provide functionality that allows disabling cache at all,
 * but it provides functionality that allows clearing cache storage.
 * The following example demonstrates how to clear persistent cache storage using JxBrowser Cache API:
 * <p>
 * This sample demonstrates how to clear the Browser's cache.
 * If you create  * several browser instances by calling new Browser()
 * or  * new Browser(BrowserContext context) method using the same BrowserContext instance,
 * calling CacheStorage.clearCache() will remove cache for all these browser instances.
 */
public class ClearingCache {
    public static void main(String[] args) {
        BrowserContext context1 = new BrowserContext(new BrowserContextParams("C:\\my-data1"));
        Browser browser1 = new Browser(context1);
        Browser browser2 = new Browser(context1);

        BrowserContext context2 = new BrowserContext(new BrowserContextParams("C:\\my-data2"));
        Browser browser3 = new Browser(context2);

        // Clears cache of browser1 and browser2 instances
        // because they use the same user data and cache "C:\\my-data1" directory.
        // It doesn't clear cache of browser3, because browser3 uses a different directory
        // for storing cache data - "C:\\my-data2".
        browser1.getCacheStorage().clearCache();
    }
}
