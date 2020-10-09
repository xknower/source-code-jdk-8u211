package com.xknower.web.jx.nineteen;

/**
 * Since 6.21, JxBrowser distribution package includes Chromium 64-bit binaries for Windows platform.
 * So, now JxBrowser provides both the jxbrowser-win32.jar and jxbrowser-win64.jar libraries
 * that contain Chromium 32-bit and Chromium 64-bit binaries accordingly.
 * <p>
 * Important: Please note that Chromium 64-bit binaries for Windows, supports only Windows 64-bit environment and cannot be used on Windows 32-bit.
 * <p>
 * <p>
 * Which one is used?
 * It depends on the Windows architecture and your Java application classpath.
 * <p>
 * <p>
 * Both jxbrowser-win32.jar and jxbrowser-win64.jar added to the classpath
 * If you include both JAR files into your Java application classpath and run JxBrowser in a Windows environment for the first time,
 * JxBrowser will check the environment architecture and extract/use the appropriate Chromium binaries.
 * On Windows 64-bit JxBrowser will extract and use Chromium 64-bit binaries. On Windows 32-bit – Chromium 32-bit binaries.
 * <p>
 * <p>
 * Only jxbrowser-win32.jar added to the classpath
 * In this case Chromium 32-bit binaries will be extracted and used.
 * Chromium 32-bit binaries work well in both Windows 32-bit and 64-bit environments.
 * <p>
 * <p>
 * <p>
 * Only jxbrowser-win64.jar added to the classpath
 * In this case JxBrowser checks the Windows architecture. If it’s a Windows 64-bit environment,
 * then Chromium 64-bit binaries will be extracted and used. If it’s a Windows 32-bit environment – an exception will be thrown.
 * <p>
 * <p>
 * <p>
 * Recommendations
 * If you know that your end users use only Windows 64-bit environment or Windows 32-bit isn’t supported by your software,
 * then it’s recommended to use only jxbrowser-win64.jar.
 * <p>
 * <p>
 * <p>
 * If your software supports both Windows 32-bit and 64-bit environments,
 * and it’s critical to have the size of your software distribution package as small as possible,
 * then it’s recommended to use only jxbrowser-win32.jar.
 * <p>
 * <p>
 * <p>
 * If your software supports both Windows 32-bit and 64-bit environments,
 * and the size of your software distribution package isn’t important, then you can use both jxbrowser-win32.jar and jxbrowser-win64.jar.
 */
public class Chromium64bitonWindows {
}
