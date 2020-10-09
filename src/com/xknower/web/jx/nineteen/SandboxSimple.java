package com.xknower.web.jx.nineteen;

/**
 * Windows
 * Starting with JxBrowser 6.17, Chromium sandbox support for Windows was added.
 * By default, Sandbox is disabled to save backward compatibility with the previous versions.
 * To enable Sandbox set the "jxbrowser.chromium.sandbox" System Property to true before you create a first Browser instance.
 * For example:
 * <p>
 * // System.setProperty("jxbrowser.chromium.sandbox", "true");
 * // Browser browser = new Browser();
 * <p>
 * <p>
 * Linux and macOS
 * Currently, Sandbox is supported on Windows platform only.
 */
public class SandboxSimple {
}
