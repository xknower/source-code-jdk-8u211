package com.xknower.web.jx.eighteen;

/**
 * You can use JxBrowser in Java Applet the same way as a standard Java library.
 * <p>
 * JxBrowser requires access to file system, so all the JAR libraries of your Java Applet must be signed.
 * JxBrowser JAR files are signed with TeamDev signature by default, so you don’t need to to sign them.
 * <p>
 * <p>
 * Your APPLET tag on a web page should look like:
 */
// <APPLET code="com.mycompany.myapp.Application.class" codebase="."
//    archive="myapp.jar, jxbrowser.jar,jxbrowser-win32.jar,
//        jxbrowser-win64.jar,jxbrowser-mac.jar,
//        jxbrowser-linux64.jar" WIDTH=800 HEIGHT=400>
//    Browser doesn't support Java Applets.
//</APPLET>

/**
 * Where the myapp.jar file is your application JAR file.
 * <p>
 * Note: If your Java Applet supports only Windows platform, there is no need to include the the jxbrowser-mac
 * and jxbrowser-linux64 JAR files into the archive attribute.
 */
public class JavaAppletSimple {
}
