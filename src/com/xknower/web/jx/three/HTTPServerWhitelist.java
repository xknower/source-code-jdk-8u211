package com.xknower.web.jx.three;

/**
 * HTTP Server Authorization Whitelist
 * Since 6.8 you can configure JxBrowser with HTTP server authorization whitelist that represents a string
 * with comma/semicolon separated list of URLs. For example:
 * Browser browser = new Browser();
 * BrowserContext browserContext = browser.getContext();
 * NetworkService networkService = browserContext.getNetworkService();
 * networkService.setServerWhiteList("*google.com,*example.com,*baz");
 * <p>
 * HTTP Network Delegate Whitelist
 * To configure JxBrowser with HTTP network delegate whitelist you can use the approach described below:
 * Browser browser = new Browser();
 * BrowserContext browserContext = browser.getContext();
 * NetworkService networkService = browserContext.getNetworkService();
 * networkService.setDelegateWhiteList("*google.com,*example.com,*baz");
 */
public class HTTPServerWhitelist {
}
