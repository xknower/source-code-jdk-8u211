package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

/**
 * JxBrowser provides functionality that allows handling network activity including HTTP requests/responses.
 * You can use the NetworkDelegate to handle all network activity of the Browser instances associated with specified BrowserContext.
 * <p>
 * With NetworkDelegate you can intercept all HTTP requests/responses headers
 * and obtain information about each request/response stage. Below is the list of all request/response stages:
 * <p>
 * onBeforeRequest
 * Fires when a request is about to occur. This event is sent before any TCP connection is made
 * and can be used to redirect requests to another location.
 * It can be used to access and modify POST data of the request when method type is "POST". See example.
 * <p>
 * onBeforeSendHeaders
 * Fires when a request is about to occur and the initial headers have been prepared.
 * It allows adding, modifying, and deleting HTTP request headers
 * <p>
 * onBeforeSendProxyHeaders
 * Fires after onBeforeSendHeaders when proxy connection is used.
 * Provides information about proxy connection, and allows adding, modifying, and deleting HTTP request headers.
 * <p>
 * onSendHeaders
 * Fires right before the HTTP headers are sent to the network.
 * This event is informational and it does not allow modifying HTTP headers.
 * <p>
 * onHeadersReceived
 * Fires each time that an HTTP(S) response header is received.
 * Due to redirects and authentication requests this can happen multiple times per request.
 * This event is intended to allow adding, modifying, and deleting HTTP response headers, such as incoming Set-Cookie headers.
 * <p>
 * onAuthRequired
 * Fires when a request receives an authentication challenge and is unable to respond using cached credentials.
 * You can use this method to handle "basic" or "digest" authentication.
 * <p>
 * onBeforeRedirect
 * Fires when a request is about to occur and the initial headers have been prepared.
 * It allows adding, modifying, and deleting HTTP request headers.
 * <p>
 * onResponseStarted
 * Fires when the first byte of the response body is received. For HTTP requests,
 * this means that the status line and response headers are available. This event is informational.
 * <p>
 * onCompleted
 * Fires when a request has been processed successfully or failed.
 * <p>
 * onDestroyed
 * Fires when a request is being destroyed.
 * <p>
 * onCanSetCookies
 * Fires when engine is about to decide whether specified cookies can be set or not.
 * <p>
 * onCanGetCookies
 * Fires when engine is about to decide whether specified cookies can be received and send to a web server.
 * <p>
 * The following sample demonstrates how to change target URL using onBeforeURLRequest event
 * and print User-Agent HTTP header value when user loads www.google.com:
 */
public class NetworkEvents {

    public static void main(String[] args) {
        BrowserContext browserContext = BrowserContext.defaultContext();
        browserContext.getNetworkService().setNetworkDelegate(new DefaultNetworkDelegate() {
            @Override
            public void onBeforeURLRequest(BeforeURLRequestParams params) {
                // If navigate to teamdev.com, then change URL to google.com.
                if (params.getURL().equals("http://www.teamdev.com/")) {
                    params.setURL("www.baidu.com");
                }
            }

            @Override
            public void onBeforeSendHeaders(BeforeSendHeadersParams params) {
                // If navigate to google.com, then print User-Agent header value.
                if (params.getURL().equals("http://www.baidu.com/")) {
                    HttpHeaders headers = params.getHeaders();
                    System.out.println("User-Agent: " + headers.getHeader("User-Agent"));
                }
            }
        });

        Browser browser = new Browser(browserContext);
        browser.loadURL("http://www.teamdev.com/");
    }
}
