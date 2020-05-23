package com.xknower.web.jx.nineteen;

/**
 * The jxbrowser-win32.jar, jxbrowser-win64.jar, jxbrowser-mac.jar, jxbrowser-linux64.jar libraries are part of JxBrowser library.
 * They contain Chromium binaries for corresponding platform. At first run, JxBrowser extracts the binaries
 * from appropriate JAR file into user’s temp directory on macOS and Linux, and AppData\Local\JxBrowser on Windows.
 * <p>
 * You can specify the directory path into which JxBrowser will extract Chromium binaries using the jxbrowser.chromium.dir system property.
 * You can set the system property using the System.setProperty() method:
 * <p>
 * // System.setProperty("jxbrowser.chromium.dir", "Users/Me/.jxbrowser");
 * <p>
 * Or through JVM parameter: -Djxbrowser.chromium.dir="Users/Me/.jxbrowser"
 * <p>
 * Note: the property must be set before you create the first Browser instance in your application.
 * <p>
 * <p>
 * You can extract Chromium binaries from the jxbrowser-<platform>.jar JAR file manually and configure JxBrowser
 * to use them via the jxbrowser.chromium.dir system property. This property must contain a valid absolute/relative path
 * to the directory with extracted Chromium binaries.
 * <p>
 * <p>
 * JxBrowser checks the directory and if the directory doesn't contain the required Chromium files,
 * JxBrowser searches for the binaries inside JAR files included in the application class path and extract them programmatically.
 * <p>
 * <p>
 * <p>
 * If JxBrowser cannot find Chromium binaries in the provided directory and none of the JAR files included
 * into Java application class path contains Chromium binaries, an exception will be thrown during Browser instance initialization.
 */
public class ChromiumBinaries {
}
