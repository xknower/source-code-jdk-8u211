package com.xknower.web.jx.seven;

/**
 * JxBrowser provides API that allows configuring proxy settings.
 * By default, JxBrowser uses system proxy settings. Proxy settings are stored in BrowserContext instance.
 * To configure Browser instance with specified proxy settings,
 * you must to initialize Browser instance with BrowserContext configured to use specified proxy configuration.
 * <p>
 * The following sample demonstrates how to configure Browser instance with DirectProxyConfig:
 * <p>
 * String dataDir = FileUtil.createTempDir("chromium-data").getAbsolutePath();
 * <p>
 * BrowserContextParams contextParams = new BrowserContextParams(dataDir);
 * contextParams.setProxyConfig(new DirectProxyConfig());
 * <p>
 * Browser browser = new Browser(new BrowserContext(contextParams));
 * <p>
 * AutoDetect
 * With this proxy configuration the connection automatically detects proxy settings:
 * <p>
 * contextParams.setProxyConfig(new AutoDetectProxyConfig());
 * <p>
 * Direct
 * With this proxy configuration the connection will not use proxy server at all:
 * <p>
 * contextParams.setProxyConfig(new DirectProxyConfig());
 * <p>
 * AutoConfig URL
 * With this proxy configuration the connection uses proxy settings received from proxy auto-config (PAC) file.
 * You must provide a valid URL of the required PAC file:
 * <p>
 * contextParams.setProxyConfig(new URLProxyConfig("<pac-file-url>"));
 * <p>
 * Note: URL to the PAC file must be a valid http:// address. You cannot provide a path to a *.pac file stored on local file system.
 * The name of the PAC file must have the pac extension. For example, http://my-site.com/proxy.pac.
 * On a web server the pac file must be served with the application/x-ns-proxy-autoconfig mime type.
 * <p>
 * Manual
 * With this proxy configuration you can provide custom proxy settings for HTTP, HTTPS and FTP protocols:
 * <p>
 * String proxyRules = "http=foo:80;https=foo:80;ftp=foo:80;socks=foo:80";
 * String exceptions = "<local>";  // bypass proxy server for local web pages
 * contextParams.setProxyConfig(new CustomProxyConfig(proxyRules, exceptions));
 * <p>
 * The format of the exceptions string can be any of the following:
 * [ URL_SCHEME "://" ] HOSTNAME_PATTERN [ ":" ] e.g. "foobar.com", "*foobar.com", "*.foobar.com", "*foobar.com:99", "https://x.*.y.com:99"
 * "." HOSTNAME_SUFFIX_PATTERN [ ":" PORT ] e.g. ".google.com", ".com", "http://.google.com"
 * [ SCHEME "://" ] IP_LITERAL [ ":" PORT ] e.g. "127.0.1", "[0:0::1]", "[::1]", "http://[::1]:99"
 * IP_LITERAL "/" PREFIX_LENGHT_IN_BITS e.g. "192.168.1.1/16", "fefe:13::abc/33"
 * "<local>" Match local addresses. The meaning of "<local>" is whether the host matches one of: "127.0.0.1", "::1", "localhost".
 * <p>
 * Proxy Authorization
 * If proxy server requires authorization you can provide login and password programmatically using the following API:
 * <p>
 * browser.getContext().getNetworkService().setNetworkDelegate(new DefaultNetworkDelegate() {
 *
 * @Override public boolean onAuthRequired(AuthRequiredParams params) {
 * if (params.isProxy()) {
 * params.setUsername("proxy-username");
 * params.setPassword("proxy-password");
 * return false;
 * }
 * return true;
 * }
 * });
 * <p>
 * Modifying proxy settings for an already created browser instance
 * Since version 6.15, the functionality that allows modifying proxy settings runtime is available in JxBrowser.
 * <p>
 * You can change proxy settings runtime for specific BrowserContext instance.
 * The proxy configuration will be applied automatically to all Browser instances associated with the BrowserContext.
 */
