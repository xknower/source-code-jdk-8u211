package com.xknower.web.jx.nineteen;

/**
 * JxBrowser is based on Google Chromium engine. Some Chromium features uses Google APIs.
 * To access those APIs, an API Key is required. To acquire API Key follow the instruction.
 * <p>
 * <p>
 * <p>
 * You can provide the Chromium keys to JxBrowser Chromium engine using one of the following approaches:
 * <p>
 * Via environment variables. Set the GOOGLE_API_KEY, GOOGLE_DEFAULT_CLIENT_ID and GOOGLE_DEFAULT_CLIENT_SECRET environment variables
 * to your "API key", "Client ID" and "Client secret" values respectively.
 * To find out where to get "API key", "Client ID" and "Client secret" see the video instruction.
 * <p>
 * Using the BrowserPreferences.setChromiumVariable(String name, String value) method. For example:
 * <p>
 * // BrowserPreferences.setChromiumVariable("GOOGLE_API_KEY", "Your API Key");
 * // BrowserPreferences.setChromiumVariable("GOOGLE_DEFAULT_CLIENT_ID", "Your Client ID");
 * // BrowserPreferences.setChromiumVariable("GOOGLE_DEFAULT_CLIENT_SECRET", "Your Client Secret");
 * <p>
 * Note: You must configure Chromium variables before you create any Browser instance.
 */
public class ChromiumAPIKeys {
}