/**
 * The following sample demonstrates how to configure Browser instance with DirectProxyConfig:
 *
 * String dataDir = FileUtil.createTempDir("chromium-data").getAbsolutePath();
 *
 * BrowserContextParams contextParams = new BrowserContextParams(dataDir);
 * contextParams.setProxyConfig(new DirectProxyConfig());
 *
 * Browser browser = new Browser(new BrowserContext(contextParams));
 */
/**
 * AutoDetect
 * With this proxy configuration the connection automatically detects proxy settings:
 *
 * contextParams.setProxyConfig(new AutoDetectProxyConfig());
 *
 * Direct
 * With this proxy configuration the connection will not use proxy server at all:
 *
 * contextParams.setProxyConfig(new DirectProxyConfig());
 */
/**
 * AutoConfig URL
 * With this proxy configuration the connection uses proxy settings received from proxy auto-config (PAC) file.
 * You must provide a valid URL of the required PAC file:
 *
 * contextParams.setProxyConfig(new URLProxyConfig("<pac-file-url>"));
 */
/**
 * Note: URL to the PAC file must be a valid http:// address. You cannot provide a path to a *.pac file stored on local file system.
 * The name of the PAC file must have the pac extension. For example, http://my-site.com/proxy.pac.
 * On a web server the pac file must be served with the application/x-ns-proxy-autoconfig mime type.
 */
/**
 * Manual
 * With this proxy configuration you can provide custom proxy settings for HTTP, HTTPS and FTP protocols:
 *
 * String proxyRules = "http=foo:80;https=foo:80;ftp=foo:80;socks=foo:80";
 * String exceptions = "<local>";  // bypass proxy server for local web pages
 * contextParams.setProxyConfig(new CustomProxyConfig(proxyRules, exceptions));
 */
/**
 *  The format of the exceptions string can be any of the following:
 *  [ URL_SCHEME "://" ] HOSTNAME_PATTERN [ ":" ] e.g. "foobar.com", "*foobar.com", "*.foobar.com", "*foobar.com:99", "https://x.*.y.com:99"
 * "." HOSTNAME_SUFFIX_PATTERN [ ":" PORT ] e.g. ".google.com", ".com", "http://.google.com"
 * [ SCHEME "://" ] IP_LITERAL [ ":" PORT ] e.g. "127.0.1", "[0:0::1]", "[::1]", "http://[::1]:99"
 * IP_LITERAL "/" PREFIX_LENGHT_IN_BITS e.g. "192.168.1.1/16", "fefe:13::abc/33"
 * "<local>" Match local addresses. The meaning of "<local>" is whether the host matches one of: "127.0.0.1", "::1", "localhost".
 */

/**
 * Proxy Authorization
 * If proxy server requires authorization you can provide login and password programmatically using the following API:
 *
 * browser.getContext().getNetworkService().setNetworkDelegate(new DefaultNetworkDelegate() {
 *     @Override
 *     public boolean onAuthRequired(AuthRequiredParams params) {
 *         if (params.isProxy()) {
 *             params.setUsername("proxy-username");
 *             params.setPassword("proxy-password");
 *             return false;
 *         }
 *         return true;
 *     }
 * });
 */
/**
 * Modifying proxy settings for an already created browser instance
 * Since version 6.15, the functionality that allows modifying proxy settings runtime is available in JxBrowser.
 *
 * You can change proxy settings runtime for specific BrowserContext instance. The proxy configuration will be applied automatically
 * to all Browser instances associated with the BrowserContext.
 */

/**
 * The following sample demonstrates how to use this API:
 * ...
 * BrowserContext browserContext = browser.getContext();
 * ProxyService proxyService = browserContext.getProxyService();
 * proxyService.setProxyConfig(new CustomProxyConfig("http=foopy:80"));
 */
public class WorkingWithProxy {
}
